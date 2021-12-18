package Controllers;

import Helpers.errorHandler;
import Models.search;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Den här klassen kommer att användas för att söka i Spotify.
 *
 */
public class spotifySearchController
{
	public String search(String json)
	{
		try
		{
			//Omvandla json till java-objekt.
			search searchData = new Gson().fromJson(json, search.class);

			//Lägga till headers.
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + searchData.getAuth());
			headers.add("Content-Type", "application/json");

			 //Ersätta blanksteg med '+' för att följa reglerna för spotifys API i query för url.
			for (int i = 0; i < searchData.getQuery().length(); i++)
			{
				searchData.getQuery().replaceAll(" ", "+");
			}

			//Skicka en förfråga till spotify API.
			HttpEntity<String> reqEntity = new HttpEntity<String>("", headers);
			ResponseEntity<String> resEntity = new RestTemplate().exchange("https://api.spotify.com/v1/search?" + "q=" + searchData.getQuery() + "&" + "type=" + searchData.getType(), HttpMethod.GET, reqEntity, String.class);

			return resEntity.getBody();
		}
		catch (RestClientException e)
		{
			int statusCode = Integer.parseInt(e.getMessage().substring(0, e.getMessage().indexOf(" ")));
			return new Gson().toJson(new errorHandler(statusCode, e.getMessage()));
		}
	}
}

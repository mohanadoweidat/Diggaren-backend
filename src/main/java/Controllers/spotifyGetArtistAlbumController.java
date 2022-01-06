package Controllers;

import Helpers.errorHandler;
import Models.getArtistAlbum;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class spotifyGetArtistAlbumController
{
	public String getArtistAlbums(String json){
		try
		{
			//Omvandla json till java-objekt.
			getArtistAlbum artistAlbum = new Gson().fromJson(json, getArtistAlbum.class);

			//Lägga till headers.
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + artistAlbum.getAuth());
			headers.add("Content-Type", "application/json");

			//Skicka en förfråga till spotify API.
			HttpEntity<String> reqEntity = new HttpEntity<String>("", headers);
			ResponseEntity<String> resEntity = new RestTemplate().exchange("https://api.spotify.com/v1/artists/"+artistAlbum.getArtistId()+
									"/albums?" +  "offset=3&limit=3", HttpMethod.GET, reqEntity, String.class);

			//Response body.
			return resEntity.getBody();
		}
		catch (
				RestClientException e)
		{
			int statusCode = Integer.parseInt(e.getMessage().substring(0, e.getMessage().indexOf(" ")));
			return new Gson().toJson(new errorHandler(statusCode, e.getMessage()));
		}
	}

}

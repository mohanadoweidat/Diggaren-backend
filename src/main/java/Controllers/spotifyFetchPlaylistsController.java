package Controllers;

import Helpers.errorHandler;
import Models.playlist;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class spotifyFetchPlaylistsController
{
   public String fetchPlaylists(String json){

    try
    {
    	//Omvandla json till java-objekt.
	    playlist playlistData = new Gson().fromJson(json, playlist.class);

	    //Lägga till headers.
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + playlistData.getAuth());

		//Hämta userId.
	    HttpEntity<String > reqEntity = new HttpEntity<>("",httpHeaders);
	    ResponseEntity<String> resEntity = new RestTemplate().exchange("https://api.spotify.com/v1/me/playlists", HttpMethod.GET, reqEntity, String.class);

	    //Response body.
	    return  resEntity.getBody();
    }
    catch (RestClientException restClientException){
         int sCode = Integer.parseInt(restClientException.getMessage().substring(0,
		         restClientException.getMessage().indexOf(" ")));
         return new Gson().toJson(new errorHandler(sCode, restClientException.getMessage()));
    }
   }
}

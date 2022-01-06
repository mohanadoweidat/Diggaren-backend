package Controllers;
import Helpers.errorHandler;
import Models.addToPlaylist;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

/**
 * En klass som används för att lägga till låtar till en spellista på spotify.
 */
public class spotifyAddToPlaylistController
{
	public String addToPlayList(String json){
		try {
			//Omvandla json till java-objekt.
			addToPlaylist addToPlaylist = new Gson().fromJson(json, addToPlaylist.class);

			//Lägga till headers.
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + addToPlaylist.getAuth());
			headers.add("Content-Type", "application/json");

			//Skicka en förfråga till spotify API.
			HttpEntity<String> reqEntity = new HttpEntity<String>("", headers);
			ResponseEntity<String> resEntity = new RestTemplate().exchange("https://api.spotify.com/v1/playlists/" + addToPlaylist.getPlaylist_id() + "/tracks?uris=spotify:track:" + addToPlaylist.getTrack_id(), HttpMethod.POST, reqEntity, String.class);

			//Response body.
			return resEntity.getBody();
		}catch (RestClientException e){
			int statusCode = Integer.parseInt(e.getMessage().substring(0, e.getMessage().indexOf(" ")));
			return new Gson().toJson(new errorHandler(statusCode, e.getMessage()));
		}
	}
}

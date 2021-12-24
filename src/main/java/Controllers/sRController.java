package Controllers;

import Helpers.errorHandler;
import Models.playingSong;
import Models.sRMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class sRController {
    public String getSongPlaying(sRMessage msg) {
        try {
            //Hämta information om låten som spelas just nu i SR genom att göra GET anrop till SR öppen API.
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> reqEntity = new HttpEntity<String>("", headers);
            ResponseEntity<String> resEntity = new RestTemplate().exchange("http://api.sr.se/api/v2/playlists/" +
                            "rightnow?channelid=" + msg.getChannelID() + "&format=JSON",
                    HttpMethod.GET, reqEntity, String.class);
            System.out.println("request skickades");
            System.out.println(resEntity.getStatusCode());
            //Json parser för att parsa API svaret i json.
            JsonObject playlistObject = JsonParser.parseString(resEntity.getBody()).getAsJsonObject().get("playlist").getAsJsonObject();

            String currentSongName = null, currentSongArtist = null;

            //Kontrollera ifall det finns null värden, alltså om ingen låt spelas på radion i respektive valda kanalen.
            try {
                JsonObject currentSongObject = playlistObject.get("song").getAsJsonObject();
                currentSongName = currentSongObject.get("title").getAsString();
                currentSongArtist = currentSongObject.get("artist").getAsString();
            } catch (NullPointerException e) {
                return new Gson().toJson(new errorHandler(400, "channelID is a not a valid ID OR there is no song currently playing"));
            }


            //Om det inte finns en låt som spelas efter alltså 'nextsong' så sätt tiden för det till -1.
            JsonObject nextSongObject = null;
            String nextSongStartTime;
            playingSong song = null;
            boolean nextSongExists = true;
            try {
                nextSongObject = playlistObject.get("nextsong").getAsJsonObject();
            } catch (Exception e) {
                nextSongExists = false;
            }
            if (nextSongExists) {
                nextSongStartTime = nextSongObject.get("starttimeutc").getAsString();
                song = new playingSong(currentSongName, currentSongArtist, nextSongStartTime);
            } else {
                song = new playingSong(currentSongName, currentSongArtist, "-1");
            }

            return new Gson().toJson(song);
        } catch (RestClientException e) {
            int statusCode = Integer.parseInt(e.getMessage().substring(0, e.getMessage().indexOf(" ")));
            return new Gson().toJson(new errorHandler(statusCode, e.getMessage()));
        }
    }
}

package Controllers;

import Helpers.errorHandler;
import Models.recommendation;
import Models.trackMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Controller klass för "Spotify/recommendation" ändpunkt. Det hämtar och behandlar data från spotify API samt lägger
 * in svaret i ett recommendation objekt som sedan skickas till klienten som efterfrågade.
 */
public class recommendationController {
    public recommendationController(){

    }

    public String getRecommendation(trackMessage msg){
        ResponseEntity<String> resEntity;
        Gson gson = new Gson();
        //Hämtar recommendation från spotify API genom att skicka GET förfråga.
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + msg.getAuth());
            headers.add("Content-Type", "application/json");
            HttpEntity<String> reqEntity = new HttpEntity<String>("", headers);
            resEntity = new RestTemplate().exchange("https://api.spotify.com/v1/recommendations?" +
                            "limit=1&market=SE&seed_tracks=" + msg.getTrackID() +
                            "&min_energy=0.4&min_popularity=50",
                    HttpMethod.GET, reqEntity, String.class);
        }catch (RestClientException restClientException){
            int statusCode = Integer.parseInt(restClientException.getMessage().substring(0, restClientException.getMessage().indexOf(" ")));
            return new Gson().toJson(new errorHandler(statusCode, restClientException.getMessage()));
        }

        //Svaret som vi får är nästlad Json-object så vi behöver omvandla till en map och sen hämta önskade data.


        //Hämtar bara en låt och det enda från svaret som ett JSON-object.
        JsonObject trackInfo = JsonParser.parseString(resEntity.getBody()).getAsJsonObject().get("tracks").getAsJsonArray().get(0).getAsJsonObject();
        String trackID = trackInfo.get("id").getAsString();
        String trackName = trackInfo.get("name").getAsString();
        JsonObject artistInfo = trackInfo.getAsJsonArray("artists").get(0).getAsJsonObject();
        String artistName = artistInfo.get("name").getAsString();
        String artistID = artistInfo.get("id").getAsString();

        //TO be deleted- debug.
        System.out.println(artistName + " - " + trackName);

        return gson.toJson( new recommendation(trackName, trackID, artistName, artistID));

    }
}

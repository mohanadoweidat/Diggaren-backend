import Helpers.corsFilter;
import Controllers.*;
import Helpers.errorHandler;
import Models.sRMessage;
import Models.trackMessage;
import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import static spark.Spark.*;

public class startAPI {

	public static void main(String[] args)
	{
         //Vi startar API här.

		//This is to enable logging.
		BasicConfigurator.configure();

		//För att välja porten.
		port(5000);

		//Används för att ge våra svar de rubriker som krävs behövde för CORS.
		new corsFilter().apply();


		Gson gson = new Gson();


		//Ett meddelande för intern fel
		internalServerError((req, res) -> {
			res.type("application/json");
			return "{\"message\":\"Custom 500 handling\"}";
		});

		//Ett meddelande för error 404.
		notFound((req, res) -> {
			res.type("application/json");
			return "{\"message\":\"Custom 404\"}";
		});


		//Skapa controller för spotify
        spotifySearchController searchController = new spotifySearchController();
        spotifyFetchPlaylistsController fetchPlaylistsController = new spotifyFetchPlaylistsController();
        spotifyRecommendationController spotifyRecommendationController = new spotifyRecommendationController();
        spotifyAddToPlaylistController addToPlaylistController = new spotifyAddToPlaylistController();
        spotifyGetArtistAlbumController getArtistAlbumController = new spotifyGetArtistAlbumController();

        //Skapa controller för SR
		sRController sRController = new sRController();



		//Ändpunkter
		path("/spotify", () -> {
			//En ändpunkt för att söka i spotify API.
			post("/search", (request,response) -> {
				response.type("application/json"); //definiera svar som json
				String responseBody = searchController.search(request.body());
				if(responseBody.contains("statusCode")){
					errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
					response.status(errorObject.getStatusCode());
					response.body(errorObject.getErrorMessage());
				} else {
					response.status(200);
				}
				return responseBody;
			});


			//Ändpunkt för att hämta användarens spellistor.
			path("/playlists", () -> {
				post("/fetch", (request,response) -> {
					response.type("application/json"); //definiera svar som json
					String responseBody = fetchPlaylistsController.fetchPlaylists(request.body());
					if(responseBody.contains("statusCode")){
						errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
						response.status(errorObject.getStatusCode());
						response.body(errorObject.getErrorMessage());
					} else {
						response.status(200);
					}
					return responseBody;
				});


				//Lägg till en låt i en spellista.
				post("/add", (request,response) -> {
					response.type("application/json"); //Definiera svar som json
					String responseBody = addToPlaylistController.addToPlayList(request.body());
					if(responseBody.contains("statusCode")){
						errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
						response.status(errorObject.getStatusCode());
						response.body(errorObject.getErrorMessage());
					} else {
						response.status(200);
					}
					return response;
				});
			});

			//Hämta artistens album.
			post("/artistAlbums", (request, response) -> {
				response.type("application/json"); //Definiera svar som json
				String responseBody = getArtistAlbumController.getArtistAlbums(request.body());
				if(responseBody.contains("statusCode")){
					errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
					response.status(errorObject.getStatusCode());
					response.body(errorObject.getErrorMessage());
				} else {
					response.status(200);
				}
				return responseBody;
			});



			//Rekommendationer --> Hämta en rekommenderad låt baserat på låten som spelas i SR.
			post("/recommendation", (request, response) -> {
				System.out.println("recommending song");
				response.type("application/json"); //definiera svar som json
				trackMessage msg = gson.fromJson(request.body(), trackMessage.class); //hämta json object från body
				// som ett definierat objekt
				System.out.println("auth: " + msg.getAuth());
				System.out.println("trackID " + msg.getTrackID());
				String responseBody = spotifyRecommendationController.getRecommendation(msg);
				if(responseBody.contains("statusCode")){
					errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
					response.status(errorObject.getStatusCode());
					response.body(errorObject.getErrorMessage());
				} else {
					response.status(200);
				}
				return responseBody;
			});

		});



		path("/SR", () -> {
			//En ändpunkt för att hämta låten som spelas just nu i någon kanal i SR.
			post("/currentlyPlaying", (request, response) -> {
				response.type("application/json"); //definiera svar som json
				sRMessage msg = gson.fromJson(request.body(), sRMessage.class); //hämta json object från body som ett
				// definierat objekt
				String responseBody = sRController.getSongPlaying(msg);
				if(responseBody.contains("statusCode")){
					errorHandler errorObject = new Gson().fromJson(responseBody, errorHandler.class);
					response.status(errorObject.getStatusCode());
					response.body(errorObject.getErrorMessage());
				} else {
					response.status(200);
				}
				return responseBody;
			});
		});



		//Denna Ändpunkt hanterar preflight automatiskt som görs av webbläsare.
		//Den finns inte med i dokumentationen.
		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			String accessControlRequestOrigin = request.headers("Access-Control-Request-Origin");
			if (accessControlRequestOrigin != null){
				response.header("Access-Control-Allow-Origin", "*");
			}
			return "OK";
		});
	}
}
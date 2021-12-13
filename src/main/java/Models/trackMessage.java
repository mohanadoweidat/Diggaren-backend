package Models;
/**
 * Representerar JSON meddelande som skickas från klienten till server.
 *
 * Kan användas för att skicka meddelande från klienten till Recommendation ändpunkt.
 * Det innehåller en auktoriseringstoken samt låt id från Spotify för låten som kommer att användas i den begärda data
 */
public class trackMessage {
    private String auth;
    private String trackID;

    public trackMessage(String auth, String trackID)
    {
        this.auth = auth;
        this.trackID = trackID;
    }

    public String getAuth()
    {
        return auth;
    }
    public void setAuth(String auth)
    {
        this.auth = auth;
    }

    public String getTrackID()
    {
        return trackID;
    }
    public void setTrackID(String trackID)
    {
        this.trackID = trackID;
    }
}

package Models;

/**
 * En klass som används för att omvandla objekt från Json till java.
 */
public class getArtistAlbum
{
    private String auth;
    private String artistId;

    public getArtistAlbum(String auth, String artistId)
    {
        this.auth = auth;
        this.artistId = artistId;

    }

    public String getAuth()
    {
        return auth;
    }

    public String getArtistId()
    {
        return artistId;
    }
}

package Models;

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

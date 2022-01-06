package Models;

/**
 * En klass som används för att omvandla objekt från Json till java.
 */
public class addToPlaylist
{
	private String playlist_Id;
	private String auth;
	private String track_Id;

	public String getPlaylist_id() {
		return playlist_Id;
	}

	public String getAuth() { return auth; }

	public String getTrack_id() {
		return track_Id;
	}
}


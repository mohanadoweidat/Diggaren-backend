package Models;

/**
 * En klass som används för att omvandla objekt från Json till java.
 */
public class playlist
{
	private String playlist_id;
	private String auth;
	private String track_id;

	public String getPlaylist_id() {
		return playlist_id;
	}

	public String getAuth() { return auth; }

	public String getTrack_id() {
		return track_id;
	}
}

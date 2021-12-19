package Models;

/**
 * Representerar objektsvar till klienten från sR "currentlyPlaying" ändpunkt. Innehåller information om nuvarande
 * låt som spelas samt tiden för låten som körs efter. Kommer att användas för att skicka JSON svar till klienten som
 * efterfrågar.
 */
public class playingSong {
    private String playingSongName;
    private String playingSongArtist;
    private String nextSongStartTime;

    public playingSong(String playingSongName, String playingSongArtist, String nextSongStartTime) {
        this.playingSongName = playingSongName;
        this.playingSongArtist = playingSongArtist;
        this.nextSongStartTime = nextSongStartTime;
    }

    public String getPlayingSongName() {
        return playingSongName;
    }

    public void setPlayingSongName(String playingSongName) {
        this.playingSongName = playingSongName;
    }

    public String getPlayingSongArtist() {
        return playingSongArtist;
    }

    public void setPlayingSongArtist(String playingSongArtist) {
        this.playingSongArtist = playingSongArtist;
    }

    public String getNextSongStartTime() {
        return nextSongStartTime;
    }

    public void setNextSongStartTime(String nextSongStartTime) {
        this.nextSongStartTime = nextSongStartTime;
    }
}
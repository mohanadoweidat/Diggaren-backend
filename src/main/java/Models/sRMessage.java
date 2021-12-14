package Models;

/**
 * Representerar meddelandet som skickas i JSON format från klienten till ändpunkten.
 * Den innehåller kanal id för valt kanal.
 */
public class sRMessage {
    private int channelID;

    public sRMessage(int channelID) {
        this.channelID = channelID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
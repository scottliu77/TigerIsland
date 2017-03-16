import java.lang.management.PlatformLoggingMXBean;

public class GameSettings {

    public GlobalSettings globalSettings;
    private Deck deck;
    private PlayOrder playOrder;

    GameSettings() {
        this.globalSettings = new GlobalSettings();
    }

    GameSettings(GlobalSettings settings) {
        this.globalSettings = settings;
    }

    public void setDeck() {
        if(globalSettings.offline) {
            deck = new Deck();
        } else {
            // TODO Server implementation of deck(?)
        }
    }

    public void setPlayOrder() {
        if(globalSettings.offline) {
            playOrder = new PlayOrder();
        } else {
            // TODO Server implementation of play order(?)
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayOrder getPlayOrder() {
        return playOrder;
    }

}

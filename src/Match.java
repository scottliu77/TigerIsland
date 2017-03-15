import java.util.ArrayList;

public class Match {

    protected Settings settings;
    protected ArrayList<Game> games;

    Match(Settings settings) {
        this.settings = settings;
        this.games = new ArrayList<Game>();
        for(int game = 0; game < settings.gameCount; game++) {
            games.add(game, new Game(settings.playerCount));
        }
    }

    // TODO overloaded constructor will go here, Match(Boolean offline, String connectionAddress)

    public void start() {
        if(settings.offline) {
            setupOfflineMatch();
        } else{
            setupOnlineMatch();
        }
    }

    // TODO Multi-threading required here. Otherwise games are run sequentially.
    private void setupOfflineMatch() {
        for(int game = 0; game < games.size(); game++) {
            games.get(game).start();
        }
    }

    private void setupOnlineMatch() {
        // TODO server setup procedure will go here
        System.out.println("TODO - waiting for server specifications");
    }
}

import java.util.ArrayList;

public class Game {

    private Settings settings;
    private ArrayList<Player> players;
    private Board board;
    private int currentPlayerIndex;

    public Game(Settings settings){
        this.settings = settings;
        board = new Board();
        players = new ArrayList<Player>();
        for(int i = 0; i < this.settings.playerCount; i++){
            players.add(i, new Player(i));
        }
    }

    public void start() {

    }

    public boolean noValidMoves(){
        Player currentPlayer = players.get(currentPlayerIndex);
        if(currentPlayer.mustPlaceTotoro()){

        }
        return false;
    }

    public boolean playerIsOutOfPieces(){
        for(Player player : players){
            if(player.getPieces().inventoryEmpty()){
                return true;
            }
        }
        return false;
    }

}

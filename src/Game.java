import java.util.ArrayList;

/**
 * Created by johnzoldos on 3/13/17.
 */
public class Game {

    private ArrayList<Player> players;
    private Board board;
    private int currentPlayerIndex;

    public Game(int numPlayers){
        board = new Board();
        for(int i=0;i<numPlayers;i++){
            players.add(i, new Player(i));
        }
    }

    public boolean noValidMoves(){
        Player currentPlayer = players.get(currentPlayerIndex);
        if(currentPlayer.mustPlaceTotoro()){

        }
        return false;
    }

    public boolean playerIsOutOfPieces(){
        for(Player player : players){
            if(player.inventoryEmpty()){
                return true;
            }
        }
        return false;
    }

}

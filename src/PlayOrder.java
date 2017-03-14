import java.util.*;

public class PlayOrder {

    private int currentPlayer = 0;

    public ArrayList<Player> determinePlayOrder(ArrayList<Player> players){
        Collections.shuffle(players);
        return players;
    }

    public Player getCurrentPlayer(ArrayList<Player> players){
        return players.get(currentPlayer);
    }

    public void setNextPlayer(){
        currentPlayer++;
    }
}

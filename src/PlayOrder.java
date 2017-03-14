import java.util.*;

public class PlayOrder {

    private int currentPlayer = 0;

    public ArrayList<Player> determinePlayOrder(ArrayList<Player> players){
        int a = 0;
        int b = players.size();
        int c = 0;
        for (int i = a; i < b; i++){
            c = a - 1 + (int)(Math.random() * (b - a));
            Player temp = players.get(a);
            players.set(a, players.get(c));
            players.set(c, temp);
        }
        return players;
    }

    public Player getCurrentPlayer(ArrayList<Player> players){
        return players.get(currentPlayer);
    }

    public void setNextPlayer(){
        currentPlayer++;
    }
}

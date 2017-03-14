import java.util.*;

public class Turn {

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

}

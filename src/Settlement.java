import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Settlement {
    String color;
    ArrayList<Hex> hexesInSettlement;

    public Settlement(Hex hexInSettlement){
        this.color = hexInSettlement.getPieceColor();
        findHexesInSettlement(hexInSettlement);
    }

    public boolean containsTotoro(){
        for(Hex hex : hexesInSettlement){
            if(hex.getPieceType().equals(PieceType.TOTORO)){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return hexesInSettlement.size();
    }

    private void findHexesInSettlement(Hex hex){
        Queue<Hex> hexesToBeAnalyzed = new LinkedList<Hex>();
        hexesToBeAnalyzed.add(hex);
        HashSet<Hex> visitedHexes = new HashSet<Hex>();
        while(!hexesToBeAnalyzed.isEmpty()){
            Hex currentHex = hexesToBeAnalyzed.remove();
            visitedHexes.add(currentHex);
            hexesInSettlement.add(currentHex);
            /*ArrayList<Hex> adjacentHexesToCurrentHex = currentHex.getAdjacentHexes();
            for(Hex hexInAdjacentList : adjacentHexesToCurrentHex) {
                if (ownedBySamePlayer(currentHex.getPieceColor(), color) && !visitedHexes.contains(hexInAdjacentList)) {
                    hexesToBeAnalyzed.add(hexInAdjacentList);
                }
            }*/
        }
    }

    private boolean ownedBySamePlayer(String firstPieceColor, String secondPieceColor){
        return firstPieceColor.equals(secondPieceColor);
    }


}

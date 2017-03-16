/**
 * Created by scott on 3/13/17.
 */
public class Player {

    protected Score score;
    protected Color color;
    protected PieceSet pieceSet;

    public Player(Color color) {
        this.color = color;
        score = new Score();
        pieceSet = new PieceSet(color);
    }

    public Score getScore() {
        return score;
    }

    public Color getPlayerColor() {
        return color;
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

    public boolean mustPlaceTotoro(){
        if(pieceSet.getNumberOfVillagersRemaining() == 0 && pieceSet.getNumberOfTotoroRemaining() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void createNewSettlement(){

    }

    public void expandSettlement(){

    }

    public void createTotoroSanct(){

    }

    public boolean canBuildSettlement(){
        if (getPieceSet().villagerSet.size() > 0){
            return true;
        }
        return false;
    }

//    public boolean canExpandSettlement(PieceSet pieceSet){
//        if (villagersRemaining() >= piecesNeeded()){
//            return true;
//        }
//        return false;
//    }
//
//    public boolean canCreateTotoroSanct(Settlement settlement){
//        if (TotoroRemaining() > 0 && settlement.size() > 4 && !settlement.hasTotoro()){
//            return true;
//        }
//        return false;
//
//    }
}

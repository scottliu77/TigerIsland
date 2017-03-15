/**
 * Created by scott on 3/13/17.
 */
public class Player {

    protected Score score;
    protected PlayerColor color;
    protected Pieces pieces;

    public Player(PlayerColor color) {
        this.color = color;
        score = new Score();
        pieces = new Pieces(getPlayerColor().getCode());
    }

    public Player(int code) {
        switch (code) {
            case 0:
                this.color = PlayerColor.BLACK;
                break;
            case 1:
                this.color = PlayerColor.WHITE;
                break;
        }
        score = new Score();
        pieces = new Pieces(getPlayerColor().getCode());
    }

    public Score getScore() {
        return score;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public boolean mustPlaceTotoro(){
        if(pieces.getNumberOfVillagersRemaining() == 0 && pieces.getNumberOfTotoroRemaining() != 0){
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
        if (getPieces().villagerSet.size() > 0){
            return true;
        }
        return false;
    }

//    public boolean canExpandSettlement(Pieces pieces){
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

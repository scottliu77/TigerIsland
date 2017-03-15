/**
 * Created by scott on 3/13/17.
 */
public class Player {

    private int score;
    private PlayerColor color;
    private Pieces pieces;

    public Player(PlayerColor color) {
        this.color = color;
        score = 0;
        pieces = new Pieces(getPlayerCode());
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
        score = 0;
    }

    public String getPlayerColor() {
        return this.color.getColor();
    }

    public int getPlayerCode() {
        return this.color.getCode();
    }

    public int getScore() {
        return score;
    }

    public int villagersRemaining(){
        return this.pieces.getNumberOfVillagersRemaining();
    }

    public int TotoroRemaining(){
        return this.pieces.getNumberOfTotoroRemaining();
    }

    public boolean inventoryEmpty(){
        if(pieces.getNumberOfTotoroRemaining() == 0 && pieces.getNumberOfVillagersRemaining() == 0){
            return true;
        }
        else {
            return false;
        }
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
        if (villagersRemaining() > 0){
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

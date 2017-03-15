public class Build {

    public void createNewSettlement(){

    }

    public void expandSettlement(){

    }

    public void createTotoroSanct(){

    }

    public boolean canBuildSettlement(){
        if (Player.pieces.getNumberOfVillagersRemaining() > 0){
            return true;
        }
        return false;
    }

    public boolean canExpandSettlement(Pieces pieces){
        if (Player.villagersRemaining() >= piecesNeeded()){
            return true;
        }
        return false;
    }

    public boolean canCreateTotoroSanct(){
        if (Player.pieces.getNumberOfVillagersRemaining() > 0 && settlement.size() > 4 && !settlement.hasTotoro()){
            return true;
        }
        return false;

    }
}

import java.util.ArrayList;

public class Pieces {

    ArrayList<Villager> villagerSet;
    ArrayList<Totoro> totoroSet;

    public Pieces(int ownerCode) {
        this.villagerSet = new ArrayList<Villager>();
        this.totoroSet = new ArrayList<Totoro>();
        this.generateVillagerSet(ownerCode);
        this.generateTotoroSet(ownerCode);
    }

    private void generateVillagerSet(int code) {
        for(int pieceNumber = 1; pieceNumber <= 20; pieceNumber++) {
            this.villagerSet.add(new Villager(code));
        }
    }

    private void generateTotoroSet(int code) {
        for(int pieceNumber = 1; pieceNumber <= 3; pieceNumber++) {
            this.totoroSet.add(new Totoro(code));
        }
    }

    public int getNumberOfVillagersRemaining() {
        return villagerSet.size();
    }

    public Totoro placeTotoro() {
        return totoroSet.remove(0);
    }

    public Villager placeVillager() {
        return villagerSet.remove(0);
    }

    public int getNumberOfTotoroRemaining() {
        return totoroSet.size();
    }

    public boolean inventoryEmpty(){
        if(totoroSet.size() == 0 && villagerSet.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }
}

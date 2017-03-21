package com.tigerisland;

import java.util.ArrayList;

public class PieceSet {

    protected ArrayList<Piece> villagerSet;
    protected ArrayList<Piece> totoroSet;
    protected Color ownerColor;

    public PieceSet(Color ownerColor) {
        this.villagerSet = new ArrayList<Piece>();
        this.totoroSet = new ArrayList<Piece>();
        this.generateVillagerSet(ownerColor, 20);
        this.generateTotoroSet(ownerColor, 3);
        this.ownerColor = ownerColor;
    }

    public PieceSet(PieceSet pieceSet){
        int newVillagerSetSize = pieceSet.villagerSet.size();
        int newTotoroSetSize = pieceSet.totoroSet.size();
        this.villagerSet = new ArrayList<Piece>();
        this.totoroSet = new ArrayList<Piece>();
        this.ownerColor = pieceSet.ownerColor;
        this.generateVillagerSet(ownerColor, newVillagerSetSize);
        this.generateTotoroSet(ownerColor, newTotoroSetSize);
    }

    private void generateVillagerSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.villagerSet.add(new Piece(color, PieceType.VILLAGER));
        }
    }

    private void generateTotoroSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.totoroSet.add(new Piece(color, PieceType.TOTORO));
        }
    }

    public int getNumberOfVillagersRemaining() {
        return villagerSet.size();
    }

    public int getNumberOfTotoroRemaining() {
        return totoroSet.size();
    }

    public Piece placeVillager() throws InvalidMoveException {
        try{
            return villagerSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No villagers remaining in player inventory.");
        }
    }

    public Piece placeMultipleVillagers(int numVillagersToPlace) throws InvalidMoveException {
        if (numVillagersToPlace > getNumberOfVillagersRemaining()) {
            IndexOutOfBoundsException exception = new IndexOutOfBoundsException();
            throw new InvalidMoveException("Insufficient number of villagers in player inventory");
        }
        for (int villager = 0; villager < numVillagersToPlace - 1; villager++) {
            villagerSet.remove(0);
        }
        return villagerSet.remove(0);
    }

    public Piece placeTotoro() throws InvalidMoveException {
        try {
            return totoroSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No totoro remaining in player inventory.");
        }
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

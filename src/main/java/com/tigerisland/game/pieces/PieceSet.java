package com.tigerisland.game.pieces;

import com.tigerisland.game.InvalidMoveException;

import java.util.ArrayList;

public class PieceSet {

    protected ArrayList<Piece> villagerSet;
    protected ArrayList<Piece> shamanSet;
    protected ArrayList<Piece> totoroSet;
    protected ArrayList<Piece> tigerSet;
    protected Color ownerColor;

    public PieceSet(Color ownerColor) {
        this.villagerSet = new ArrayList<Piece>();
        this.totoroSet = new ArrayList<Piece>();
        this.tigerSet = new ArrayList<Piece>();
        this.shamanSet = new ArrayList<Piece>();
        this.ownerColor = ownerColor;
        this.generateVillagerSet(ownerColor, 19);
        this.generateShamanSet(ownerColor, 1);
        this.generateTotoroSet(ownerColor, 3);
        this.generateTigerSet(ownerColor, 2);
    }

    public PieceSet(PieceSet pieceSet){
        int newVillagerSetSize = pieceSet.villagerSet.size();
        int newShamanSetSize = pieceSet.shamanSet.size();
        int newTotoroSetSize = pieceSet.totoroSet.size();
        int newTigerSetSize = pieceSet.tigerSet.size();
        this.villagerSet = new ArrayList<Piece>();
        this.shamanSet = new ArrayList<Piece>();
        this.totoroSet = new ArrayList<Piece>();
        this.tigerSet = new ArrayList<Piece>();
        this.ownerColor = pieceSet.ownerColor;
        this.generateVillagerSet(ownerColor, newVillagerSetSize);
        this.generateShamanSet(ownerColor, newShamanSetSize);
        this.generateTotoroSet(ownerColor, newTotoroSetSize);
        this.generateTigerSet(ownerColor, newTigerSetSize);
    }

    private void generateVillagerSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.villagerSet.add(new Piece(color, PieceType.VILLAGER));
        }
    }

    private void generateShamanSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.shamanSet.add(new Piece(color, PieceType.SHAMAN));
        }
    }

    private void generateTotoroSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.totoroSet.add(new Piece(color, PieceType.TOTORO));
        }
    }

    private void generateTigerSet(Color color, int size) {
        for(int pieceNumber = 1; pieceNumber <= size; pieceNumber++) {
            this.tigerSet.add(new Piece(color, PieceType.TIGER));
        }
    }

    public Piece placeVillager() throws InvalidMoveException {
        try{
            return villagerSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No villagers remaining in game inventory.");
        }
    }

    public Piece placeShaman() throws InvalidMoveException {
        try{
            return shamanSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No shaman remaining in game inventory.");
        }
    }

    public Piece placeMultipleVillagers(int numVillagersToPlace) throws InvalidMoveException {
        if (numVillagersToPlace > getNumberOfRegularVillagersRemaining()) {
            IndexOutOfBoundsException exception = new IndexOutOfBoundsException();
            throw new InvalidMoveException("Insufficient number of villagers in game inventory");
        }
        for (int villager = 1; villager <= numVillagersToPlace - 1 ; villager++) {
            villagerSet.remove(0);
        }
        return villagerSet.remove(0);
    }


    public Piece placeTotoro() throws InvalidMoveException {
        try {
            return totoroSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No totoro remaining in game inventory.");
        }
    }

    public Piece placeTiger() throws InvalidMoveException {
        try {
            return tigerSet.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("No tiger remaining in game inventory");
        }
    }

    public boolean inventoryEmpty(){
        if(totoroSet.size() == 0 && villagerSet.size() == 0 && tigerSet.size() == 0 && shamanSet.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int getNumberOfVillagersRemaining() {
        return villagerSet.size() + shamanSet.size();
    }

    public int getNumberOfRegularVillagersRemaining() {
        return villagerSet.size();
    }

    public boolean shamanAlreadyPlayed() {
        return shamanSet.size() == 0;
    }

    public int getNumberOfTotoroRemaining() {
        return totoroSet.size();
    }

    public int getNumberOfTigersRemaining() {
        return tigerSet.size();
    }

}

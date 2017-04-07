package com.tigerisland.AI;

import com.tigerisland.game.*;

import java.util.ArrayList;

//This is an implementation for the AI that we discussed Sunday-4/2/17.
//It involves placing tiles/settlements in a way so that it is difficult for the opponent to disturb them
// and easy(-ish) for us to place Totoros.

public class AI_TotoroLines extends Player{
    private Board currentBoard;
    private Tile currentTile;

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;

    private TilePlacement myNextTilePlacement;
    private BuildActionType myNextBuildActionType;
    private Location myNextBuildLocation;
    private Location myNextExpansionLocation;

    private ArrayList<Location> plannedSettlementLocations;

    public AI_TotoroLines(Color color, int playerID){
        super(color, playerID);
        plannedSettlementLocations = new ArrayList<Location>();
    }

    public TilePlacement returnTilePlacement(){ return myNextTilePlacement; }
    public BuildActionType returnBuildActionType(){ return myNextBuildActionType; }
    public Location returnBuildLocation(){ return myNextBuildLocation; }
    public Location returnExpansionLocation() { return myNextExpansionLocation; }

    public void decideOnAMove(Tile tile, Board board){
        this.currentTile = tile;
        this.currentBoard = board;
        gatherInfo();
        if(canPlaceTotoro()){
            placeTotoro();
            resetTotoroLine();
        }
        else if(canPlaceTiger()) {
            placeTiger();
        }
        else if(noCurrentLine() || lineIsInterrupted()){
            startNewLine();
        }
        else if(!lineIsInterrupted()){
            extendLine();
        }
        else{
            System.out.println("Shouldn't be an option...");
        }
    }

    private void gatherInfo(){
        this.validTilePlacements = AI_Info.returnValidTilePlacements(currentTile, currentBoard);
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(this.getPlayerColor(), currentBoard);
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(this.getPlayerColor(), currentBoard);
    }

    private boolean canPlaceTotoro(){
        return this.validTotoroPlacements.size()>0 && hasATotoro();
    }

    private boolean hasATotoro(){
        return this.getPieceSet().getNumberOfTotoroRemaining()>0;
    }

    private void placeTotoro(){
        this.myNextBuildActionType = BuildActionType.TOTOROPLACEMENT;
        this.myNextBuildLocation = validTotoroPlacements.get(0);
        this.myNextTilePlacement = validTilePlacements.get(0);
    }

    private void resetTotoroLine(){
        this.plannedSettlementLocations = new ArrayList<Location>();
    }

    private boolean canPlaceTiger(){
        return this.validTigerPlacements.size()>0 && hasATiger();
    }

    private boolean hasATiger(){
        return this.getPieceSet().getNumberOfTigersRemaining()>0;
    }

    private void placeTiger(){
        this.myNextBuildActionType = BuildActionType.TIGERPLACEMENT;
        this.myNextBuildLocation = validTigerPlacements.get(0);
        this.myNextTilePlacement = validTilePlacements.get(0);
    }



    private boolean noCurrentLine(){
        return plannedSettlementLocations.size()==0;
    }

    private void startNewLine(){
        plannedSettlementLocations = new ArrayList<Location>();

        TilePlacement startTilePlacement = chooseStartTilePlacement();
        int xStart = startTilePlacement.getLocation().x + 1;
        int yStart = startTilePlacement.getLocation().y - 1;

        plannedSettlementLocations.add(new Location(xStart,yStart));
        plannedSettlementLocations.add(new Location(xStart,yStart+2));
        plannedSettlementLocations.add(new Location(xStart,yStart+4));
        plannedSettlementLocations.add(new Location(xStart,yStart+1));
        plannedSettlementLocations.add(new Location(xStart,yStart+3));

        myNextTilePlacement = startTilePlacement;
        myNextBuildActionType = BuildActionType.VILLAGECREATION;
        myNextBuildLocation = new Location(plannedSettlementLocations.remove(0));
    }

    private TilePlacement chooseStartTilePlacement(){
        return validTilePlacements.get(validTilePlacements.size()-1);
    }

    private void extendLine(){
        if (plannedSettlementLocations.size() == 4) {
            myNextExpansionLocation = plannedSettlementLocations.get(0);
        }

        if (plannedSettlementLocations.size() == 2) {
            Location nextLocation = plannedSettlementLocations.remove(0);
            this.myNextBuildActionType = BuildActionType.VILLAGEEXPANSION;
            findNextTilePlacement(nextLocation);

        } else {
            Location nextLocation = plannedSettlementLocations.remove(0);
            this.myNextBuildActionType = BuildActionType.VILLAGECREATION;
            this.myNextBuildLocation = nextLocation;
            findNextTilePlacement(nextLocation);
        }
    }

    private void findNextTilePlacement(Location location) {
        if(currentBoard.hexExistsAtLocation(location)){
            this.myNextTilePlacement = validTilePlacements.get(0);
        }
        else{
            this.myNextTilePlacement = placeTileToExtendLine(location);
        }
    }

    private TilePlacement placeTileToExtendLine(Location nextLocation){
        if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,1)),300))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,1)), 300);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 120);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),180))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 180);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,-1)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,-1)), 120);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,0)),0))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,0)), 0);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(0,-1)),60))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(0,-1)), 60);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(0,1)),240))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(0,1)), 240);
        else
            startNewLine();
            return myNextTilePlacement;
    }

    private boolean lineIsInterrupted(){
        for(Location loc : plannedSettlementLocations){
            Hex hex;
            if(currentBoard.hexExistsAtLocation(loc)) {
                hex = currentBoard.hexAt(loc);
                if (!hex.isNotVolcano() || !hex.isEmpty() || hex.getHeight() != 1)
                    return true;
            }
        }
        return false;
    }
}

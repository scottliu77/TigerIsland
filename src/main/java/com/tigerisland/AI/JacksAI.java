package com.tigerisland.AI;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;


public class JacksAI extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATiger;
    private ArrayList<TilePlacement> tilePlacementsThatSetUpPlayerForTotoroPlacement;
    private ArrayList<TilePlacement> tilePlacementsThatCutTotoroOffOfMostOfSettlement;
    private Board tempBoard;

    private Location myNextExpansionLocation;

    private ArrayList<Location> plannedSettlementLocations;

    public JacksAI(){
        plannedSettlementLocations = new ArrayList<Location>();
    }

    public void decideOnMove(){
        tempBoard = turnState.getBoard();
        try {
            gatherTilePlacementInfo();
        } catch (InvalidMoveException e) {
        }
        if(tilePlacementsThatSetUpPlayerForTotoroPlacement.size() > 0) {
            tilePlacement = tilePlacementsThatSetUpPlayerForTotoroPlacement.get(0);
        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.size() > 0) {
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.get(0);
        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.size() > 0) {
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.get(0);
        }
        else if(tilePlacementsThatCutTotoroOffOfMostOfSettlement.size() > 0) {
            tilePlacement = tilePlacementsThatCutTotoroOffOfMostOfSettlement.get(0);
        }
        if(tilePlacement != null) {
            try {
                tempBoard.placeTile(tilePlacement);
            } catch (InvalidMoveException e) {

            }
        }
        gatherBuildActionInfo();
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

    private void gatherTilePlacementInfo() throws InvalidMoveException{
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTotoroPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATiger = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTigerPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsThatSetUpPlayerForTotoroPlacement = AI_Info.findTilePlacementsThatEnableTotoroPlacementForSamePlayer(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsThatCutTotoroOffOfMostOfSettlement = AI_Info.findTilePlacementsThatCutTotoroOffOfMostOfSettlement(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard, 3);
    }

    private void gatherBuildActionInfo() {
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer().getPlayerColor(), tempBoard);
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), tempBoard);
    }


    private boolean canPlaceTotoro(){
        return this.validTotoroPlacements.size()>0 && hasATotoro();
    }

    private boolean hasATotoro(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTotoroRemaining()>0;
    }

    private void placeTotoro(){
        buildActionType = BuildActionType.TOTOROPLACEMENT;
        buildLocation = validTotoroPlacements.get(0);
        tilePlacement = validTilePlacements.get(0);
    }

    private void resetTotoroLine(){
        this.plannedSettlementLocations = new ArrayList<Location>();
    }

    private boolean canPlaceTiger(){
        return this.validTigerPlacements.size()>0 && hasATiger();
    }

    private boolean hasATiger(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining()>0;
    }

    private void placeTiger(){
        buildActionType = BuildActionType.TIGERPLACEMENT;
        buildLocation = validTigerPlacements.get(0);
        tilePlacement = validTilePlacements.get(0);
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

        tilePlacement = startTilePlacement;
        buildActionType = BuildActionType.VILLAGECREATION;
        buildLocation = new Location(plannedSettlementLocations.remove(0));
    }

    private TilePlacement chooseStartTilePlacement(){
        return validTilePlacements.get(validTilePlacements.size()-1);
    }

    private void extendLine(){
       /* if (plannedSettlementLocations.size() == 4) {
            myNextExpansionLocation = plannedSettlementLocations.get(0);
        }

        if (plannedSettlementLocations.size() == 2) {
            Location nextLocation = plannedSettlementLocations.remove(0);
            this.buildActionType = BuildActionType.VILLAGEEXPANSION;
            findNextTilePlacement(nextLocation);
            // TODO replace with an actual solution
            expandTerrain = tilePlacement.getTile().getLeftHex().getHexTerrain();

        } else {*/
            Location nextLocation = plannedSettlementLocations.remove(0);
            this.buildActionType = BuildActionType.VILLAGECREATION;
            this.buildLocation = nextLocation;
            findNextTilePlacement(nextLocation);
       // }
    }

    private void findNextTilePlacement(Location location) {
        if(tempBoard.hexExistsAtLocation(location)){
            this.tilePlacement = validTilePlacements.get(0);
        }
        else{
            this.tilePlacement = placeTileToExtendLine(location);
        }
    }

    private TilePlacement placeTileToExtendLine(Location nextLocation){
        Board currentBoard = tempBoard;
        Tile currentTile = turnState.getCurrentTile();

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
        return tilePlacement;
    }

    private boolean lineIsInterrupted(){
        for(Location loc : plannedSettlementLocations){
            Hex hex;
            if(tempBoard.hexExistsAtLocation(loc)) {
                hex = tempBoard.hexAt(loc);
                if (!hex.isNotVolcano() || !hex.isEmpty() || hex.getHeight() != 1)
                    return true;
            }
        }
        return false;
    }

    public Location returnExpansionLocation() { return myNextExpansionLocation; }

}


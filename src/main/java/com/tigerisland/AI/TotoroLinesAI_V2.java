package com.tigerisland.AI;

import com.tigerisland.game.*;

import java.util.ArrayList;

//Same as TotoroLines, but this goes backwards

public class TotoroLinesAI_V2 extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;

    private Location myNextExpansionLocation;

    private ArrayList<Location> plannedSettlementLocations;

    public TotoroLinesAI_V2(){
        plannedSettlementLocations = new ArrayList<Location>();
    }

    public void decideOnMove(){
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
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), turnState.getBoard());
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer().getPlayerColor(), turnState.getBoard());
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), turnState.getBoard());
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
        return this.validTigerPlacements.size() > 0 && hasATiger();
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
        plannedSettlementLocations.add(new Location(xStart,yStart-2));
        plannedSettlementLocations.add(new Location(xStart,yStart-4));
        plannedSettlementLocations.add(new Location(xStart,yStart-1));
        plannedSettlementLocations.add(new Location(xStart,yStart-3));

        tilePlacement = startTilePlacement;
        buildActionType = BuildActionType.VILLAGECREATION;
        buildLocation = new Location(plannedSettlementLocations.remove(0));
    }

    private TilePlacement chooseStartTilePlacement(){
        return validTilePlacements.get(validTilePlacements.size()-1);
    }

    private void extendLine(){
        Location nextLocation = plannedSettlementLocations.remove(0);
        this.buildActionType = BuildActionType.VILLAGECREATION;
        this.buildLocation = nextLocation;
        findNextTilePlacement(nextLocation);
    }

    private void findNextTilePlacement(Location location) {
        if(turnState.getBoard().hexExistsAtLocation(location)){
            this.tilePlacement = validTilePlacements.get(0);
        }
        else{
            this.tilePlacement = placeTileToExtendLine(location);
        }
    }

    private TilePlacement placeTileToExtendLine(Location nextLocation){
        Board currentBoard = turnState.getBoard();
        Tile currentTile = turnState.getCurrentTile();

        if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,0)),300))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,0)), 300);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,-1)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,-1)), 120);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,1)),240))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,1)), 240);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),180))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 180);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,1)),300))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,1)), 300);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 120);
        else
            startNewLine();
        return tilePlacement;
    }

    private boolean lineIsInterrupted(){
        for(Location loc : plannedSettlementLocations){
            Hex hex;
            if(turnState.getBoard().hexExistsAtLocation(loc)) {
                hex = turnState.getBoard().hexAt(loc);
                if (!hex.isNotVolcano() || !hex.isEmpty() || hex.getHeight() != 1)
                    return true;
            }
        }
        return false;
    }
}

package com.tigerisland.AI;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.board.Board;
import com.tigerisland.game.board.Location;
import com.tigerisland.game.board.SettlementAndTerrainListPair;
import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.moves.BuildActionType;
import com.tigerisland.game.moves.TilePlacement;

import java.util.ArrayList;

import static com.tigerisland.messenger.Adapter.convertLocationAxialToCube;

public class TigerFormAI extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;

    private ArrayList<Location> plannedTileLocations;
    private ArrayList<Location> plannedSettlementLocations;
    private ArrayList<Integer> plannedTileRotations;

    private Board boardCopy;

    public TigerFormAI() {
        plannedTileLocations = new ArrayList<Location>();
        plannedTileRotations = new ArrayList<Integer>();
        plannedSettlementLocations = new ArrayList<Location>();
    }

    public void decideOnMove() {
        gatherInfo();
        if (canPlaceTiger()) {
            placeTiger();
            resetTigerFormation();
        }
        else if (canPlaceTotoro()) {
            placeTotoro();
        }
        else if (noCurrentLine() || lineIsInterrupted()) {
            startNewLine();
            System.out.println("start new");
        }
        else if (!lineIsInterrupted()) {
            extendFormation();
            System.out.println("continue");

        }
        else {
            System.out.println("Shouldn't be an option...");
        }
    }

    private void gatherInfo() {
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), turnState.getBoard());
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer(), turnState.getBoard());
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), turnState.getBoard());
//Is this needed?
        this.boardCopy = new Board(turnState.getBoard());
        //ToDo bro you made a copy for checks but don't update copy...wait it does?
    }

    private boolean canPlaceTiger() {
        return this.validTigerPlacements.size() > 0 && hasATiger();
    }

    private boolean hasATiger() {
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining() > 0;
    }

    private void placeTiger() {
        buildActionType = BuildActionType.TIGERPLACEMENT;
        buildLocation = validTigerPlacements.get(0);
        tilePlacement = validTilePlacements.get(0);
    }

    private void resetTigerFormation(){
        this.plannedTileLocations = new ArrayList<Location>();
        this.plannedTileRotations = new ArrayList<Integer>();
        this.plannedSettlementLocations = new ArrayList<Location>();
    }

    private boolean canPlaceTotoro(){
        return this.validTotoroPlacements.size() > 0 && hasATotoro();
    }

    private boolean hasATotoro(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTotoroRemaining() > 0;
    }

    private void placeTotoro(){
        buildActionType = BuildActionType.TOTOROPLACEMENT;
        buildLocation = validTotoroPlacements.get(0);
        tilePlacement = validTilePlacements.get(0);
    }
    private boolean noCurrentLine() {
        return plannedTileLocations.size() == 0;
    }

    private void startNewLine() {

        Location loco;
        Location bella;

        plannedTileLocations = new ArrayList<Location>();

        TilePlacement startTilePlacement = chooseStartTilePlacement();
        int xStart = startTilePlacement.getLocation().x;
        int yStart = startTilePlacement.getLocation().y;
        plannedSettlementLocations.add(new Location(xStart, yStart + 1));

        plannedTileLocations.add(new Location(xStart + 1, yStart - 2));
        plannedTileRotations.add(0);
        plannedSettlementLocations.add(new Location(xStart + 1, yStart));
        loco = convertLocationAxialToCube(plannedTileLocations.get(plannedTileLocations.size() - 1));
        System.out.println("tile2: " + loco.x + " " + loco.y + " " + loco.z);
        bella = convertLocationAxialToCube(plannedSettlementLocations.get(plannedSettlementLocations.size()-  1));
        System.out.println("                     intended Set: " + bella.x + " " + bella.y + " " + bella.z);

        plannedTileLocations.add(new Location(xStart + 2, yStart - 4));
        plannedTileRotations.add(0);
        plannedSettlementLocations.add(new Location(xStart + 3, yStart - 4));
        loco = convertLocationAxialToCube(plannedTileLocations.get(plannedTileLocations.size() - 1));
        System.out.println("tile3: " + loco.x + " " + loco.y + " " + loco.z);
        bella = convertLocationAxialToCube(plannedSettlementLocations.get(plannedSettlementLocations.size()-  1));
        System.out.println("                     intended Set: " + bella.x + " " + bella.y + " " + bella.z);

        plannedTileLocations.add(new Location(xStart, yStart));
        plannedTileRotations.add(300);
        plannedSettlementLocations.add(new Location(xStart + 1, yStart));
        loco = convertLocationAxialToCube(plannedTileLocations.get(plannedTileLocations.size() - 1));
        System.out.println("tile4: " + loco.x + " " + loco.y + " " + loco.z);
        bella = convertLocationAxialToCube(plannedSettlementLocations.get(plannedSettlementLocations.size()-  1));
        System.out.println("                     intended Set: " + bella.x + " " + bella.y + " " + bella.z);

        plannedTileLocations.add(new Location(xStart + 1, yStart - 2));
        plannedTileRotations.add(300);
        plannedSettlementLocations.add(new Location(xStart + 1, yStart - 1));
        loco = convertLocationAxialToCube(plannedTileLocations.get(plannedTileLocations.size() - 1));
        System.out.println("tile5: " + loco.x + " " + loco.y + " " + loco.z);
        bella = convertLocationAxialToCube(plannedSettlementLocations.get(plannedSettlementLocations.size()-  1));
        System.out.println("                     intended Set: " + bella.x + " " + bella.y + " " + bella.z);

        plannedTileLocations.add(new Location(xStart + 1, yStart - 2));
        plannedTileRotations.add(0);
        plannedSettlementLocations.add(new Location(xStart + 1, yStart - 1));
        loco = convertLocationAxialToCube(plannedTileLocations.get(plannedTileLocations.size() - 1));
        System.out.println("tile6: " + loco.x + " " + loco.y + " " + loco.z);
        bella = convertLocationAxialToCube(plannedSettlementLocations.get(plannedSettlementLocations.size()-  1));
        System.out.println("                     intended Set: " + bella.x + " " + bella.y + " " + bella.z);

        tilePlacement = startTilePlacement;
        try {
            boardCopy.placeTile(tilePlacement);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        buildActionType = BuildActionType.VILLAGECREATION;
        buildLocation = new Location(plannedSettlementLocations.remove(0));
    }

    private TilePlacement chooseStartTilePlacement() {
        TilePlacement curr = validTilePlacements.get(validTilePlacements.size()-1);
        for (TilePlacement tilePlacement : validTilePlacements){
            if (tilePlacement.getRotation() == 0 && tilePlacement.getLocation().y < curr.getLocation().y){
                curr = tilePlacement;
            }
        }
        return curr;
    }

    private void extendFormation() {
        findNextTilePlacement();
        findNextBuildMove();
    }

    private void findNextTilePlacement() {
        //ToDo Problemo lies here
        if (isValidTilePlacement(plannedTileLocations.get(0), plannedTileRotations.get(0))){
            this.tilePlacement = new TilePlacement(turnState.getCurrentTile(), plannedTileLocations.remove(0), plannedTileRotations.remove(0));
    /*        try {
                boardCopy.placeTile(tilePlacement);
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }*/
        }
        else {
            startNewLine();
        /*    this.tilePlacement = chooseStartTilePlacement();
            try {
                boardCopy.placeTile(tilePlacement);
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void findNextBuildMove(){
      //  ArrayList<Location> validVillageLoc = AI_Info.returnValidVillagePlacements(turnState.getBoard());
        ArrayList<Location> validVillageLoc = AI_Info.returnValidVillagePlacements(boardCopy);

        ArrayList<SettlementAndTerrainListPair> expandableSetAndTer = AI_Info.returnValidVillageExpansions(turnState.getCurrentPlayer(), boardCopy);

        Location nextLoc = plannedSettlementLocations.remove(0);
        int step = 6 - plannedSettlementLocations.size();
        System.out.println("step: " + step);
        System.out.println("nextLoc: " + nextLoc.x + ", " + nextLoc.y);
        Location location = convertLocationAxialToCube(nextLoc);
        System.out.println("nextLocCube: " + location.x + ", " +location.y + ", " + location.z);

        if (step == 2 || step == 3){
            //ToDo bruh location checks
            if(isValidSettlementLocation(nextLoc)){
       //     if (validVillageLoc.contains(nextLoc)){
                this.buildActionType = BuildActionType.VILLAGECREATION;
                this.buildLocation = nextLoc;
            }
        }
        else if (step == 4 || step == 5){
         //   Terrain intendedTer = turnState.getBoard().hexAt(nextLoc).getHexTerrain();
            Terrain intendedTer = boardCopy.hexAt(nextLoc).getHexTerrain();
            for (SettlementAndTerrainListPair sAndT : expandableSetAndTer){
                if (sAndT.getTerrainList().contains(intendedTer)){
                    this.buildActionType = BuildActionType.VILLAGEEXPANSION;
                    expandTerrain = intendedTer;
                }
            }
        }
        else{
            if (validTigerPlacements.contains(nextLoc)){
                this.buildActionType = BuildActionType.TIGERPLACEMENT;
                this.buildLocation = nextLoc;
            }
        }
    }

    private boolean isValidSettlementLocation(Location location){
        ArrayList<Location> validVillageLoc = AI_Info.returnValidVillagePlacements(boardCopy);
        int x0 = location.x;
        int y0 = location.y;

        for (int i = 0; i < validVillageLoc.size(); i++){
            int validX = validVillageLoc.get(i).x;
            int validY = validVillageLoc.get(i).y;
            if (validX == x0 && validY == y0){
                return true;
            }
        }
        return false;
    }

    private boolean lineIsInterrupted() {

        if (!isValidTilePlacement(plannedTileLocations.get(0), plannedTileRotations.get(0))){
            System.out.println("bro methinks it's interrupted 1");
            return true;
        }

 /*       try {
            boardCopy.placeTile(turnState.getCurrentTile(), plannedTileLocations.get(0), plannedTileRotations.get(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }*/

       //ToDo: does not account for upper level
        Location loc = plannedSettlementLocations.get(0);
        if (!turnState.getBoard().hexAt(loc).isEmpty()) {
            System.out.println("bro methinks it's interrupted 2");
            return true;
        }
        return false;
    }

    private boolean isValidTilePlacement(Location location, int rotation){
        int x0 = location.x;
        int y0 = location.y;

        for (int i = 0; i < validTilePlacements.size(); i++){
            TilePlacement tp = validTilePlacements.get(i);
           if (tp.getLocation().x == x0 && tp.getLocation().y == y0 && tp.getRotation() == rotation){
                return true;
           }
        }
        return false;
    }

}

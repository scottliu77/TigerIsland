package com.tigerisland.AI;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;
import java.util.ArrayList;
import java.util.Random;


public class RandomAI extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;
    private ArrayList<SettlementAndTerrainListPair> validVillageExpansions;
    private ArrayList<Location> validVillagePlacements;


    private Random random;

    public RandomAI() {
        random = new Random();
    }

    public void decideOnMove() {
        gatherInfo(turnState.getBoard());
        pickRandomTilePlacement();
        Board tempBoard = new Board(turnState.getBoard());
        try {
            tempBoard.placeTile(tilePlacement);
        } catch(InvalidMoveException e){

        }
        gatherInfo(tempBoard);
        pickRandomBuildAction();
    }

    private void gatherInfo(Board board){
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), board);
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer(), board);
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), board);
        this.validVillageExpansions = AI_Info.returnValidVillageExpansions(turnState.getCurrentPlayer(), board);
        this.validVillagePlacements = AI_Info.returnValidVillagePlacements(board);
    }

    private void pickRandomTilePlacement() {
        int index = random.nextInt(validTilePlacements.size());
        tilePlacement = validTilePlacements.get(index);
    }

    private void pickRandomBuildAction() {

        int choice = random.nextInt(4);
        // 0 = Totoro placement
        // 1 = Tiger placement
        // 2 = Village Expansion
        // 3 = Village Creation
        boolean doDefault = false;
        switch (choice) {
            case 0:
                if(validTotoroPlacements.size() > 0 && hasATotoro()) {
                    int index = random.nextInt(validTotoroPlacements.size());
                    buildLocation = validTotoroPlacements.get(index);
                    buildActionType = BuildActionType.TOTOROPLACEMENT;
                }
                else {
                    doDefault = true;
                }
                break;
            case 1:
                if(validTigerPlacements.size() > 0 && hasATiger()) {
                    int index = random.nextInt(validTigerPlacements.size());
                    buildLocation = validTigerPlacements.get(index);
                    buildActionType = BuildActionType.TIGERPLACEMENT;
                }
                else {
                    doDefault = true;
                }
                break;
            case 2:
                /*if(validVillageExpansions.size() > 0) {
                    int index = random.nextInt(validVillageExpansions.size());
                    SettlementAndTerrainListPair settlementAndTerrainListPair = validVillageExpansions.get(index);
                    Settlement settlement = settlementAndTerrainListPair.getSettlement();
                    ArrayList<PlacedHex> placedHexes = settlement.getHexesInSettlement();
                    int hexIndex = random.nextInt(placedHexes.size());
                    PlacedHex placedHex = placedHexes.get(hexIndex);
                    buildLocation = placedHex.getLocation();

                    ArrayList<Terrain> terrains = settlementAndTerrainListPair.getTerrainList();
                    int terrainIndex = random.nextInt(terrains.size());
                    expandTerrain = terrains.get(terrainIndex);

                    buildActionType = BuildActionType.VILLAGEEXPANSION;
                }
                else {*/
                    doDefault = true;
                //}
                break;
            case 3:
                int index = random.nextInt(validVillagePlacements.size());
                buildLocation = validVillagePlacements.get(index);
                buildActionType = BuildActionType.VILLAGECREATION;
        }

        if(doDefault) {
            int index = random.nextInt(validVillagePlacements.size());
            buildLocation = validVillagePlacements.get(index);
            buildActionType = BuildActionType.VILLAGECREATION;
        }

    }

    private boolean hasATotoro(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTotoroRemaining()>0;
    }

    private boolean hasATiger(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining()>0;
    }

}

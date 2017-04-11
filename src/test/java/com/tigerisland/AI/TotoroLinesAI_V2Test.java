package com.tigerisland.AI;

import com.tigerisland.GameSettings;
import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TotoroLinesAI_V2Test {

    private Turn turnState;
    private AI myAI;
    private Boolean hasPlacedTotoro;

    @Before
    public void before(){
        GameSettings gameSettings = new GameSettings();
        gameSettings.getPlayerSet().setCurrentPlayer("1");

        Board board = new Board();
        board.placeStartingTile();

        turnState = new Turn(gameSettings, board);
        turnState.updateTurnInformation("1", new Tile(Terrain.GRASS, Terrain.JUNGLE));

        myAI = new TotoroLinesAI_V2();
        hasPlacedTotoro = false;
    }

    @Ignore("Ignoring: WORK IN PROGRESS") @Test
    public void testAI() throws InvalidMoveException{
        for(int ii=0; ii<6; ii++) {
            turnState.updateTurnInformation("1", new Tile(Terrain.GRASS, Terrain.JUNGLE));
            turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);

            try {
                turnState.processMove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            TextGUI.printMap(turnState.getBoard());
        }

        assert(hasPlacedTotoro);
    }

    /*public void decideOnMoveAndTakeTurn() throws InvalidMoveException{
        myAI.pickTilePlacementAndBuildAction(turnState);

        TilePlacement tp = myAI.turnState.getTilePlacement();
        turnState.getBoard().placeTile(tp.getTile(), tp.getLocation(), tp.getRotation());

        BuildActionType bat = myAI.buildActionType;
        if(bat == BuildActionType.VILLAGECREATION) {
            board.createVillage(myAI, myAI.returnBuildLocation());
            board.updateSettlements();
        }
        else if(bat == BuildActionType.VILLAGEEXPANSION){
            board.expandVillage(myAI, myAI.returnExpansionLocation(), Terrain.LAKE);
        }
        else if(bat == BuildActionType.TOTOROPLACEMENT) {
            board.placeTotoro(myAI, myAI.returnBuildLocation());
            hasPlacedTotoro = true;
        }
        else if(bat == BuildActionType.TIGERPLACEMENT)
            board.placeTiger(myAI, myAI.returnBuildLocation());
        else
            assert(false);
    }*/
}

package com.tigerisland.AI;

import com.tigerisland.game.board.Board;
import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.board.TextGUI;
import com.tigerisland.game.board.Tile;
import com.tigerisland.settings.GameSettings;
import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.*;
import com.tigerisland.client.Message;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TotoroLinesAITest {

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

        myAI = new TotoroLinesAI();
        hasPlacedTotoro = false;
    }

    @Ignore("Ignoring: WORK IN PROGRESS") @Test
    public void testAI() throws InvalidMoveException{
        for(int ii=0; ii<6; ii++) {
            turnState.updateTurnInformation("1", new Tile(Terrain.GRASS, Terrain.JUNGLE));
            turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);

            try {
                Message message = new Message("GAME A MOVE 1 PLACE ROCK+LAKE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0");
                turnState.processMove(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            TextGUI.printMap(turnState.getBoard());
        }

        assert(hasPlacedTotoro);
    }

//    public void decideOnMoveAndTakeTurn() throws InvalidMoveException{
//        myAI.pickTilePlacementAndBuildAction(turnState);
//
//        TilePlacement tp = myAI.turnState.getTilePlacement();
//        turnState.getBoard().placeTile(tp.getTile(), tp.getLocation(), tp.getRotation());
//
//        BuildActionType bat = myAI.buildActionType;
//        if(bat == BuildActionType.VILLAGECREATION) {
//            board.createVillage(myAI, myAI.returnBuildLocation());
//            board.updateSettlements();
//        }
//        else if(bat == BuildActionType.VILLAGEEXPANSION){
//            board.expandVillage(myAI, myAI.returnExpansionLocation(), Terrain.LAKE);
//        }
//        else if(bat == BuildActionType.TOTOROPLACEMENT) {
//            board.placeTotoro(myAI, myAI.returnBuildLocation());
//            hasPlacedTotoro = true;
//        }
//        else if(bat == BuildActionType.TIGERPLACEMENT)
//            board.placeTiger(myAI, myAI.returnBuildLocation());
//        else
//            assert(false);
//    }
}

package com.tigerisland.AI;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import org.junit.Before;
import org.junit.Test;

public class AI_TotoroLinesTest {
    private Board board;
    private AI_TotoroLines myAI;
    private Boolean hasPlacedTotoro;

    @Before
    public void before(){
        this.board = new Board();
        board.setUpStartingHexes();
        myAI = new AI_TotoroLines(Color.BLUE,1);
        hasPlacedTotoro = false;
    }

    @Test
    public void testAI() throws InvalidMoveException{
        for(int ii=0; ii<6; ii++)
            decideOnMoveAndTakeTurn();

        assert(hasPlacedTotoro);
    }
    public void decideOnMoveAndTakeTurn() throws InvalidMoveException{
        TextGUI.printMap(board);
        myAI.decideOnAMove(new Tile(Terrain.LAKE,Terrain.LAKE), board);

        TilePlacement tp = myAI.returnTilePlacement();
        board.placeTile(tp.getTile(), tp.getLocation(), tp.getRotation());

        BuildActionType bat = myAI.returnBuildActionType();
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
    }
}

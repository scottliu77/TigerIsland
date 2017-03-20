package com.tigerisland;

import org.junit.Test;
import org.junit.Before;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player playerWhite;

    @Before
    public void createPlayer() {
        this.playerWhite = new Player(Color.WHITE);
    }

    @Test
    public void testCanCreatePlayer() {
        assertTrue(playerWhite != null);
    }

    @Test
    public void testCanGetPlayerColor() {
        assertTrue(playerWhite.getPlayerColor().getColorString() == "White");
    }

    @Test
    public void testCanGetPlayerScore() {
        assertTrue(playerWhite.getScore() == 0);
    }

    @Test
    public void testCanIncreasePlayerScore() {
        playerWhite.addPoints(5);
        playerWhite.addPoints(5);
        assertTrue(playerWhite.getScore() == 10);
    }

    @Test
    public void testCanGetPlayerPieceSet() {
        assertTrue(playerWhite.getPieceSet() != null);
    }

    @Test
    public void testCanPlaceVillager() {
        try {
            Piece newTotoro = playerWhite.getPieceSet().placeVillager();
            assertTrue(newTotoro != null);
        } catch (InvalidMoveException exception) {
            assertTrue(false);
        }
    }

    @Test
    public void testCanPlaceTotoro() {
        try {
            Piece newTotoro = playerWhite.getPieceSet().placeTotoro();
            assertTrue(newTotoro != null);
        } catch (InvalidMoveException exception) {
            assertTrue(false);
        }
    }

    @Test
    public void testCannotPlaceMoreTotoroThanPlayerHas() {
        try {
            for(int totoro = 0; totoro < 4; totoro++) {
                Piece newTotoro = playerWhite.getPieceSet().placeTotoro();
            }
            assertTrue(false);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanExpandSettlement() {
        try {
            Piece villagers = playerWhite.getPieceSet().placeMultipleVillagers(5);
            assertTrue(true);
        } catch (InvalidMoveException exception) {
            assertTrue(false);
        }
    }

    @Test
    public void testCannotPlaceMoreVillagesrThanPlayerHas() {
        try {
            Piece villagers = playerWhite.getPieceSet().placeMultipleVillagers(21);
            assertTrue(false);
        } catch (InvalidMoveException exception) {
            assertTrue(true);
        }
    }
}

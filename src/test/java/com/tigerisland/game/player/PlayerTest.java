package com.tigerisland.game.player;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.Piece;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerType;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player playerWhite;

    @Before
    public void createPlayer() {

        this.playerWhite = new Player(Color.WHITE, "1", PlayerType.SAFEAI);

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
        assertTrue(playerWhite.getScore().getScoreValue() == 0);
    }

    @Test
    public void testCanIncreasePlayerScore() throws InvalidMoveException {
        playerWhite.getScore().addPoints(5);
        playerWhite.getScore().addPoints(5);
        assertTrue(playerWhite.getScore().getScoreValue() == 10);
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

    @Test
    public void testCanCreateCopyOfPlayerCheckScore() throws InvalidMoveException {
        Player playerCopy = new Player(playerWhite);
        playerCopy.getScore().addPoints(10);
        assertTrue(playerCopy.getScore().getScoreValue() != playerWhite.getScore().getScoreValue());
    }

    @Test
    public void testCanCreateCopyOfPlayerCheckPieceSet() throws InvalidMoveException {
        Player playerCopy = new Player(playerWhite);
        playerCopy.getPieceSet().placeVillager();
        assertTrue(playerCopy.getPieceSet().getNumberOfVillagersRemaining() != playerWhite.getPieceSet().getNumberOfVillagersRemaining());
    }

    @Test
    public void testCanSaveOriginalPlayerUsingCopy() throws InvalidMoveException {
        Player playerCopy = new Player(playerWhite);
        playerCopy.getPieceSet().placeMultipleVillagers(4);
        playerWhite = playerCopy;
        assertTrue(playerWhite.getPieceSet().getNumberOfVillagersRemaining() == 16);
    }

    @Test
    public void testCanSetAndGetPlayerType() {
        playerWhite.setPlayerType(PlayerType.SAFEAI);
        assertTrue(playerWhite.getPlayerType() == PlayerType.SAFEAI);
    }

    @Test
    public void testCanUpdatePlayerState() {

        Player playerCopy = new Player(Color.ORANGE, "1", PlayerType.SAFEAI);

        playerWhite.updatePlayerState(playerCopy);
        assertTrue(playerCopy.getPlayerColor() == Color.ORANGE);
    }
}

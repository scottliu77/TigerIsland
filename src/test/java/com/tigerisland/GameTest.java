package com.tigerisland;

import com.tigerisland.game.*;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class GameTest {

    private GlobalSettings globalSettings = new GlobalSettings();
    private GameSettings gameSettings;
    private Game game;
    private Player playerBlack = new Player(Color.BLACK);

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        this.game = new Game(1, gameSettings);
    }

    @Before
    public void createPlayerWithNoPieces() {
        try {
            playerBlack.getPieceSet().placeMultipleVillagers(20);
            for (int ii = 0; ii < 3; ii++){
                playerBlack.getPieceSet().placeTotoro();
            }
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }

    @Test
    public void testCanGetBoard() {
        Board retrievedBoard = game.getBoard();
        assertTrue(retrievedBoard != null);
    }

    @Test
    public void testCanTakeAnotherTurn() {

    }

    @Test
    public void testCanGetGameSettings() {
        GameSettings retrievedGameSettings = game.getGameSettings();
        assertTrue(retrievedGameSettings != null);
    }

    @Test
    public void testCanGetGameID() {
        assertTrue(game.getGameID() == 1);
    }

    @Test
    public void testCanGetMoveID() {
        assertTrue(game.getMoveID() == 1);
    }

}

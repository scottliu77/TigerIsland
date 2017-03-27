package com.tigerisland;

import com.tigerisland.game.Color;
import com.tigerisland.game.Game;
import com.tigerisland.game.Player;
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

}

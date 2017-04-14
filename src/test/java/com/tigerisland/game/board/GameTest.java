package com.tigerisland.game.board;

import com.tigerisland.game.Game;
import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerType;
import com.tigerisland.settings.GameSettings;
import com.tigerisland.settings.GlobalSettings;
import com.tigerisland.client.Message;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class GameTest {

    private GlobalSettings globalSettings = new GlobalSettings();
    private BlockingQueue<Message> inboundMessages;
    private GameSettings gameSettings;
    private Game game;

    private Player playerBlack = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

    private String[] tournamentSettings = new String[]{"--offline", "false", "--turnTime", "1.5", "--ipaddress", "localhost", "--port", "6539", "--username", "username", "--password", "password"};

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.gameSettings.constructPlayerSet();
        this.game = new Game(gameSettings);
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

    private void placeDummyTilePlacementAndBuildInQueue() {
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 0 0 0 1 FOUND SETTLEMENT AT -1 -1 0"));
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }

    @Test
    public void testCanGetGameSettings() {
        GameSettings retrievedGameSettings = game.getGameSettings();
        assertTrue(retrievedGameSettings != null);
    }

    @Test
    public void testCanGetBoard() {
        Board retrievedBoard = game.getBoard();
        assertTrue(retrievedBoard != null);
    }

    @Test
    public void testCanGetGameID() {
        assertTrue(game.getGameID() == "A");
    }

    @Test
    public void testCanPlaceStartingTileOfGame() throws InvalidMoveException {
        game.getBoard().placeStartingTile();
        assertTrue(game.getBoard().placedHexes.size() == 5);
    }

    @Ignore("Ignoring can run game test") @Test
    public void testCanRunGame() {
        Thread gameThread = new Thread(game);
        gameThread.run();
    }
}

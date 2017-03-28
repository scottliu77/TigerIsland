package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Message;
import org.junit.Test;
import org.junit.Before;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class GameTest {

    private GlobalSettings globalSettings = new GlobalSettings();
    private BlockingQueue<Message> inboundMessages;
    private GameSettings gameSettings;
    private Game game;
    private Player playerBlack = new Player(Color.BLACK);

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.gameSettings.setPlayOrder();
        this.game = new Game(1, gameSettings);
        placeDummyTilePlacementAndBuildInQueue();
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
        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE RG AT 0 0 60"));
        inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD villager AT 0 1"));
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }

    @Test
    public void testCanSafelyPackageGameState() throws InvalidMoveException, InterruptedException {
        Turn newTurn = game.packageTurnState();
        newTurn.getPlayer().getScore().addPoints(1);
        assertTrue(game.getGameSettings().getPlayerSet().getCurrentPlayer().getScore().getScoreValue() == 0);
    }

    @Test
    public void testCanSafelyUnpackageGameState() throws InvalidMoveException, InterruptedException {
        Turn newTurn = game.packageTurnState();
        newTurn.getPlayer().getScore().addPoints(1);
        game.unpackageTurnState(newTurn);
        assertTrue(game.getGameSettings().getPlayerSet().getCurrentPlayer().getScore().getScoreValue() == 1);
    }

    @Test
    public void testCanTakeAnotherTurn() throws InvalidMoveException, InterruptedException {
        game.takeAnotherTurn();
        assertTrue(game.getGameSettings().getPlayerSet().getPlayerList().get(0).getScore().getScoreValue() == 1);
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
        assertTrue(game.getGameID() == 1);
    }

    @Test
    public void testCanGetMoveID() {
        assertTrue(game.getMoveID() == 1);
    }

}

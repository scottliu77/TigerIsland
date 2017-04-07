package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
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
    private Player playerBlack = new Player(Color.BLACK, "1");

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        gameSettings.setGameID("A");
        gameSettings.getPlayerSet().setCurrentPlayer("1");
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.gameSettings.setPlayOrder();
        setAllPlayersToServer();
        this.game = new Game(gameSettings);
    }

    private void setAllPlayersToServer() {
        for(Player player : gameSettings.getPlayerSet().getPlayerList().values()) {
            player.setPlayerType(PlayerType.SERVER);
        }
        gameSettings.getPlayerSet().setCurrentPlayer("1");
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
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCKY+GRASSLANDS AT 0 0 0 1 FOUND SETTLEMENT AT -1 -1 0"));
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }

    @Test
    public void testCanSafelyPackageGameState() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME A MOVE 1 PLAYER 1 PLACE ROCKY+GRASSLANDS AT 0 0 0 1 FOUND SETTLEMENT AT -1 -1 0"));
        Turn newTurn = game.packageTurnState(inboundMessages.remove());
        newTurn.getCurrentPlayer().getScore().addPoints(1);
        assertTrue(game.getGameSettings().getPlayerSet().getCurrentPlayer().getScore().getScoreValue() == 0);
    }

    @Test
    public void testCanSafelyUnpackageGameState() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME A MOVE 1 PLAYER 1 PLACE ROCKY+GRASSLANDS AT 0 0 0 1 FOUND SETTLEMENT AT -1 -1 0"));
        Turn newTurn = game.packageTurnState(inboundMessages.remove());
        newTurn.getCurrentPlayer().getScore().addPoints(1);
        game.unpackageTurnState(newTurn);
        assertTrue(game.getGameSettings().getPlayerSet().getCurrentPlayer().getScore().getScoreValue() == 1);
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
        game.board.placeStartingTile();
        assertTrue(game.getBoard().placedHexes.size() == 5);
    }

    @Ignore("Ignoring can run game test") @Test
    public void testCanRunGame() {
        Thread gameThread = new Thread(game);
        gameThread.run();
    }
}

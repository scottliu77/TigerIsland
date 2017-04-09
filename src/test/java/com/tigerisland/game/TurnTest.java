package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertTrue;

public class TurnTest {

    private Player player;
    private Board board;
    private Turn turn;
    private GameSettings gameSettings;
    private BlockingQueue<Message> inboundMessages;

    @Before
    public void createTurnObject() {

        player = new Player(Color.BLACK,"1", PlayerType.SAFEAI);
        board = new Board();
        gameSettings = new GameSettings();
        gameSettings.getPlayerSet().setCurrentPlayer("1");

        turn = new Turn(gameSettings, board);
        turn.updateTurnInformation("1", new Tile(Terrain.GRASS, Terrain.JUNGLE), "1");
        inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+LAKE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0"));
    }

    @Test
    public void testCanCreateTurn() {
        assertTrue(turn != null);
    }

    @Test
    public void testCanUpdateAndGetTilePlacement() throws InvalidMoveException, InterruptedException {
        turn.processMove();
        TilePlacement tilePlacement = turn.getTilePlacement();
        assertTrue(tilePlacement != null);
    }

    @Test
    public void testCanUpdateAndGetBuildAction() throws InterruptedException, InvalidMoveException {
        turn.processMove();
        BuildAction buildAction = turn.getBuildAction();
        assertTrue(buildAction != null);
    }

    @Test
    public void testCanUpdateAndGetPlayer() throws InvalidMoveException {
        turn.getCurrentPlayer().getScore().addPoints(5);
        assertTrue(turn.getCurrentPlayer().getScore().getScoreValue() == 5);
    }

    @Test
    public void testCanUpdateAndGetBoard() throws InvalidMoveException {
        Tile tile = new Tile(Terrain.GRASS, Terrain.GRASS);
        Location loc = new Location(0, 0);
        turn.getBoard().placeTile(tile, loc, 0);
        assertTrue(turn.getBoard().hexExistsAtLocation(new Location(0, 0)));
    }

    @Test
    public void testCanGetGameSettings() {
        assertTrue(turn.getGameSettings() != null);
    }

    @Test
    public void testCanGetMoveID() {
        assertTrue(turn.getMoveID() != null);
    }

    @Test
    public void testCanGetCurrentTileID() {
        assertTrue(turn.getCurrentTile() != null);
    }

}

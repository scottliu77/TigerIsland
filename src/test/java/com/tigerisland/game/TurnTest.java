package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
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
        player = new Player(Color.BLACK);
        board = new Board();
        turn = new Turn(player, board);
        gameSettings = new GameSettings(new GlobalSettings());
        inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE ROCKY+LAKE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0"));
    }

    @Test
    public void testCanCreateTurn() {
        assertTrue(turn != null);
    }

    @Test
    public void testCanUpdateAndGetTilePlacement() throws InvalidMoveException, InterruptedException {
        turn.updateTurnState(new TurnInfo(1, gameSettings) );
        TilePlacement tilePlacement = turn.getTilePlacement();
        assertTrue(tilePlacement != null);
    }

    @Test
    public void testCanUpdateAndGetBuildAction() throws InterruptedException, InvalidMoveException {
        turn.updateTurnState(new TurnInfo(1, gameSettings));
        BuildAction buildAction = turn.getBuildAction();
        assertTrue(buildAction != null);
    }

    @Test
    public void testCanUpdateAndGetPlayer() throws InvalidMoveException {
        turn.getPlayer().getScore().addPoints(5);
        assertTrue(turn.getPlayer().getScore().getScoreValue() == 5);
    }

    @Test
    public void testCanUpdateAndGetBoard() throws InvalidMoveException {
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS);
        Location loc = new Location(0, 0);
        turn.getBoard().placeTile(tile, loc, 0);
        assertTrue(turn.getBoard().hexExistsAtLocation(new Location(0, 0)));
    }




}

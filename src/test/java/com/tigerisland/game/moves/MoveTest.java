package com.tigerisland.game.moves;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.Turn;
import com.tigerisland.game.board.*;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerType;
import com.tigerisland.game.player.Score;
import com.tigerisland.settings.GameSettings;
import com.tigerisland.client.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertTrue;

// Your handy-dandy debugging friend
//         TextGUI.printMap(turn.getBoard());

public class MoveTest {

    private Player player;
    private Board board;
    private Turn turn;
    private GameSettings gameSettings;
    private BlockingQueue<Message> inboundMessages;

    @Before
    public void createMoveTargets() throws InterruptedException, InvalidMoveException {

        player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        board = new Board();
        createBasicMocks();
        gameSettings = new GameSettings();
        gameSettings.getPlayerSet().setCurrentPlayer("1");
        turn = new Turn(gameSettings, board);
        turn.updateTurnInformation("1", new Tile(Terrain.GRASS, Terrain.JUNGLE));
        inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
    }

    private void createBasicMocks() {
        try {
             Tile tile1 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
            Location loc1 = new Location(0,0);
            board.placeTile(tile1, loc1, 60);

            Tile tile2 = new Tile(Terrain.ROCK,Terrain.ROCK);
            Location loc2 = new Location(-1,0);
            board.placeTile(tile2, loc2, 240);

            Tile tile3 = new Tile(Terrain.GRASS,Terrain.GRASS);
            Location loc3 = new Location(1,0);
            board.placeTile(tile3, loc3, 240);

            Tile tile4 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
            Location loc4 = new Location(1,-2);
            board.placeTile(tile4, loc4, 240);

            Tile tile5 = new Tile(Terrain.LAKE,Terrain.LAKE);
            Location loc5 = new Location(0,-2);
            board.placeTile(tile5, loc5, 180);

            Tile tile6 = new Tile(Terrain.LAKE,Terrain.LAKE);
            Location loc6 = new Location(2,-2);
            board.placeTile(tile6, loc6, 300);

            Tile tile7 = new Tile(Terrain.LAKE,Terrain.LAKE);
            Location loc7 = new Location(0,0);
            board.placeTile(tile7, loc7, 300);

            Tile tile8 = new Tile(Terrain.ROCK,Terrain.ROCK);
            Location loc8 = new Location(0,-2);
            board.placeTile(tile8, loc8, 60);

            Tile tile9 = new Tile(Terrain.ROCK,Terrain.ROCK);
            Location loc9 = new Location(1,-2);
            board.placeTile(tile9, loc9, 300);

            Tile tile10 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
            Location loc10 = new Location(1,-2);
            board.placeTile(tile10, loc10, 60);

        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanPlaceTile() throws InvalidMoveException, InterruptedException {
        Board oldBoard = new Board(turn.getBoard());

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT 3 -3 0"));
        turn.processMove(inboundMessages.remove());
        turn = Move.placeTile(turn);

        assertTrue(oldBoard != turn.getBoard());
    }

    @Test
    public void testCanCreateVillage() throws InvalidMoveException, InterruptedException {
        createInitialVillage();

        assertTrue(turn.getCurrentPlayer().getPieceSet().getNumberOfVillagersRemaining() == 19);
    }

    private void createInitialVillage() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT -2 -1 3"));
        turn.processMove(inboundMessages.remove());

        turn = Move.placeTile(turn);
        turn = Move.takeBuildAction(turn);

    }

    @Test
    public void testCanCreateVillageAndGetAdjustedScore() throws InvalidMoveException, InterruptedException {
        createInitialVillage();

        assertTrue(turn.getCurrentPlayer().getScore().getScoreValue() == Score.VILLAGER_POINT_VALUE);
    }

    @Test
    public void testCanExpandVillage() throws InvalidMoveException, InterruptedException {
        createInitialVillage();

        TextGUI.printMap(turn.getBoard());
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPANDED SETTLEMENT AT -2 -1 3 ROCK"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getCurrentPlayer().getPieceSet().getNumberOfVillagersRemaining() == 15);
    }

    @Test
    public void testCanExpandVillageAndGetAdjustedScore() throws InvalidMoveException, InterruptedException {
        createInitialVillage();

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPANDED SETTLEMENT AT -2 -1 3 ROCK"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getCurrentPlayer().getScore().getScoreValue() == 5 * Score.VILLAGER_POINT_VALUE);
    }

    @Test
    public void testCanPlaceTotoro() throws InterruptedException, InvalidMoveException {

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT -2 -1 3"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);


        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPAND SETTLEMENT AT -2 -1 3 LAKE"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPAND SETTLEMENT AT -2 -1 3 ROCK"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 BUILD TOTORO SANCTUARY AT 0 -3 3"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getCurrentPlayer().getPieceSet().getNumberOfTotoroRemaining() == 2);
    }

    @Test
    public void testCanPlaceTotoroAndGetAdjustedScore() throws InterruptedException, InvalidMoveException {

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT -2 -1 3"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);


        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPAND SETTLEMENT AT -2 -1 3 LAKE"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 EXPAND SETTLEMENT AT -2 -1 3 ROCK"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        int preTotoroScore = turn.getCurrentPlayer().getScore().getScoreValue();

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 BUILD TOTORO SANCTUARY AT 0 -3 3"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);


        assertTrue(turn.getCurrentPlayer().getScore().getScoreValue() - preTotoroScore == Score.TOTORO_POINT_VALUE);
    }

    @Test
    public void testCanPlaceTiger() throws InterruptedException, InvalidMoveException {

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT 1 -2 1"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 BUILD TIGER PLAYGROUND AT 0 -1 1"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining() == 1);

    }

    @Test
    public void testCanPlaceTigerAndGetAdjustedScore() throws InterruptedException, InvalidMoveException {

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 FOUND SETTLEMENT AT 1 -2 1"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCK+GRASS AT 2 -2 0 2 BUILD TIGER PLAYGROUND AT 0 -1 1"));
        turn.processMove(inboundMessages.remove());
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getCurrentPlayer().getScore().getScoreValue() == Score.TIGER_POINT_VALUE + Score.VILLAGER_POINT_VALUE);

    }
}

package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertTrue;

public class MoveTest {

    private Player player;
    private Board board;
    private Turn turn;
    private BlockingQueue<Message> inboundMessages;

    @Before
    public void createMoveTargets() throws InterruptedException, InvalidMoveException {
        player = new Player(Color.BLACK);
        board = new Board();
        turn = new Turn(player, board);
        inboundMessages = new LinkedBlockingQueue<Message>();
        createBasicMocks();
    }

    private void createBasicMocks() {
        try {
             Tile tile1 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
            Location loc1 = new Location(0,0);
            board.placeTile(tile1, loc1, 60);

            Tile tile2 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
            Location loc2 = new Location(-1,0);
            board.placeTile(tile2, loc2, 240);

            Tile tile3 = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);
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

            Tile tile8 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
            Location loc8 = new Location(0,-2);
            board.placeTile(tile8, loc8, 60);

            Tile tile9 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
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
        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE RG AT 2 0 60"));
        turn.updateTilePlacement(1, 1, inboundMessages);
        turn = Move.placeTile(turn);
        assertTrue(board != turn.getBoard());
    }

    @Test
    public void testCanCreateVillage() throws InvalidMoveException, InterruptedException {
        createInitialVillage();
        assertTrue(turn.getPlayer().getPieceSet().getNumberOfVillagersRemaining() == 19);
    }

    private void createInitialVillage() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD villager AT 1 -3"));
        turn.updatedBuildAction(1, 1, inboundMessages);
        turn = Move.takeBuildAction(turn);
    }

    @Test
    public void testCanExpandVillage() throws InvalidMoveException, InterruptedException {
        createInitialVillage();

        inboundMessages.add(new Message("GAME 1 MOVE 1 EXPAND 1 -3 AT 2 -3"));
        turn.updatedBuildAction(1, 1, inboundMessages);
        turn = Move.takeBuildAction(turn);

        assertTrue(turn.getPlayer().getPieceSet().getNumberOfVillagersRemaining() == 17);
        TextGUI.printMap(turn.getBoard());
    }

    @Test
    public void testCanPlaceTotoro() throws InterruptedException {
        try {
            inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD totoro AT 0 0"));
            turn.updatedBuildAction(1, 1, inboundMessages);
            turn = Move.takeBuildAction(turn);
            assertTrue(false);
        } catch (InvalidMoveException exception) {
            assertTrue(exception.getMessage().equals("Cannot place a piece on a volcano hex"));
        }
    }

    @Test
    public void testCanPlaceTiger() throws InterruptedException {
        try{
            inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD tiger AT 0 0"));
            turn.updatedBuildAction(1, 1, inboundMessages);
            turn = Move.takeBuildAction(turn);
            assertTrue(false);
        } catch(InvalidMoveException exception) {
            assertTrue(exception.getMessage().equals("Cannot place a piece on a volcano hex"));
        }
    }

}
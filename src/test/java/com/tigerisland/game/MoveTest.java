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
    }

    @Test
    public void testCanPlaceTile() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE RG AT 0 0 0"));
        turn.updateTilePlacement(1, 1, inboundMessages);
        turn = Move.placeTile(turn);
        assertTrue(board != turn.getBoard());
    }

    @Test
    public void testCanCreateVillage() throws InvalidMoveException, InterruptedException {
        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE RG AT 0 0 0"));
        turn.updateTilePlacement(1, 1, inboundMessages);
        turn = Move.placeTile(turn);

        inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD villager AT 0 1"));
        turn.updatedBuildAction(1, 1, inboundMessages);
        turn = Move.takeBuildAction(turn);
        assertTrue(turn.getPlayer().getPieceSet().getNumberOfVillagersRemaining() == 19);
    }

    // NEED MORE MOCKS (OR TEST ELSEWHERE)

//    @Test
//    public void testCanExpandVillage() throws InvalidMoveException, InterruptedException {
//        inboundMessages.add(new Message("GAME 1 MOVE 1 PLACE RG AT 0 0 0"));
//        turn.updateTilePlacement(1, 1, inboundMessages);
//        turn = Move.placeTile(turn);
//
//        inboundMessages.add(new Message("GAME 1 MOVE 1 EXPAND 0 0 AT 0 1"));
//        turn.updatedBuildAction(1, 1, inboundMessages);
//        turn = Move.takeBuildAction(turn);
//        assertTrue(turn.getPlayer().getPieceSet().getNumberOfVillagersRemaining() == 18);
//    }
//
//    @Test
//    public void testCanPlaceTotoro() throws InvalidMoveException, InterruptedException {
//        inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD totoro AT 0 0"));
//        turn.updatedBuildAction(1, 1, inboundMessages);
//        turn = Move.takeBuildAction(turn);
//        assertTrue(turn.getPlayer().getPieceSet().getNumberOfTotoroRemaining() == 2);
//    }
//
//    @Test
//    public void testCanPlaceTiger() throws InvalidMoveException, InterruptedException {
//        inboundMessages.add(new Message("GAME 1 MOVE 1 BUILD tiger AT 0 0"));
//        turn.updatedBuildAction(1, 1, inboundMessages);
//        turn = Move.takeBuildAction(turn);
//        assertTrue(turn.getPlayer().getPieceSet().getNumberOfTigersRemaining() == 1);
//    }
}

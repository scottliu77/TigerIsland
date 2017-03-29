package com.tigerisland.messenger;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessageTest {

    @Test
    public void testCanCreateMessage() {
        Message message = new Message("manualTesting 1 2 3");
        assertTrue(message != null);
    }

    @Test
    public void testCanSendAndGetGameID() {
        Message message = new Message("GAME 1");
        assertTrue(message.getGameID() == 1);
    }

    @Test
    public void testNotPickingUpBadGameID() {
        Message message = new Message("1");
        assertTrue(message.getGameID() == null);
    }

    @Test
    public void testMessagesNotContainingGameIDsetNull() {
        Message message = new Message("Knock knock server");
        assertTrue(message.getGameID() == null);
    }

    @Test
    public void testCanSendAndGetMoveID() {
        Message message = new Message("GAME 1 MOVE 1");
        assertTrue(message.getMoveID() == 1);
    }

    @Test
    public void testMessagesNotContainingMoveIDsetNull() {
        Message message = new Message("Game 1");
        assertTrue(message.getMoveID() == null);
    }

    @Test
    public void testCanReturnCompleteMessageString() {
        Message message = new Message("echo echo echo");
        assertTrue(message.toString().equals("echo echo echo"));
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetTile() {
        Message message = new Message("PLACE VG AT 0 1 60");
        assertTrue(message.getTileString().equals("VG"));
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetX() {
        Message message = new Message("PLACE VG AT 0 1 60");
        assertTrue(message.getX() == 0);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetY() {
        Message message = new Message("PLACE VG AT 0 1 60");
        assertTrue(message.getY() == 1);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetOrientation() {
        Message message = new Message("PLACE VG AT 0 1 60");
        assertTrue(message.getOrientation() == 60);
    }

    @Test
    public void testCanSendBuildActionAndGetPiece() {
        Message message = new Message("BUILD villager AT 0 1");
        assertTrue(message.getPieceString().equals("villager"));
    }

    @Test
    public void testCanSendBuildActionAndGetX() {
        Message message = new Message("BUILD villager AT 0 1");
        assertTrue(message.getX() == 0);
    }

    @Test
    public void testCanSendBuildActionAndGetY() {
        Message message = new Message("BUILD villager AT 0 1");
        assertTrue(message.getY() == 1);
    }

    @Test
    public void testCanSendExpandActionAndGetX() {
        Message message = new Message("EXPAND 0 1 AT 1 1");
        assertTrue(message.getX() == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetY() {
        Message message = new Message("EXPAND 0 1 AT 1 1");
        assertTrue(message.getY() == 1);
    }

    @Test
    public void testCanSendExpandActionAndGetNewX() {
        Message message = new Message("EXPAND 0 1 AT 1 1");
        assertTrue(message.getNewX() == 1);
    }

    @Test
    public void testCanSendExpandActionAndGetNewY() {
        Message message = new Message("EXPAND 0 1 AT 1 1");
        assertTrue(message.getNewY() == 1);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetType() {
        Message message = new Message("PLACE VG AT 0 1 60");
        assertTrue(message.getMessageType() == MessageType.TILEPLACEMENT);
    }

    @Test
    public void testCanSendCreateVillageMessageAndGetType() {
        Message message = new Message("BUILD villager AT 0 1");
        assertTrue(message.getMessageType() == MessageType.VILLAGECREATION);
    }

    @Test
    public void testCanSendPlaceTotoroMessageAndGetType() {
        Message message = new Message("BUILD totoro AT 0 1");
        assertTrue(message.getMessageType() == MessageType.TOTOROPLACEMENT);
    }

    @Test
    public void testCanSendPlaceTigerMessageAndGetType() {
        Message message = new Message("BUILD tiger AT 0 1");
        assertTrue(message.getMessageType() == MessageType.TIGERPLACEMENT);
    }

    @Test
    public void testCanSendExpandVillageMessageAndGetType() {
        Message message = new Message("EXPAND 0 1 AT 1 1");
        assertTrue(message.getMessageType() == MessageType.VILLAGEXPANSION);
    }

    @Test
    public void testCanGetNegativeIndicesFromMessage() {
        Message message = new Message("PLACE RG AT 0 -2 240");
        assertTrue(message.getY() == -2);
    }

}


package com.tigerisland.messenger;

import com.tigerisland.game.Terrain;
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
        Message message = new Message("GAME A");
        assertTrue(message.getGameID().equals("A"));
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
        assertTrue(message.message.equals("echo echo echo"));
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetTile() {
        Message message = new Message("PLACED ROCKY+LAKE AT 0 0 1 6");
        assertTrue(message.getTile().getLeftHex().getHexTerrain() == Terrain.ROCKY);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetX() {
        Message message = new Message("PLACE ROCKY+LAKE AT 0 0 0 6");
        assertTrue(message.getTileLocation().x == 0);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetY() {
        Message message = new Message("PLACE ROCKY+LAKE AT 0 0 0 6");
        assertTrue(message.getTileLocation().y == 0);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetOrientation() {
        Message message = new Message("PLACE ROCKY+LAKE AT 0 0 0 1");
        assertTrue(message.getOrientation() == 60);
    }

    @Test
    public void testCanSendBuildActionAndGetPiece() {
        Message message = new Message("FOUND SETTLEMENT AT 0 1 0");
        assertTrue(message.getMessageType() == MessageType.FOUNDSETTLEMENT);
    }

    @Test
    public void testCanSendBuildActionAndGetX() {
        Message message = new Message("FOUND SETTLEMENT AT 0 0 0");
        assertTrue(message.getBuildLocation().x == 0);
    }

    @Test
    public void testCanSendBuildActionAndGetY() {
        Message message = new Message("FOUND SETTLEMENT AT 0 0 0");
        assertTrue(message.getBuildLocation().y == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetX() {
        Message message = new Message("EXPANDED SETTLEMENT AT 0 0 0 ROCKY");
        assertTrue(message.getBuildLocation().x == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetY() {
        Message message = new Message("EXPAND SETTLEMENT AT 0 0 0 ROCKY");
        assertTrue(message.getBuildLocation().y == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetTerrainType() {
        Message message = new Message("EXPAND SETTLEMENT AT 0 0 0 ROCKY");
        assertTrue(message.getExpandTerrain() == Terrain.ROCKY);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetType() {
        Message message = new Message("PLACE ROCKY+LAKE AT 0 0 0 3");
        assertTrue(message.getTile() != null);
    }

    @Test
    public void testCanSendCreateVillageMessageAndGetType() {
        Message message = new Message("FOUND SETTLEMENT AT 0 0 0");
        assertTrue(message.getMessageType() == MessageType.FOUNDSETTLEMENT);
    }

    @Test
    public void testCanSendPlaceTotoroMessageAndGetType() {
        Message message = new Message("BUILD TOTORO SANCTUARY AT 0 0 0");
        assertTrue(message.getMessageType() == MessageType.BUILDTOTORO);
    }

    @Test
    public void testCanSendPlaceTigerMessageAndGetType() {
        Message message = new Message("BUILD TIGER PLAYGROUND AT 0 0 0");
        assertTrue(message.getMessageType() == MessageType.BUILDTIGER);
    }

    @Test
    public void testCanSendExpandVillageMessageAndGetType() {
        Message message = new Message("EXPANDED SETTLEMENT AT 0 0 0 ROCKY");
        assertTrue(message.getMessageType() == MessageType.EXPANDSETTLEMENT);
    }

    @Test
    public void testCanGetNegativeIndicesFromMessage() {
        Message message = new Message("PLACE ROCKY+LAKE AT 0 0 -1 1");
        assertTrue(message.getTileLocation().x == 1);
    }

}


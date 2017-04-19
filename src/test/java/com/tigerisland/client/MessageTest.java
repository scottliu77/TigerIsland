package com.tigerisland.client;

import com.tigerisland.game.board.Terrain;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
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
        assertTrue(message.getMoveID().equals("1"));
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
        Message message = new Message("PLACED ROCK+LAKE AT 0 0 1 6");
        assertTrue(message.getTile().getLeftHex().getHexTerrain() == Terrain.ROCK);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetX() {
        Message message = new Message("PLACE ROCK+LAKE AT 0 0 0 6");
        assertTrue(message.getTileLocation().x == 0);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetY() {
        Message message = new Message("PLACE ROCK+LAKE AT 0 0 0 6");
        assertTrue(message.getTileLocation().y == 0);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetOrientation() {
        Message message = new Message("PLACE ROCK+LAKE AT 0 0 0 1");
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
        Message message = new Message("EXPANDED SETTLEMENT AT 0 0 0 ROCK");
        assertTrue(message.getBuildLocation().x == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetY() {
        Message message = new Message("EXPAND SETTLEMENT AT 0 0 0 ROCK");
        assertTrue(message.getBuildLocation().y == 0);
    }

    @Test
    public void testCanSendExpandActionAndGetTerrainType() {
        Message message = new Message("EXPAND SETTLEMENT AT 0 0 0 ROCK");
        assertTrue(message.getExpandTerrain() == Terrain.ROCK);
    }

    @Test
    public void testCanSendTilePlacementMessageAndGetType() {
        Message message = new Message("PLACE ROCK+LAKE AT 0 0 0 3");
        assertTrue(message.getTile() != null);
    }

    @Test
    public void testCanSendCreateVillageMessageAndGetType() {
        Message message = new Message("FOUND SETTLEMENT AT 0 0 0");
        assertTrue(message.getMessageType() == MessageType.FOUNDSETTLEMENT);
    }

    @Test
    public void testCanSendCreateVillageWithShamanMessageAndGetType() {
        Message message = new Message(("FOUND SHANGRILA AT 0 0 0 "));
        assertTrue(message.getMessageType() == MessageType.FOUNDSHANGRILA);
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
        Message message = new Message("EXPANDED SETTLEMENT AT 0 0 0 ROCK");
        assertTrue(message.getMessageType() == MessageType.EXPANDSETTLEMENT);
    }

    @Test
    public void testCanGetNegativeIndicesFromMessage() {
        Message message = new Message("PLACE ROCK+LAKE AT 0 0 -1 1");
        assertTrue(message.getTileLocation().x == 0);
    }

    @Test
    public void testCanGetTournamentPassword() {
        Message message = new Message("ENTER THUNDERDOME tournamentPassword");
        assertTrue(message.getTournamentPassword().equals("tournamentPassword"));
    }

    @Test
    public void testCanGetTeamUsernameAndPassword() {
        Message message = new Message("I AM username password");
        assertTrue(message.getTeamUsername().equals("username") && message.getTeamPassword().equals("password"));
    }

    @Test
    public void testCanGetChallengeIDandRoundCountvariantOne() {
        Message message = new Message("NEW CHALLENGE 5 YOU WILL PLAY 2 MATCHES");
        assertTrue(message.getChallengeID().equals("5") && message.getRoundCount() == 2);
    }

    @Test
    public void testCanGetChallengeIDandRoundCountvariantTwo() {
        Message message = new Message("NEW CHALLENGE A YOU WILL PLAY 1 MATCH");
        assertTrue(message.getChallengeID().equals("A") && message.getRoundCount() == 1);
    }

    @Test
    public void testCanGetSendScore() {
        Message message = new Message("GAME 1 OVER SEND OUTCOME");
        assertTrue(message.getGameID().equals("1"));
    }

    @Test
    public void testCanTellPlayerDoesNotKnowScore() {
        Message message = new Message("GAME 1 PLAYER 7 FORFEITED: DOES NOT KNOW OUTCOME");
        assertTrue(message.getGameID().equals("1") && message.getOurPlayerID().equals("7"));
    }

    @Test
    public void testCanGetOpponentIDandScore() {
        // TODO Fix bad default first player is always 'our player' second is always 'opponent'
        Message message = new Message("GAME A OVER PLAYER 7 25 PLAYER 13 100");
        assertTrue(message.getOurPlayerID().equals("7") && message.getOurPlayerScore().equals("25"));
    }

    @Test
    public void testCanGetPlayerIDandScore() {
        Message message = new Message("GAME A OVER PLAYER 7 25 PLAYER 13 100");
        assertTrue(message.getOpponentID().equals("13") && message.getOpponentScore().equals("100"));
    }

    @Test
    public void testMakeMoveVariantOneGetGameID() {
        Message message = new Message("MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE ROCK+LAKE");
        assertTrue(message.getGameID().equals("A"));
    }

    @Test
    public void testMakeMoveVariantOneGetTurnTime() {
        Message message = new Message("MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE ROCK+LAKE");
        assertTrue(message.getTurnTime() == 1);
    }

    @Test
    public void testMakeMoveVariantTwoGetTurnTime() {
        Message message = new Message("MAKE YOUR MOVE IN GAME A WITHIN 1.75 SECONDS: MOVE 1 PLACE ROCK+LAKE");
        assertTrue(message.getTurnTime() == 1.75);
    }

    @Test
    public void testMakeMoveVariantOneGetMoveID() {
        Message message = new Message("MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE ROCK+LAKE");
        assertTrue(message.getMoveID().equals("1"));
    }

    @Test
    public void testMakeMoveVariantOneGetTile() {
        Message message = new Message("MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE ROCK+LAKE");
        Boolean correctLeftHex = message.getTile().getLeftHex().getHexTerrain() == Terrain.ROCK;
        Boolean correctRightHex = message.getTile().getRightHex().getHexTerrain() == Terrain.LAKE;
        assertTrue(correctLeftHex && correctRightHex);
    }


}


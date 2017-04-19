package com.tigerisland.client;

import com.tigerisland.game.moves.BuildActionType;
import com.tigerisland.game.board.Location;
import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.board.Tile;

import java.util.regex.Matcher;

import static com.tigerisland.client.ClientMessages.*;
import static com.tigerisland.client.ServerMessages.*;

public class Message {

    public final String message;

    private String tournamentPassword;
    private String teamUsername;
    private String teamPassword;

    private String challengeID;
    private Integer roundCount;

    private String gameID;
    private String moveID;
    private Double turnTime;

    private String currentPlayerID;
    private String ourPlayerID;
    private String opponentID;

    private String ourPlayerScore;
    private String opponentScore;

    private MessageType messageType;
    private BuildActionType buildActionType;

    private Tile tile;
    private Location tileLocation;
    private Location buildLocation;
    private Integer orientation;
    private Terrain expandTerrain;

    public Message(String message) {
        this.message = message;

        checkForServerWelcomeOne();
        checkForEnterTournament();
        checkForServerWelcomeTwo();
        checkForAuthenticateTeam();

        checkForGameID();
        checkForMoveID();

        checkForGeneralPlayerID();
        checkForCurrentPlayerID();

        checkForNewChallenge();
        checkForRoundStart();
        checkForMatchStart();

        checkForMakeMove();

        checkForSendScore();

        checkForMatchOver();

        checkForSendScore();

        checkForClientSendScore();

        checkForPlayerDoesNotKnowScore();

        checkForEndOfRound();
        checkForEndOfChallenge();

        checkStringForDetails();

        checkForGameOver();

        checkForTournamentEnd();
    }

    private void checkForServerWelcomeOne() {
        Matcher serverWelcomeMatcher = authWelcomePattern.matcher(message);
        while(serverWelcomeMatcher.find()) {
            messageType = MessageType.SERVERWELCOME;
        }
    }

    private void checkForEnterTournament() {
        Matcher enterTournamentMatcher = enterTournamentPattern.matcher(message);
        while(enterTournamentMatcher.find()) {
            String match = enterTournamentMatcher.group();
            tournamentPassword = match.split("\\s+")[2];

            messageType = MessageType.CLIENTENTERTOURNAMENT;
        }
    }

    private void checkForServerWelcomeTwo() {
        Matcher serverWelcomeMatcher = authAcceptPattern.matcher(message);
        while(serverWelcomeMatcher.find()) {
            messageType = MessageType.SERVERWELCOME2;
        }
    }

    private void checkForAuthenticateTeam() {
        Matcher authenticateMatcher = authenticationPattern.matcher(message);
        while(authenticateMatcher.find()) {
            String match = authenticateMatcher.group();

            teamUsername = match.split("\\s+")[2];
            teamPassword = match.split("\\s+")[3];

            messageType = MessageType.CLIENTAUTHENTICATETEAM;
        }
    }

    private void checkForGameID() {
        Matcher gidMatcher = gameIDPattern.matcher(message);
        while(gidMatcher.find()) {
            String match = gidMatcher.group();

            gameID = match.split("\\s+")[1];
        }
    }

    private void checkForMoveID() {
        Matcher moveMatcher = moveIDPattern.matcher(message);
        while(moveMatcher.find()) {
            moveID = moveMatcher.group().replaceAll("\\D+", "");
        }
    }

    private void checkForCurrentPlayerID() {
        Matcher playerMatcher = ServerMessages.authWaitPlayerIDPattern.matcher(message);
        while(playerMatcher.find()) {
            ourPlayerID = playerMatcher.group().replaceAll("\\D+", "");

            messageType = MessageType.PLAYERID;
        }
    }

    private void checkForNewChallenge() {
        Matcher challengeMatcher = ServerMessages.challengeNewPattern.matcher(message);
        while(challengeMatcher.find()) {
            String match = challengeMatcher.group();

            challengeID = match.split("\\s+")[2];

            roundCount = Integer.valueOf(match.split("\\s+")[6]);

            messageType = MessageType.CHALLENGESTARTED;
        }
    }

    private void checkForRoundStart() {
        Matcher roundMatcher = ServerMessages.roundBeginPattern.matcher(message);
        while(roundMatcher.find()) {

            messageType = MessageType.ROUNDSTARTED;
        }
    }

    private void checkForMatchStart() {
        Matcher matchMatcher = ServerMessages.matchStartPattern.matcher(message);
        while(matchMatcher.find()) {
            String match = matchMatcher.group();

            opponentID = match.split("\\s+")[8];

            messageType = MessageType.MATCHSTARTED;
        }
    }

    private void checkForGeneralPlayerID() {
        Matcher playerMatcher = currentPlayerIDPattern.matcher(message);
        while(playerMatcher.find()) {
            currentPlayerID = playerMatcher.group().replaceAll("\\D+", "");
        }
    }

    private void checkStringForDetails() {
        checkForTilePlacement();
        checkForFoundSettlement();
        checkForFoundSettlementWithShaman();
        checkForExpandSettlement();
        checkForBuildTotoro();
        checkForBuildTiger();
    }

    private void checkForTilePlacement() {
        int x, y, z, orientationCode;
        Terrain leftTerrain, rightTerrain;
        Location tileCubeLocation;

        String cleanMessage = message.replaceAll("\\+", " ");

        Matcher tilePlacementMatcher = placeTilePattern.matcher(cleanMessage);
        while(tilePlacementMatcher.find()) {
            String match = tilePlacementMatcher.group();

            leftTerrain = Terrain.valueOf(match.split("\\s+")[1]);
            rightTerrain = Terrain.valueOf(match.split("\\s+")[2]);

            x = Integer.valueOf( match.split("\\s+")[4]);
            y = Integer.valueOf( match.split("\\s+")[5]);
            z = Integer.valueOf( match.split("\\s+")[6]);

            orientationCode = Integer.valueOf( match.split("\\s+")[7]);
            tileCubeLocation = new Location(x, y, z);

            tile = new Tile(leftTerrain, rightTerrain);
            tileLocation = Adapter.convertLocationCubeToAxial(tileCubeLocation);
            orientation = Adapter.convertOrientationToDegrees(orientationCode);
        }
    }

    private void checkForFoundSettlement() {
        int x, y, z;
        Location buildCubeLocation;

        Matcher foundSettlementMatcher = foundSettlementPattern.matcher(message);
        while(foundSettlementMatcher.find()) {
            String match = foundSettlementMatcher.group();

            x = Integer.valueOf( match.split("\\s+")[3]);
            y = Integer.valueOf( match.split("\\s+")[4]);
            z = Integer.valueOf( match.split("\\s+")[5]);

            buildCubeLocation = new Location(x, y, z);

            buildLocation = Adapter.convertLocationCubeToAxial(buildCubeLocation);
            messageType = MessageType.FOUNDSETTLEMENT;

            buildActionType = BuildActionType.VILLAGECREATION;
        }
    }

    private void checkForFoundSettlementWithShaman() {
        int x, y, z;
        Location buildCubeLocation;

        Matcher foundShangrilaMatcher = foundShangrilaPattern.matcher(message);
        while(foundShangrilaMatcher.find()) {
            String match = foundShangrilaMatcher.group();

            x = Integer.valueOf( match.split("\\s+")[3]);
            y = Integer.valueOf( match.split("\\s+")[4]);
            z = Integer.valueOf( match.split("\\s+")[5]);

            buildCubeLocation = new Location(x, y, z);

            buildLocation = Adapter.convertLocationCubeToAxial(buildCubeLocation);
            messageType = MessageType.FOUNDSHANGRILA;

            buildActionType = BuildActionType.SHAMANCREATION;
        }
    }

    private void checkForBuildTotoro() {
        int x, y, z;
        Location buildCubeLocation;

        Matcher buildTotoroMatcher = buildTotoroPattern.matcher(message);
        while(buildTotoroMatcher.find()) {
            String match = buildTotoroMatcher.group();

            x = Integer.valueOf( match.split("\\s+")[4]);
            y = Integer.valueOf( match.split("\\s+")[5]);
            z = Integer.valueOf( match.split("\\s+")[6]);

            buildCubeLocation = new Location(x, y, z);

            buildLocation = Adapter.convertLocationCubeToAxial(buildCubeLocation);
            messageType = MessageType.BUILDTOTORO;

            buildActionType = BuildActionType.TOTOROPLACEMENT;
        }
    }

    private void checkForBuildTiger() {
        int x, y, z;
        Location buildCubeLocation;

        Matcher buildTigerMatcher = buildTigerPattern.matcher(message);
        while(buildTigerMatcher.find()) {
            String match = buildTigerMatcher.group();

            x = Integer.valueOf( match.split("\\s+")[4]);
            y = Integer.valueOf( match.split("\\s+")[5]);
            z = Integer.valueOf( match.split("\\s+")[6]);

            buildCubeLocation = new Location(x, y, z);

            buildLocation = Adapter.convertLocationCubeToAxial(buildCubeLocation);
            messageType = MessageType.BUILDTIGER;

            buildActionType = BuildActionType.TIGERPLACEMENT;
        }
    }

    private void checkForExpandSettlement() {
        int x, y, z;
        Location buildCubeLocation;

        Matcher expandSettlementMatcher = expandSettlementPattern.matcher(message);
        while(expandSettlementMatcher.find()) {
            String match = expandSettlementMatcher.group();

            x = Integer.valueOf( match.split("\\s+")[3]);
            y = Integer.valueOf( match.split("\\s+")[4]);
            z = Integer.valueOf( match.split( "\\s+")[5]);

            buildCubeLocation = new Location(x, y, z);

            expandTerrain = Terrain.valueOf(match.split("\\s+")[6]);
            buildLocation = Adapter.convertLocationCubeToAxial(buildCubeLocation);
            messageType = MessageType.EXPANDSETTLEMENT;

            buildActionType = BuildActionType.VILLAGEEXPANSION;
        }
    }

    private void checkForClientSendScore() {
        Matcher sendScoreMatcher = sendScorePattern.matcher(message);
        while(sendScoreMatcher.find()) {
            String match = sendScoreMatcher.group();

            gameID = match.split("\\w+")[1];

            ourPlayerID = match.split("\\w+")[4];
            ourPlayerScore = match.split("\\d+")[5];

            opponentID = match.split("\\w+")[7];
            opponentScore = match.split("\\d+")[8];
        }
    }

    private void checkForEndOfChallenge() {
        Matcher endChallengeMatcher = ServerMessages.challengeWaitPattern.matcher(message);
        Matcher lastChallengeMatcher = ServerMessages.challengeEndPattern.matcher(message);

        while(endChallengeMatcher.find()) {
            messageType = MessageType.ENDOFCHALLENGE;
            return;
        }

        while(lastChallengeMatcher.find()) {
            messageType = MessageType.LASTCHALLENGEOVER;
            return;
        }
    }

    private void checkForEndOfRound() {
        Matcher roundMatcher = ServerMessages.roundEndWaitPattern.matcher(message);
        Matcher lastRoundMatcher = ServerMessages.roundEndPattern.matcher(message);

        while(roundMatcher.find()) {
            messageType = MessageType.ROUNDENDED;
            return;
        }

        while(lastRoundMatcher.find()) {

            messageType = MessageType.LASTROUNDOVER;
            return;
        }
    }

    private void checkForMakeMove() {
        Terrain leftTerrain, rightTerrain;
        String cleanMessage = message.replaceAll("\\+", " ");

        Matcher moveMatcher = ServerMessages.makeMovePattern.matcher(cleanMessage);

        while(moveMatcher.find()) {
            String match = moveMatcher.group();

            gameID = match.split("\\s+")[5];

            turnTime = Double.valueOf(match.split("\\s+")[7]);

            moveID = match.split("\\s+")[10];

            leftTerrain = Terrain.valueOf(match.split("\\s+")[12]);
            rightTerrain = Terrain.valueOf(match.split("\\s+")[13]);

            tile = new Tile(leftTerrain, rightTerrain);

            messageType = MessageType.MAKEMOVE;
        }

    }

    private void checkForSendScore() {
        Matcher matchMater = ServerMessages.serverRequestsGameOutcomePattern.matcher(message);
        while(matchMater.find()) {
            String match = matchMater.group();
            gameID = match.split("\\s+")[1];


        }
    }

    private void checkForMatchOver() {
        Matcher matchMatcher = ServerMessages.gameOverPattern.matcher(message);

        while(matchMatcher.find()) {
            String match = matchMatcher.group();

            gameID = match.split("\\s+")[1];

            ourPlayerID = match.split("\\s+")[4];
            ourPlayerScore = match.split("\\s+")[5];

            opponentID = match.split("\\s+")[7];
            opponentScore = match.split("\\s+")[8];

            messageType = MessageType.GAMEOVER;
        }
    }

    private void checkForPlayerDoesNotKnowScore() {
        Matcher matchMatcher = ServerMessages.playerDoesNotKnowOutcomePattern.matcher(message);

        while(matchMatcher.find()) {
            String match = matchMatcher.group();

            gameID = match.split("\\s+")[1];

            ourPlayerID = match.split("\\s+")[3];
        }
    }

    private void checkForGameOver() {
        checkForIllegalTilePlacement();
        checkForIllegalBuild();
        checkForTimeout();
        checkForMalformedMove();
        checkForUnableToBuild();
    }

    private void checkForIllegalTilePlacement() {
        Matcher forfeitMatcher = ServerMessages.forfeitIllegalTilePlacementPattern.matcher(message);

        while(forfeitMatcher.find()) {
            messageType = MessageType.FORFEITTILE;
        }
    }

    private void checkForIllegalBuild() {
        Matcher forfeitMatcher = ServerMessages.forfeitIllegalBuildPattern.matcher(message);

        while(forfeitMatcher.find()) {
            messageType = MessageType.FORFEITBUILD;
        }
    }

    private void checkForTimeout() {
        Matcher forfeitMatcher = ServerMessages.forfeitTimeoutPattern.matcher(message);

        while(forfeitMatcher.find()) {
            messageType = MessageType.FORFEITTIMEOUT;
        }
    }

    private void checkForMalformedMove() {
        Matcher foreitMatcher = ServerMessages.forfeitMalformedMovePattern.matcher(message);

        while(foreitMatcher.find()) {
            messageType = MessageType.FORFEITMALFORMED;
        }
    }

    private void checkForUnableToBuild() {
        Matcher forfeitMatcher = ServerMessages.serverLostUnableToBuildPattern.matcher(message);
        Matcher clientForfeitMatcher = clientLostUnableToBuildPattern.matcher(message);

        while(forfeitMatcher.find()) {
            messageType = MessageType.LOSTNOBUILD;
        }

        while(clientForfeitMatcher.find()) {
            messageType = MessageType.FORFEITBUILD;
        }
    }

    private void checkForTournamentEnd() {
        Matcher endMatcher  = ServerMessages.tournamentEndPattern.matcher(message);

        while(endMatcher.find()) {
            messageType = MessageType.TOURNAMENTEND;
        }
    }

    public String getGameID() {
        return gameID;
    }

    public String getMoveID() {
        return moveID;
    }

    public Double getTurnTime() {
        return turnTime;
    }

    public String getPlayerID() {
        return currentPlayerID;
    }

    public String getOurPlayerID() {
        return ourPlayerID;
    }

    public Tile getTile() {
        return tile;
    }

    public Location getTileLocation() {
        return tileLocation;
    }

    public Location getBuildLocation() {
        return buildLocation;
    }

    public Terrain getExpandTerrain() {
        return expandTerrain;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setProcessed() {
        messageType = MessageType.PROCESSED;
    }

    public String getTournamentPassword() {
        return tournamentPassword;
    }

    public String getTeamUsername() {
        return teamUsername;
    }

    public String getTeamPassword() {
        return teamPassword;
    }

    public String getChallengeID() {
        return challengeID;
    }

    public Integer getRoundCount() {
        return roundCount;
    }

    public String getOpponentID() {
        return opponentID;
    }

    public String getOurPlayerScore() {
        return ourPlayerScore;
    }

    public String getOpponentScore() {
        return opponentScore;
    }

    public String getCurrentPlayerID() {
        return currentPlayerID;
    }

    public BuildActionType getBuildActionType() {
        return buildActionType;
    }
}

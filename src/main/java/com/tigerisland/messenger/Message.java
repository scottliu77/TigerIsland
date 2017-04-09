package com.tigerisland.messenger;

import com.tigerisland.game.BuildActionType;
import com.tigerisland.game.Location;
import com.tigerisland.game.Terrain;
import com.tigerisland.game.Tile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public static final Pattern enterTournamentPattern = Pattern.compile("ENTER THUNDERDOME \\w+");
    public static final Pattern authenticationPattern = Pattern.compile("I AM \\w+ \\w+");

    public static final Pattern gameIDPattern = Pattern.compile("GAME \\w+");
    public static final Pattern moveIDPattern = Pattern.compile("MOVE \\w+");
    public static final Pattern currentPlayerIDPattern = Pattern.compile("PLAYER \\w+");

    public static final Pattern placeTilePattern = Pattern.compile("PLACE(D)? \\w+[\\+ ]?\\w+ AT -?\\d+ -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern foundSettlementPattern = Pattern.compile("FOUND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern expandSettlementPattern = Pattern.compile("EXPAND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+ \\w+");
    public static final Pattern buildTotoroPattern = Pattern.compile("BUIL[DT] TOTORO SANCTUARY AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern buildTigerPattern = Pattern.compile("BUIL[DT] TIGER PLAYGROUND AT -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern clientLostUnableToBuildPattern = Pattern.compile("GAME \\w+ MOVE \\w+ UNABLE TO BUILD");

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

        checkForGameID();
        checkForMoveID();
        // TODO: general player ID check conflicts with GAME over player check
        checkForGeneralPlayerID();

        checkForEnterTournament();
        checkForAuthenticateTeam();
        checkForCurrentPlayerID();

        checkForNewChallenge();
        checkForRoundStart();
        checkForMatchStart();

        checkForMakeMove();

        checkForMatchOver();

        checkForEndOfRound();
        checkForEndOfChallenge();

        checkStringForDetails();

        checkForGameOver();

        checkForTournamentEnd();
    }

    private void checkForEnterTournament() {
        Matcher enterTournamentMatcher = enterTournamentPattern.matcher(message);
        while(enterTournamentMatcher.find()) {
            String match = enterTournamentMatcher.group();
            tournamentPassword = match.split("\\s+")[2];

            messageType = MessageType.ENTERTOURNAMENT;
        }
    }

    private void checkForAuthenticateTeam() {
        Matcher authenticateMatcher = authenticationPattern.matcher(message);
        while(authenticateMatcher.find()) {
            String match = authenticateMatcher.group();

            teamUsername = match.split("\\s+")[2];
            teamPassword = match.split("\\s+")[3];

            messageType = MessageType.AUTHENTICATETEAM;
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

    private void checkForGameOver() {
        checkForIllegalTilePlacement();
        checkForIllegalBuild();
        checkForTimeout();
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

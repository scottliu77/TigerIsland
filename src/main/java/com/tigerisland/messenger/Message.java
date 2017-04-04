package com.tigerisland.messenger;

import com.tigerisland.game.Location;
import com.tigerisland.game.Terrain;
import com.tigerisland.game.Tile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public static final Pattern enterTournamentPattern = Pattern.compile("ENTER THUNDERDOME \\w+");
    public static final Pattern authenticationPattern = Pattern.compile("I AM \\w+ \\w+");

    public static final Pattern gameIDPattern = Pattern.compile("GAME \\w+");
    public static final Pattern moveIDPattern = Pattern.compile("MOVE \\d+");
    public static final Pattern playerIDPattern = Pattern.compile("PLAYER \\d+");

    public static final Pattern placeTilePattern = Pattern.compile("PLACE(D)? \\w+ \\w+ AT -?\\d+ -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern foundSettlementPattern = Pattern.compile("FOUND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern expandSettlementPattern = Pattern.compile("EXPAND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+ \\w+");
    public static final Pattern buildTotoroPattern = Pattern.compile("BUIL[DT] TOTORO SANCTUARY AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern buildTigerPattern = Pattern.compile("BUIL[DT] TIGER PLAYGROUND AT -?\\d+ -?\\d+ -?\\d+");

    public final String message;

    private String tournamentPassword;
    private String teamUsername;
    private String teamPassword;

    private Integer ourPlayerID;

    private String challengeID;
    private Integer roundCount;

    private String gameID;
    private Integer moveID;
    private Integer playerID;
    private Integer opponentID;

    private Integer playerScore;
    private Integer opponentScore;

    private MessageType messageType;

    private Tile tile;
    private Location tileLocation;
    private Location buildLocation;
    private Integer orientation;
    private Terrain expandTerrain;

    public Message(String message) {
        this.message = message;

        checkForEnterTournament();
        checkForAuthenticateTeam();
        checkForOurPlayerID();

        checkForNewChallenge();
        checkForRoundStart();
        checkForMatchStart();

        checkForMatchOver();
        checkForLastRound();
        checkForLastChallenge();

        checkForGameID();
        checkForMoveID();
        checkForGeneralPlayerID();
        checkStringForDetails();
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
            moveID = Integer.valueOf(moveMatcher.group().replaceAll("\\D+", ""));
        }
    }

    private void checkForOurPlayerID() {
        Matcher playerMatcher = ServerMessages.authWaitPlayerIDPattern.matcher(message);
        while(playerMatcher.find()) {
            ourPlayerID = Integer.valueOf(playerMatcher.group().replaceAll("\\D+", ""));

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

            opponentID = Integer.valueOf(match.split("\\s+")[8]);

            messageType = MessageType.MATCHSTARTED;
        }
    }

    private void checkForGeneralPlayerID() {
        Matcher playerMatcher = playerIDPattern.matcher(message);
        while(playerMatcher.find()) {
            playerID = Integer.valueOf(playerMatcher.group().replaceAll("\\D+", ""));
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
        }
    }

    private void checkForLastChallenge() {
        Matcher challengeMatcher = ServerMessages.challengeEndPattern.matcher(message);

        while(challengeMatcher.find()) {

            messageType = MessageType.LASTCHALLENGEOVER;
        }
    }

    private void checkForLastRound() {
        Matcher roundMatcher = ServerMessages.roundEndPattern.matcher(message);

        while(roundMatcher.find()) {

            messageType = MessageType.LASTROUNDOVER;
        }
    }

    private void checkForMatchOver() {
        Matcher matchMatcher = ServerMessages.matchOverPattern.matcher(message);

        while(matchMatcher.find()) {
            String match = matchMatcher.group();

            gameID = match.split("\\s+")[1];

            playerID = Integer.valueOf(match.split("\\s+")[4]);
            playerScore = Integer.valueOf(match.split("\\s+")[5]);

            opponentID = Integer.valueOf(match.split("\\s+")[7]);
            opponentScore = Integer.valueOf(match.split("\\s+")[8]);

            messageType = MessageType.MATCHOVER;
        }
    }

    public String getGameID() {
        return gameID;
    }

    public Integer getMoveID() {
        return moveID;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public Integer getOurPlayerID() {
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

    public Integer getOpponentID() {
        return opponentID;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }
}

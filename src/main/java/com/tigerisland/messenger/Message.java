package com.tigerisland.messenger;

import com.tigerisland.game.Location;
import com.tigerisland.game.Terrain;
import com.tigerisland.game.Tile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public static final Pattern gameIDPattern = Pattern.compile("GAME \\d+");
    public static final Pattern moveIDPattern = Pattern.compile("MOVE \\d+");
    public static final Pattern playerIDPattern = Pattern.compile("PLAYER \\d+");

    public static final Pattern placeTilePattern = Pattern.compile("PLACE(D)? \\w+ \\w+ AT -?\\d+ -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern foundSettlementPattern = Pattern.compile("FOUND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern expandSettlementPattern = Pattern.compile("EXPAND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+ \\w+");
    public static final Pattern buildTotoroPattern = Pattern.compile("BUIL[DT] TOTORO SANCTUARY AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern buildTigerPattern = Pattern.compile("BUIL[DT] TIGER PLAYGROUND AT -?\\d+ -?\\d+ -?\\d+");

    public final String message;

    private Integer gameID;
    private Integer moveID;
    private Integer playerID;

    private MessageType messageType;

    private Tile tile;
    private Location tileLocation;
    private Location buildLocation;
    private Integer orientation;
    private Terrain expandTerrain;

    public Message(String message) {
        this.message = message;
        checkForGameID();
        checkForMoveID();
        checkStringForDetails();
    }

    private void checkForGameID() {
        Matcher gidMatcher = gameIDPattern.matcher(message);
        while(gidMatcher.find()) {
            gameID = Integer.valueOf(gidMatcher.group().replaceAll("\\D+", ""));
        }
    }

    private void checkForMoveID() {
        Matcher moveMatcher = moveIDPattern.matcher(message);
        while(moveMatcher.find()) {
            moveID = Integer.valueOf(moveMatcher.group().replaceAll("\\D+", ""));
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

    public Integer getGameID() {
        return gameID;
    }

    public Integer getMoveID() {
        return moveID;
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
}

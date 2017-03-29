package com.tigerisland.messenger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    // WARNING! patterns based on temporary protocol, subject to change
    public static final Pattern gidPattern = Pattern.compile("GAME \\d+");
    public static final Pattern moveNumberPattern = Pattern.compile("MOVE \\d+");
    //GAME <gameID> MOVE <#> PLACE <tile> AT <x> <y> <orientation>
    public static final Pattern placeTilePattern = Pattern.compile("PLACE \\w+ AT -?\\d+ -?\\d+ -?\\d+");
    //GAME <gameID> MOVE <#> BUILD <piece> AT <x> <y>
    public static final Pattern buildPattern = Pattern.compile("BUILD \\w+ AT -?\\d+ -?\\d+");
    //GAME <gameID> MOVE <#> EXPAND <x> <y> AT <new_x> <new_y>
    public static final Pattern expandPattern = Pattern.compile("EXPAND -?\\d+ -?\\d+ AT -?\\d+ -?\\d+");

    private String message;
    private Integer gameID;
    private MessageType messageType;
    private Integer moveID;

    private String tile;
    private Integer x;
    private Integer y;
    private Integer newX;
    private Integer newY;
    private Integer orientation;
    private String piece;
    private String terrain;

    public Message(String message) {
        this.message = message;
        checkForGameID();
        checkForMoveNumber();
        checkForMoveDetails();
    }

    private void checkForGameID() {
        Matcher gidMatcher = gidPattern.matcher(message);
        while(gidMatcher.find()) {
            gameID = Integer.valueOf(gidMatcher.group().replaceAll("\\D+", ""));
        }
    }

    private void checkForMoveNumber() {
        Matcher moveMatcher = moveNumberPattern.matcher(message);
        while(moveMatcher.find()) {
            moveID = Integer.valueOf(moveMatcher.group().replaceAll("\\D+", ""));
        }
    }

    private void checkForMoveDetails() {
        checkForTilePlacement();
        checkForBuildAction();
        checkForExpandAction();
    }

    private void checkForTilePlacement() {
        Matcher tilePlacementMatcher = placeTilePattern.matcher(message);
        while(tilePlacementMatcher.find()) {
            String match = tilePlacementMatcher.group();
            tile = match.split("\\s+")[1];
            x = Integer.valueOf( match.split("\\s+")[3]);
            y = Integer.valueOf( match.split("\\s+")[4]);
            orientation = Integer.valueOf( match.split("\\s+")[5]);
            messageType = MessageType.TILEPLACEMENT;
        }
    }

    private void checkForBuildAction() {
        Matcher buildActionMatcher = buildPattern.matcher(message);
        while(buildActionMatcher.find()) {
            String match = buildActionMatcher.group();
            piece = match.split("\\s+")[1];
            x = Integer.valueOf( match.split("\\s+")[3]);
            y = Integer.valueOf( match.split("\\s+")[4]);
            identifyBuildActionType();
        }
    }

    private void identifyBuildActionType() {
        piece = piece.toLowerCase();
        if(piece.equals("villager")) {
            messageType = MessageType.VILLAGECREATION;
        } else if (piece.equals("totoro")) {
            messageType = MessageType.TOTOROPLACEMENT;
        } else if (piece.equals("tiger")) {
            messageType = MessageType.TIGERPLACEMENT;
        }
    }

    private void checkForExpandAction() {
        Matcher expandPatternMatcher = expandPattern.matcher(message);
        while(expandPatternMatcher.find()) {
            String match = expandPatternMatcher.group();
            x = Integer.valueOf( match.split("\\s+")[1]);
            y = Integer.valueOf( match.split("\\s+")[2]);
            newX = Integer.valueOf( match.split("\\s+")[4]);
            newY = Integer.valueOf( match.split("\\s+")[5]);
            messageType = MessageType.VILLAGEXPANSION;
        }
    }

    public String toString() {
        return message;
    }

    public Integer getGameID() {
        return gameID;
    }

    public Integer getMoveID() {
        return moveID;
    }

    public String getTileString() {
        return tile;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getNewX() {
        return newX;
    }

    public Integer getNewY() {
        return newY;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public String getPieceString() {
        return piece;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setProcessed() {
        messageType = MessageType.PROCESSED;
    }
}

package com.tigerisland.messenger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    // WARNING! patterns based on temporary protocol, subject to change
    private final Pattern gidPattern = Pattern.compile("GAME \\d+");
    private final Pattern moveNumberPattern = Pattern.compile("MOVE \\d+");
    //GAME <gid> MOVE <#> PLACE <tile> AT <x> <y> <orientation>
    private final Pattern placeTilePattern = Pattern.compile("PLACE \\w+ AT -?\\d+ -?\\d+ -?\\d+");
    //GAME <gid> MOVE <#> BUILD <piece> AT <x> <y>
    private final Pattern buildPattern = Pattern.compile("BUILD \\w+ AT -?\\d+ -?\\d+");
    //GAME <gid> MOVE <#> EXPAND <x> <y> AT <new_x> <new_y>
    private final Pattern expandPattern = Pattern.compile("EXPAND -?\\d+ -?\\d+ AT -?\\d+ -?\\d+");

    private String message;
    private Integer gid;
    private MessageType messageType;
    private Integer moveNumber;

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
            gid = Integer.valueOf(gidMatcher.group().replaceAll("\\D+", ""));
        }
    }

    private void checkForMoveNumber() {
        Matcher moveMatcher = moveNumberPattern.matcher(message);
        while(moveMatcher.find()) {
            moveNumber = Integer.valueOf(moveMatcher.group().replaceAll("\\D+", ""));
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
        return gid;
    }

    public Integer getMoveNumber() {
        return moveNumber;
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

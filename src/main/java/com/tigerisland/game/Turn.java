package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Turn {

    private Player player;
    private Board board;
    private TilePlacement tilePlacement;
    private BuildAction buildAction;

    public Turn(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    public void updateTilePlacement(int gameID, int moveID, BlockingQueue<Message> inboundMessages) throws InterruptedException, InvalidMoveException {
        while(true) {
            for(Message message : inboundMessages) {
                if(message.getMessageType() != MessageType.PROCESSED) {
                    if(message.getMoveNumber() == moveID) {
                        if(message.getGameID() == gameID) {
                            if(message.getMessageType() == MessageType.TILEPLACEMENT) {
                                parseTilePlacement(message);
                                message.setProcessed();
                                return;
                            }
                        }
                    }
                }
            }
            sleep(5);
        }
    }

    private void parseTilePlacement(Message message) throws InvalidMoveException {
        Terrain leftTerrain = getTerrainMatch(message.getTileString().substring(0,1));
        Terrain rightTerrain = getTerrainMatch(message.getTileString().substring(1, 2));
        Tile tile = new Tile(leftTerrain, rightTerrain);
        Location loc = new Location(message.getX(), message.getY());
        int rotation = message.getOrientation();
        tilePlacement = new TilePlacement(tile, loc, rotation);
    }

    private Terrain getTerrainMatch(String terrainChar) throws InvalidMoveException {
        terrainChar = terrainChar.toLowerCase();
        if(terrainChar.equals("g")) {
            return Terrain.GRASSLANDS;
        } else if (terrainChar.equals("l")) {
            return Terrain.LAKE;
        } else if (terrainChar.equals("r")) {
            return Terrain.ROCKY;
        } else if (terrainChar.equals("j"))
            return Terrain.JUNGLE;
        throw new InvalidMoveException("That terrain type does not exist");
    }

    public void updatedBuildAction(int gameID, int moveID, BlockingQueue<Message> inboundMessages) throws InterruptedException {
        while(true) {
            for(Message message : inboundMessages) {
                if(message.getMessageType() != MessageType.PROCESSED) {
                    if(message.getGameID() == gameID) {
                        if(message.getMoveNumber() == moveID) {
                            if(message.getMessageType() == MessageType.VILLAGECREATION) {
                                parseBuildAction(message, BuildActionType.VILLAGECREATION);
                                message.setProcessed();
                                return;
                            } else if (message.getMessageType() == MessageType.VILLAGEXPANSION) {
                                parseBuildAction(message, BuildActionType.VILLAGEEXPANSION);
                                message.setProcessed();
                                return;
                            } else if (message.getMessageType() == MessageType.TOTOROPLACEMENT) {
                                parseBuildAction(message, BuildActionType.TOTOROPLACEMENT);
                                message.setProcessed();
                                return;
                            } else if (message.getMessageType() == MessageType.TIGERPLACEMENT) {
                                parseBuildAction(message, BuildActionType.TIGERPLACEMENT);
                                message.setProcessed();
                                return;
                            }
                        }
                    }
                }
            }
            sleep(5);
        }
    }

    private void parseBuildAction(Message message, BuildActionType buildActionType) {
        Location location = new Location(message.getX(), message.getY());
        if (buildActionType == BuildActionType.VILLAGEEXPANSION) {
            Location expandLocation = new Location(message.getNewX(), message.getNewY());
            buildAction = new BuildAction(player, location, expandLocation);
        } else {
            buildAction = new BuildAction(player, location, buildActionType);
        }
    }

    public void updatePlayer(Player newPlayer) {
        player = newPlayer;
    }

    public void updateBoard(Board newBoard) {
        board = newBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public TilePlacement getTilePlacement() {
        return tilePlacement;
    }

    public BuildAction getBuildAction() {
        return buildAction;
    }

}

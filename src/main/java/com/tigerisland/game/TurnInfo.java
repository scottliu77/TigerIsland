package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;

import java.util.concurrent.BlockingQueue;

public class TurnInfo {

    public final int gameID;
    private int moveID;
    private Tile currentTile;
    private GameSettings gameSettings;
    public final BlockingQueue<Message> inboundMessages;

    public TurnInfo(int gameID, GameSettings gameSettings) {
        this.gameID = gameID;
        moveID = 1;
        this.gameSettings = gameSettings;
        this.inboundMessages = this.gameSettings.getGlobalSettings().inboundQueue;
    }

    public int getMoveID() {
        return moveID;
    }

    public void incrementMoveNumber() {
        moveID++;
    }

    public void drawANewTile() throws InvalidMoveException {
        try {
            currentTile = gameSettings.getDeck().drawTile();
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidMoveException("Cannot draw a tile from empty deck");
        }
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Tile getTile() {
        return currentTile;
    }
}

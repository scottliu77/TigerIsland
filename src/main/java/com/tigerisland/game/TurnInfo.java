package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.messenger.Message;

import java.util.concurrent.BlockingQueue;

public class TurnInfo {

    public final int gameID;
    private int moveID;
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

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}

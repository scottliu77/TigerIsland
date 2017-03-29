package com.tigerisland.AI;

import com.tigerisland.game.TextGUI;
import com.tigerisland.game.Turn;
import com.tigerisland.game.TurnInfo;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DummyAI {

    public static void pickTilePlacement(TurnInfo turnInfo, Turn turnState) {
        TextGUI.printMap(turnState.getBoard());
        while(true) {
            System.out.println("Enter a tile placement message for GAME " + turnInfo.gameID + " MOVE" + turnInfo.getMoveID());
            BufferedReader manualInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                String message = manualInput.readLine();
                if(checkValidTilePlacementMessage(message)) {
                    turnInfo.inboundMessages.add(new Message(message));
                    return;
                } else {
                    System.out.println("Please use the following tile placement message format.");
                    System.out.println("\tGAME <gid> MOVE <#> PLACE <tile> AT <x> <y> <orientation>");
                }
            } catch (IOException e) {
                System.out.println("WARNING: Please enter a valid message");
            }
        }
    }

    private static Boolean checkValidTilePlacementMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(checkMessage.getMessageType() == MessageType.TILEPLACEMENT) {
            return true;
        } else {
            return false;
        }
    }

    public static void pickBuildAction(TurnInfo turnInfo, Turn turnState) {
        TextGUI.printMap(turnState.getBoard());
        while(true) {
            System.out.println("Enter a build action message for GAME " + turnInfo.gameID + " MOVE" + turnInfo.getMoveID());
            BufferedReader manualInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                String message = manualInput.readLine();
                if(checkValidBuildActionMessage(message)) {
                    turnInfo.inboundMessages.add(new Message(message));
                    return;
                } else {
                    System.out.println("Please use one of the following build message formats");
                    System.out.println("\tGAME <gid> MOVE <#> BUILD <piece> AT <x> <y>");
                    System.out.println("\tGAME <gid> MOVE <#> EXPAND <x> <y> AT <new_x> <new_y>");
                }
            } catch (IOException e) {
                System.out.println("WARNING: Please enter a valid message");
            }

        }
    }

    private static Boolean checkValidBuildActionMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(MessageType.buildActions().contains(checkMessage.getMessageType())) {
            return true;
        } else {
            return false;
        }
    }

}

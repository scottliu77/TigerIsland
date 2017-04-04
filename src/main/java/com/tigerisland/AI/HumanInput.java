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

public class HumanInput {

    public static String pickTilePlacementAndBuildAction(TurnInfo turnInfo, Turn turnState) {
        TextGUI.printMap(turnState.getBoard());
        String message;
        String leftTerrain = turnInfo.getTile().getLeftHex().getHexTerrain().name();
        String rightTerrain = turnInfo.getTile().getRightHex().getHexTerrain().name();

        while(true) {
            System.out.println("Enter a tile placement and build action for GAME " + turnInfo.gameID + " MOVE " + turnInfo.getMoveID());
            System.out.println("\tYour tile is: " + leftTerrain + "+" + rightTerrain);
            BufferedReader manualInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                message = manualInput.readLine();
                if(checkValidTilePlacementMessage(message)) {
                    if(checkValidBuildActionMessage(message)) {
                        turnInfo.inboundMessages.add(new Message(message));
                        return message;
                    }
                } else {
                    System.out.println("Please use one of the following message formats.");
                    System.out.println("\tGAME <gid> MOVE <#> <place> <build>");
                    System.out.println("\twhere <place> := PLACE " + leftTerrain + "+" + rightTerrain + " AT <x> <y> <z> <orientation>");
                    System.out.println("\tFOUND SETTLEMENT AT <x> <y> <z>");
                    System.out.println("\tEXPAND SETTLEMENT AT <x> <y> <z> <terrain>");
                    System.out.println("\tBUILD TOTORO SANCTUARY AT <x> <y> <z>");
                    System.out.println("\tBUILD TIGER PLAYGROUND AT <x> <y> <z>");
                }
            } catch (IOException e) {
                System.out.println("WARNING: Please enter a valid message");
            }
        }
    }

    private static Boolean checkValidTilePlacementMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(checkMessage.getTile() != null) {
            return true;
        } else {
            return false;
        }
    }

    private static Boolean checkValidBuildActionMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(checkMessage.getMessageType().getSubtype().equals("BUILDACTION")) {
            return true;
        } else {
            return false;
        }
    }

}

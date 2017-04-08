package com.tigerisland.AI;

import com.tigerisland.game.TextGUI;
import com.tigerisland.game.TilePlacement;
import com.tigerisland.game.Turn;
import com.tigerisland.messenger.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanInput extends AI {

    public void decideOnMove() {

        String manualString = builtValidMoveString();
        deconstructValidMoveString(manualString);

    }

    public String builtValidMoveString() {

        TextGUI.printMap(turnState.getBoard());

        String tempMessage;
        String leftTerrain = turnState.getCurrentTile().getLeftHex().getHexTerrain().name();
        String rightTerrain = turnState.getCurrentTile().getRightHex().getHexTerrain().name();

        while(true) {
            System.out.println("Enter a tile placement and build action for GAME " + turnState.gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerColor());
            System.out.println("\tYour tile is: " + leftTerrain + "+" + rightTerrain);
            BufferedReader manualInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                tempMessage = manualInput.readLine();
                if(checkValidTilePlacementMessage(tempMessage)) {
                    if(checkValidBuildActionMessage(tempMessage)) {
                        return tempMessage;
                    }
                } else {
                    System.out.println("Please the following message formats.");
                    System.out.println("\t<place> <build>");
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

    private Boolean checkValidTilePlacementMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(checkMessage.getTile() != null) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean checkValidBuildActionMessage(String messageString) {
        Message checkMessage = new Message(messageString);
        if(checkMessage.getMessageType().getSubtype().equals("BUILDACTION")) {
            return true;
        } else {
            return false;
        }
    }

    private void deconstructValidMoveString(String tempMessage) {
        Message messageFields = new Message(tempMessage);
        tilePlacement = new TilePlacement(messageFields.getTile(), messageFields.getTileLocation(), messageFields.getOrientation());
        buildActionType = messageFields.getBuildActionType();
        buildLocation = messageFields.getBuildLocation();
    }
}

package com.tigerisland.game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by johnzoldos on 3/29/17.
 */
public class TotoroPlacer {

    static final int SIZE_REQUIRED_FOR_TOTORO = 5;

    public static void placeTotoro(Player player, PlacedHex targetHex, ArrayList<Settlement> adjacentSettlementsToTargetLocation) throws InvalidMoveException {

        if(adjacentSettlementsToTargetLocation.size() > 1){
            throw new InvalidMoveException("THIS IS ACTUALLY OKAY BUT THE SERVER IS A JERK");
        }



        if (targetHex == null) {
            throw new InvalidMoveException("Target hex does not exist");
        }
        if (targetHex.getHex().getPieceCount() > 0) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        }
        if (targetHex.getHex().getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        removeSettlementsThatCantAcceptTotoroFromList(adjacentSettlementsToTargetLocation);
        if(adjacentSettlementsToTargetLocation.size() == 0){
            throw new InvalidMoveException("Settlement already contains a totoro or is too small");
        }

        try {
            targetHex.getHex().addPiecesToHex(player.getPieceSet().placeTotoro(), 1);
        } catch(NullPointerException e){
            return;
        }
    }



    private static void removeSettlementsThatCantAcceptTotoroFromList(ArrayList<Settlement> adjacentSettlementsToTargetLocation) {
        Iterator<Settlement> iter = adjacentSettlementsToTargetLocation.iterator();
        while (iter.hasNext()) {
            Settlement adjacentSettlement = iter.next();
            if (adjacentSettlement.containsTotoro() || adjacentSettlement.size() < SIZE_REQUIRED_FOR_TOTORO) {
                iter.remove();
            }
        }
    }


}

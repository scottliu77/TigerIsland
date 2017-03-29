package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by johnzoldos on 3/29/17.
 */
public class TigerPlacer {

    static final int SIZE_REQUIRED_FOR_TIGER = 1;

    public static void placeTiger(Player player, PlacedHex targetHex,ArrayList<Settlement> adjacentSettlementsToTargetLocation) throws InvalidMoveException {
        if (targetHex == null) {
            throw new InvalidMoveException("Target hex does not exist");
        }
        if (targetHex.getHex().getPieceCount() > 0) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        }
        if (targetHex.getHex().getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        if (targetHex.getHex().getHeight() < 3){
            throw new InvalidMoveException("Cannot build Tiger playground on hex of level less than 3");
        }

        removeSettlementsThatCantAcceptTigerFromList(adjacentSettlementsToTargetLocation);
        if(adjacentSettlementsToTargetLocation.size() == 0){
            throw new InvalidMoveException("Settlement already contains a tiger or is too small");
        }
        targetHex.getHex().addPiecesToHex(player.getPieceSet().placeTiger(), 1);
    }

    private static void removeSettlementsThatCantAcceptTigerFromList(ArrayList<Settlement> adjacentSettlementsToTargetLocation) {
        Iterator<Settlement> iter = adjacentSettlementsToTargetLocation.iterator();
        while(iter.hasNext()){
            Settlement adjacentSettlement = iter.next();
            if(adjacentSettlement.containsTiger() || adjacentSettlement.size()<SIZE_REQUIRED_FOR_TIGER) {
                iter.remove();
            }
        }
    }
}

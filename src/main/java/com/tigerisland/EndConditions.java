package com.tigerisland;

import java.util.ArrayList;
import java.util.Set;

public final class EndConditions {

    public static boolean noEndConditionsAreMet(Player currentPlayer, Board board) {
        return !noValidMoves(currentPlayer, board) && !playerIsOutOfPieces(currentPlayer);
    }

    public static boolean noValidMoves(Player currentPlayer, Board board){
        if(noMoreVillagers(currentPlayer) && cantPlayTotoro(currentPlayer, board)){
            return true;
        }
        return false;
    }

    public static boolean playerIsOutOfPieces(Player currentPlayer){
        if(currentPlayer.getPieceSet().inventoryEmpty()){
            return true;
        }
        return false;
    }

    private static boolean noMoreVillagers(Player currentPlayer){
        return currentPlayer.getPieceSet().getNumberOfVillagersRemaining() == 0;
    }

    private static boolean cantPlayTotoro(Player currentPlayer, Board board){
        if(currentPlayer.getPieceSet().getNumberOfTotoroRemaining() == 0){
            return true;
        }

        ArrayList<Settlement> playerSettlements = getPlayerSettlementsSizeFiveOrGreater(currentPlayer, board);

        if (noSettlementsOfSizeFiveOrGreater(playerSettlements)) {
            return true;
        } else if (noSettlementsNotContainingTotoro(playerSettlements)) {
            return true;
        } else if (noAvaibleSpacesForTotoro(board, playerSettlements)) {
            return true;
        }

        return false;
    }

    private static ArrayList<Settlement> getPlayerSettlementsSizeFiveOrGreater(Player currentPlayer, Board board) {
        ArrayList<Settlement> playerSettlements = new ArrayList<Settlement>();
        for (Settlement settlement : board.settlements) {
            if (settlement.color == currentPlayer.getPlayerColor()) {
                if (settlement.getHexesInSettlement().size() >= 5) {
                    playerSettlements.add(settlement);
                }
            }
        }
        return playerSettlements;
    }

    private static boolean noSettlementsOfSizeFiveOrGreater(ArrayList<Settlement> playerSettlements) {
        for(Settlement settlement : playerSettlements) {
            if (settlement.size() >= 5) {
                return false;
            }
        }
        return true;
    }

    private static boolean noSettlementsNotContainingTotoro(ArrayList<Settlement> playerSettlements) {
        for(Settlement settlement : playerSettlements) {
            if (settlement.containsTotoro() == false) {
                return false;
            }
        }
        return true;
    }

    private static boolean noAvaibleSpacesForTotoro(Board board, ArrayList<Settlement> playerSettlements) {
        for(Settlement settlement : playerSettlements) {
            for(PlacedHex hex : settlement.getHexesInSettlement()) {
                for(Location location : hex.getLocation().getAdjacentLocations()) {
                    if(board.hexAt(location).getPieceCount() == 0) {
                        if(board.hexAt(location).getHexTerrain() != Terrain.VOLCANO) {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }
}

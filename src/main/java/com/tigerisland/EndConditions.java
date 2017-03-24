package com.tigerisland;

public final class EndConditions {

    public static boolean noEndConditionsAreMet(Player currentPlayer, Board board) {
        return !playerIsOutOfPieces(currentPlayer) && !noValidMoves(currentPlayer, board);
    }

    public static boolean playerIsOutOfPieces(Player currentPlayer){
        if(currentPlayer.getPieceSet().inventoryEmpty()){
            return true;
        }
        return false;
    }

    public static boolean noValidMoves(Player currentPlayer, Board board){
        if(noMoreVillagers(currentPlayer) && cantPlayTotoro(currentPlayer, board) && cantPlayTiger(currentPlayer, board)){
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
        } else {
            return !board.playerHasSettlementThatCouldAcceptTotoro(currentPlayer);
        }
    }

    private static boolean cantPlayTiger(Player currentPlayer, Board board) {

        if(currentPlayer.getPieceSet().getNumberOfTigersRemaining() == 0) {
            return true;
        } else {
            return !board.playerHasSettlementThatCouldAcceptTiger(currentPlayer);
        }
    }
}

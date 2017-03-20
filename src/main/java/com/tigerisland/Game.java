package com.tigerisland;

import java.util.ArrayList;

public class Game {

    protected GameSettings gameSettings;
    protected Deck deck;
    protected Board board;
    protected PlayOrder players;
    protected Move move;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        deck = this.gameSettings.getDeck();
        board = new Board();
        players = gameSettings.getPlayOrder();
    }

    public void start() {
        while(noEndConditionsAreMet()) {
            takeTurn();
           players.setNextPlayer();
        }

        // TODO fancy game-ending stuff
    }
    private boolean noEndConditionsAreMet() {
        return !noValidMoves() && !playerIsOutOfPieces();
    }

    private void takeTurn() {
        try {
            // Listen for tile placement
            move = listenForMove();
            placeTile(move);

            // Listen for build option
            move = listenForMove();

            switch (move.getMoveType()) {
                case VILLAGECREATION:
                    createVillage(move);
                case VILLAGEEXPANSION:
                    expandVillage(move);
                case TOTOROPLACEMENT:
                    placeTotoro(move);
            }
        } catch (InvalidMoveException exception) {
            // runInvalidMoveEndCondtion();
        }
    }

    private Move listenForMove() {
        // TODO replace with mock listener
        Tile newTile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        Location newLocation = new Location(0, 1);
        int newRotation = 60;
        Move newMove = new Move(newTile, newLocation, newRotation);
        return newMove;
    }

    private void placeTile(Move move) throws InvalidMoveException {
        // Save create temp copy of board
        Board tempBoard = board;

        tempBoard.placeTile(move.getTile(), move.getLocation(), move.getRotation());

        // Update board state
        board = tempBoard;
    }

    private void createVillage(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        //tempBoard.createVillage(tempPlayer);
        //tempPlayer.getPieceSet().placeVillager();

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    private void expandVillage(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        //int piecesNeeded = tempBoard.expandVillage(tempPlayer);
        //tempPlayer.getPieceSet().placeMultipleVillagers(piecesNeeded);

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    private void placeTotoro(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        //tempBoard.placeTotoro(tempPlayer);
        //tempPlayer.getPieceSet().placeTotoro();

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    private boolean noValidMoves(){
        Player currentPlayer = gameSettings.getPlayOrder().getCurrentPlayer();
        if(noMoreVillagers(currentPlayer) && cantPlayTotoro(currentPlayer)){
            return true;
        }
        return false;
    }

    private boolean playerIsOutOfPieces(){
        for(Player player : players.getPlayerList()){
            if(player.getPieceSet().inventoryEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean noMoreVillagers(Player player){
        return player.getPieceSet().getNumberOfVillagersRemaining() == 0;
    }

    private boolean cantPlayTotoro(Player player){
        if(player.getPieceSet().getNumberOfTotoroRemaining() == 0){
            return true;
        }
        //otherwise check board's list of settlements (to be created) and see if one exists in this player's color with
        //room for a totoro. For every large enough settlement, see if there's an adjacent hex capable of being settled

        return false;
    }
}

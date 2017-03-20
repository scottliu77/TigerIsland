package com.tigerisland;

import java.util.ArrayList;

public class Game {

    protected GameSettings gameSettings;
    protected Deck deck;
    protected Board board;
    protected ArrayList<Player> players;
    protected Rules rules;
    protected Move move;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        deck = this.gameSettings.getDeck();
        board = new Board();
        players = new ArrayList<Player>();
        rules = new Rules();
        for(int player = 0; player < this.gameSettings.globalSettings.playerCount; player++){
            players.add(player, new Player(Color.values()[player]));
        }
    }

    public void start() {
        while(noEndConditionsAreMet()) {
            takeTurn();
            gameSettings.getPlayOrder().setNextPlayer();
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
            processTilePlacementMove();

            // Listen for build option
            move = listenForMove();

            switch (move.getMoveType()) {
                case VILLAGECREATION:
                    processVillageCreationMove();
                case VILLAGEEXPANSION:
                    processVillageExpansionMove();
                case TOTOROPLACEMENT:
                    processTotoroPlacementMove();
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

    private void processTilePlacementMove() throws InvalidMoveException {

    }

    private void processVillageCreationMove() throws InvalidMoveException {

    }

    private void processVillageExpansionMove() throws InvalidMoveException {

    }

    private void processTotoroPlacementMove() throws InvalidMoveException {

    }

    private boolean noValidMoves(){
        Player currentPlayer = gameSettings.getPlayOrder().getCurrentPlayer();
        if(noMoreVillagers(currentPlayer) && cantPlayTotoro(currentPlayer)){
            return true;
        }
        return false;
    }

    private boolean playerIsOutOfPieces(){
        for(Player player : players){
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

package com.tigerisland;

public class Game {
    private static final int TOTORO_POINT_VALUE = 200;
    private static final int VILLAGER_POINT_VALUE = 1;

    protected GameSettings gameSettings;
    protected Deck deck;
    protected Board board;
    protected PlayerOrder players;
    protected Move currentMove;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        deck = gameSettings.getDeck();
        board = new Board();
        players = gameSettings.getPlayerOrder();
    }

    public void start() {
        while(EndConditions.noEndConditionsAreMet(players.getCurrentPlayer(), board)) {
            takeTurn();
            players.setNextPlayer();
        }

        // TODO fancy game-ending stuff
    }

    public boolean noValidMoves(Player player){
        if(noMoreVillagers(player) && cantPlayTotoro(player)){
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

        return !board.playerHasSettlementThatCouldAcceptTotoro(player);
    }



    protected void takeTurn() {
        try {
            // Listen for tile placement
            currentMove = listenForMove();
            placeTile(currentMove);

            // Listen for build option
            currentMove = listenForMove();

            switch (currentMove.getMoveType()) {
                case VILLAGECREATION:
                    createVillage(currentMove);
                case VILLAGEEXPANSION:
                    expandVillage(currentMove);
                case TOTOROPLACEMENT:
                    placeTotoro(currentMove);
            }
        } catch (InvalidMoveException exception) {
            // runInvalidMoveEndCondtion();
        }
    }

    protected Move listenForMove() {
        // TODO replace with mock listener
        Tile newTile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        Location newLocation = new Location(0, 1);
        int newRotation = 60;
        Move newMove = new Move(newTile, newLocation, newRotation);
        return newMove;
    }

    protected void placeTile(Move move) throws InvalidMoveException {
        // Save create temp copy of board
        Board tempBoard = board;

        tempBoard.placeTile(move.getTile(), move.getLocation(), move.getRotation());

        // Update board state
        board = tempBoard;
    }

    protected void createVillage(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        tempBoard.createVillage(tempPlayer, move.getLocation());
        tempPlayer.getPieceSet().placeVillager();
        tempPlayer.getScore().addPoints(VILLAGER_POINT_VALUE);
        tempBoard.updateSettlements();

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    protected void expandVillage(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        int piecesNeeded = tempBoard.expandVillage(tempPlayer, move.getLocation(), move.getSettlementLocation());
        tempPlayer.getPieceSet().placeMultipleVillagers(piecesNeeded);
        tempBoard.updateSettlements();

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    protected void placeTotoro(Move move) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = players.getCurrentPlayer();
        Board tempBoard = board;

        tempBoard.placeTotoro(tempPlayer, move.getLocation());
        tempBoard.updateSettlements();
        tempPlayer.getScore().addPoints(TOTORO_POINT_VALUE);

        // Update board and player state
        players.updatePlayerState(tempPlayer);
        board = tempBoard;
    }
}

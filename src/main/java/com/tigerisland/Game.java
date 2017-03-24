package com.tigerisland;

import java.util.concurrent.BlockingQueue;

public class Game implements Runnable {
    private static final int TOTORO_POINT_VALUE = 200;
    private static final int VILLAGER_POINT_VALUE = 1;
    private static final int TIGER_POINT_VALUE = 75;

    protected GameSettings gameSettings;
    protected Board board;
    protected TilePlacement currentTilePlacement;
    protected BuildAction currentBuildAction;
    protected BlockingQueue inboundQueue;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        board = new Board();
        this.inboundQueue = gameSettings.globalSettings.inboundQueue;
    }

    public void run() {
        // TODO add checking for interrupted exception
        while(gameIsNotOver()) {
            takeTurn();
            gameSettings.getPlayerOrder().setNextPlayer();
        }

        // TODO fancy game-ending stuff
    }

    protected boolean gameIsNotOver() {
        return EndConditions.noEndConditionsAreMet(gameSettings.getPlayerOrder().getCurrentPlayer(), board);
    }

    protected void takeTurn() {
        try {
            proccessMessages();
            placeTile(currentTilePlacement);

            switch (currentBuildAction.getBuildActionType()) {
                case VILLAGECREATION:
                    createVillage(currentBuildAction);
                case VILLAGEEXPANSION:
                    expandVillage(currentBuildAction);
                case TOTOROPLACEMENT:
                    placeTotoro(currentBuildAction);
                case TIGERPLACEMENT:
                    placeTiger(currentBuildAction);
            }
        } catch (InvalidMoveException exception) {
            // runInvalidMoveEndCondtion();
        }
    }

    protected void proccessMessages() {
        currentTilePlacement = null;
        currentBuildAction = null;

        // Read message from inbound queue
        getTilePlacementFromMessage();
        getBuildActionFromMessage();
    }

    protected void getTilePlacementFromMessage() {
        // TODO replace with assembler from string message
        Tile newTile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        Location newLocation = new Location(0, 1);
        int newRotation = 60;
        currentTilePlacement = new TilePlacement(newTile, newLocation, newRotation);
    }

    protected void getBuildActionFromMessage() {
        // TODO replace with assembler from string message
        Player currentPlayer = gameSettings.getPlayerOrder().getCurrentPlayer();
        Location placementLocation = new Location(0, 1);
        currentBuildAction = new BuildAction(currentPlayer, placementLocation, BuildActionType.VILLAGECREATION);
    }


    protected void placeTile(TilePlacement tilePlacement) throws InvalidMoveException {
        // Save create temp copy of board
        Board tempBoard = new Board(board);

        //Update Settlements before and after placing a tile to avoid problems when settlements change
        tempBoard.updateSettlements();
        tempBoard.placeTile(tilePlacement.getTile(), tilePlacement.getLocation(), tilePlacement.getRotation());
        tempBoard.updateSettlements();

        // Update board state
        board = tempBoard;
    }

    protected void createVillage(BuildAction buildAction) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = new Player(gameSettings.getPlayerOrder().getCurrentPlayer());
        Board tempBoard = new Board(board);

        tempBoard.createVillage(tempPlayer, buildAction.getLocation());
        tempPlayer.getPieceSet().placeVillager();
        tempPlayer.getScore().addPoints(VILLAGER_POINT_VALUE);
        tempBoard.updateSettlements();

        // Update board and player state
        gameSettings.getPlayerOrder().updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    protected void expandVillage(BuildAction buildAction) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = new Player(gameSettings.getPlayerOrder().getCurrentPlayer());
        Board tempBoard = new Board(board);

        int piecesNeeded = tempBoard.expandVillage(tempPlayer, buildAction.getLocation(), buildAction.getSettlementLocation());
        tempPlayer.getPieceSet().placeMultipleVillagers(piecesNeeded);
        tempBoard.updateSettlements();

        // Update board and player state
        gameSettings.getPlayerOrder().updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    protected void placeTotoro(BuildAction buildAction) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = new Player(gameSettings.getPlayerOrder().getCurrentPlayer());
        Board tempBoard = new Board(board);

        tempBoard.placeTotoro(tempPlayer, buildAction.getLocation());
        tempBoard.updateSettlements();
        tempPlayer.getScore().addPoints(TOTORO_POINT_VALUE);

        // Update board and player state
        gameSettings.getPlayerOrder().updatePlayerState(tempPlayer);
        board = tempBoard;
    }

    protected void placeTiger(BuildAction buildAction) throws InvalidMoveException {
        // Save temp copies of board and player
        Player tempPlayer = new Player(gameSettings.getPlayerOrder().getCurrentPlayer());
        Board tempBoard = new Board(board);

        tempBoard.placeTiger(tempPlayer, buildAction.getLocation());
        tempBoard.updateSettlements();
        tempPlayer.getScore().addPoints(TIGER_POINT_VALUE);

        // Update board and player state
        gameSettings.getPlayerOrder().updatePlayerState(tempPlayer);
        board = tempBoard;
    }
}

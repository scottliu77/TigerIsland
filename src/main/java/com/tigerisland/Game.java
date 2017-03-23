package com.tigerisland;

public class Game {
    private static final int TOTORO_POINT_VALUE = 200;
    private static final int VILLAGER_POINT_VALUE = 1;
    private static final int TIGER_POINT_VALUE = 75;

    protected GameSettings gameSettings;
    protected Board board;
    protected TilePlacement currentTilePlacement;
    protected BuildAction currentBuildAction;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        board = new Board();
    }

    public void start() {
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
            // Listen for tile placement
            currentTilePlacement = listenForTilePlacement();
            placeTile(currentTilePlacement);

            // Listen for build option
            currentBuildAction = listenForBuildAction();

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

    protected TilePlacement listenForTilePlacement() {
        // TODO replace with mock listener
        Tile newTile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        Location newLocation = new Location(0, 1);
        int newRotation = 60;
        TilePlacement tilePlacement = new TilePlacement(newTile, newLocation, newRotation);
        return tilePlacement;
    }

    protected BuildAction listenForBuildAction() {
        // TODO replace with mock listener
        Player currentPlayer = gameSettings.getPlayerOrder().getCurrentPlayer();
        Location placementLocation = new Location(0, 1);
        BuildAction newBuildAction = new BuildAction(currentPlayer, placementLocation, BuildActionType.VILLAGECREATION);
        return newBuildAction;
    }


    protected void placeTile(TilePlacement tilePlacement) throws InvalidMoveException {
        // Save create temp copy of board
        Board tempBoard = new Board(board);

        tempBoard.placeTile(tilePlacement.getTile(), tilePlacement.getLocation(), tilePlacement.getRotation());

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

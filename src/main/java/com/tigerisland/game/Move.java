package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;

public final class Move {

    private static final int TOTORO_POINT_VALUE = 200;
    private static final int VILLAGER_POINT_VALUE = 1;
    private static final int TIGER_POINT_VALUE = 75;

    public static Turn placeTile(Turn turnState) throws InvalidMoveException {

        Board tempBoard = new Board(turnState.getBoard());

        tempBoard.updateSettlements();
        tempBoard.placeTile(turnState.getTilePlacement().getTile(), turnState.getTilePlacement().getLocation(), turnState.getTilePlacement().getRotation());
        tempBoard.updateSettlements();

        turnState.updateBoard(tempBoard);

        return turnState;
    }

    public static Turn takeBuildAction(Turn turnState) throws InvalidMoveException {
        switch (turnState.getBuildAction().getBuildActionType()) {
            case VILLAGECREATION:
                return createVillage(turnState);
            case VILLAGEEXPANSION:
                return expandVillage(turnState);
            case TOTOROPLACEMENT:
                return placeTotoro(turnState);
            case TIGERPLACEMENT:
                return placeTiger(turnState);
        }
        throw new InvalidMoveException();
    }

    public static Turn createVillage(Turn turnState) throws InvalidMoveException {

        Player tempPlayer = new Player(turnState.getPlayer());
        Board tempBoard = new Board(turnState.getBoard());

        tempBoard.createVillage(tempPlayer, turnState.getBuildAction().getLocation());
        tempPlayer.getPieceSet().placeVillager();
        tempPlayer.getScore().addPoints(VILLAGER_POINT_VALUE);
        tempBoard.updateSettlements();

        turnState.updatePlayer(tempPlayer);
        turnState.updateBoard(tempBoard);

        return turnState;
    }

    public static Turn expandVillage(Turn turnState) throws InvalidMoveException {

        Player tempPlayer = new Player(turnState.getPlayer());
        Board tempBoard = new Board(turnState.getBoard());

        tempBoard.expandVillage(tempPlayer, turnState.getBuildAction().getLocation(), turnState.getBuildAction().getSettlementLocation());
        tempBoard.updateSettlements();

        turnState.updatePlayer(tempPlayer);
        turnState.updateBoard(tempBoard);

        return turnState;
    }

    public static Turn placeTotoro(Turn turnState) throws InvalidMoveException {

        Player tempPlayer = new Player(turnState.getPlayer());
        Board tempBoard = new Board(turnState.getBoard());

        tempBoard.placeTotoro(tempPlayer, turnState.getBuildAction().getLocation());
        tempBoard.updateSettlements();
        tempPlayer.getScore().addPoints(TOTORO_POINT_VALUE);

        turnState.updatePlayer(tempPlayer);
        turnState.updateBoard(tempBoard);

        return turnState;
    }

    public static Turn placeTiger(Turn turnState) throws InvalidMoveException {

        Player tempPlayer = new Player(turnState.getPlayer());
        Board tempBoard = new Board(turnState.getBoard());

        tempBoard.placeTiger(tempPlayer, turnState.getBuildAction().getLocation());
        tempBoard.updateSettlements();
        tempPlayer.getScore().addPoints(TIGER_POINT_VALUE);

        turnState.updatePlayer(tempPlayer);
        turnState.updateBoard(tempBoard);

        return turnState;
    }
}

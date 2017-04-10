package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;

public final class Move {

    public static Turn placeTile(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().updateSettlements();
        turnState.getBoard().placeTile(turnState.getTilePlacement().getTile(), turnState.getTilePlacement().getLocation(), turnState.getTilePlacement().getRotation());
        turnState.getBoard().updateSettlements();

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

    private static Turn createVillage(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().createVillage(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation());
        turnState.getBoard().updateSettlements();
        turnState.getCurrentPlayer().getScore().addPoints(Score.VILLAGER_POINT_VALUE);
        return turnState;
    }

    private static Turn expandVillage(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().expandVillage(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation(), turnState.getBuildAction().getExpandTerrain());
        turnState.getBoard().updateSettlements();

        return turnState;
    }

    private static Turn placeTotoro(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().placeTotoro(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation());
        turnState.getBoard().updateSettlements();
        turnState.getCurrentPlayer().getScore().addPoints(Score.TOTORO_POINT_VALUE);
        return turnState;
    }

    private static Turn placeTiger(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().placeTiger(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation());
        turnState.getBoard().updateSettlements();
        turnState.getCurrentPlayer().getScore().addPoints(Score.TIGER_POINT_VALUE);

        return turnState;
    }
}

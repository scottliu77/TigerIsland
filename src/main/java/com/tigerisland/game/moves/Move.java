package com.tigerisland.game.moves;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.Turn;
import com.tigerisland.game.pieces.PieceType;
import com.tigerisland.game.player.Score;

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
                return createVillageWithVillager(turnState);
            case SHAMANCREATION:
                return createVillageWithShaman(turnState);
            case VILLAGEEXPANSION:
                return expandVillage(turnState);
            case TOTOROPLACEMENT:
                return placeTotoro(turnState);
            case TIGERPLACEMENT:
                return placeTiger(turnState);
        }
        throw new InvalidMoveException();
    }

    private static Turn createVillageWithVillager(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().createVillageWithVillager(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation());
        turnState.getBoard().updateSettlements();
        turnState.getCurrentPlayer().getScore().addPoints(Score.VILLAGER_POINT_VALUE);
        return turnState;
    }
    private static Turn createVillageWithShaman(Turn turnState) throws InvalidMoveException {

        turnState.getBoard().createVillageWithShaman(turnState.getCurrentPlayer(), turnState.getBuildAction().getLocation());
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

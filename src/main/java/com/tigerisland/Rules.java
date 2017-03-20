package com.tigerisland;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class Rules {

    protected Class tilePlacementRules = TilePlacementRules.class;
    protected Class villageCreationRules = VillageCreationRules.class;
    protected Class villageExpansionRules = VillageExpansionRules.class;
    protected Class totoroPlacementRules = TotoroPlacementRules.class;

    public Boolean validTilePlacement(Game game) {
        try {
            checkAllRules(tilePlacementRules, game);
        } catch (InvalidMoveException exception) {
            return false;
        } catch (Exception exception) {
            // TODO add more comprehensive exception handling?
            return false;
        }
        return true;
    }

    public Boolean validVillageCreation(Game game) {
        try {
            checkAllRules(villageCreationRules, game);
        } catch (InvalidMoveException exception) {
            return false;
        } catch (Exception exception) {
            // TODO add more comprehensive exception handling?
            return false;
        }
        return true;
    }

    public Boolean validVillageExpansion(Game game) {
        try {
            checkAllRules(villageExpansionRules, game);
        } catch (InvalidMoveException exception) {
            return false;
        } catch (Exception exception) {
            // TODO add more comprehensive exception handling?
            return false;
        }
        return true;
    }

    public Boolean validTotoroPlacement(Game game) {
        try {
            checkAllRules(totoroPlacementRules, game);
        } catch (InvalidMoveException exception) {
            return false;
        } catch (Exception exception) {
            // TODO add more comprehensive exception handling?
            return false;
        }
        return true;
    }

    private void checkAllRules(Class runnableClass, Game game) throws InvalidMoveException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
        Object ruleObject = runnableClass.newInstance();
        Method[] methods = runnableClass.getDeclaredMethods();
        for (Method runnableMethod : methods) {
            runnableMethod.invoke(ruleObject, game);
        }
    }



}

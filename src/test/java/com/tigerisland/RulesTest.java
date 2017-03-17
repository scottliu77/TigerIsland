package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RulesTest {

    private Rules rules;
    private Game game;

    @Before
    public void createRules() {
        rules = new Rules();
        game = new Game(new GameSettings(new GlobalSettings()));
    }

    @Test
    public void testCanAccessRulesClass() {
        assertTrue(rules != null);
    }

    @Test
    public void testCanApplyTilePlacementRules() {
        assertTrue(rules.validTilePlacement(game));
    }

    @Test
    public void testCanApplyVillageCreationRules() {
        assertTrue(rules.validVillageCreation(game));
    }

    @Test
    public void testCanApplyVillageExpansionRules() {
        assertTrue(rules.validVillageExpansion(game));
    }

    @Test
    public void testCanApplyTotoroPlacementRules() {
        assertTrue(rules.validTotoroPlacement(game));
    }
}

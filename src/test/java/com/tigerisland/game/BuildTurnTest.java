package com.tigerisland.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BuildTurnTest {

    private Tile tile;
    private Location location;
    private Terrain settlementTerrain;
    private Player player;
    private int rotation;

    private TilePlacement tilePlacement;
    private BuildAction villageCreationBuildAction;
    private BuildAction villageExpansionBuildAction;

    @Before
    public void createDefaultMoves() {
        this.tile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.location = new Location(0, 1);
        this.settlementTerrain = Terrain.GRASSLANDS;
        this.player = new Player(Color.ORANGE, 1);
        this.rotation = 0;

        this.tilePlacement = new TilePlacement(tile, location, rotation);
        this.villageCreationBuildAction = new BuildAction(player, location, BuildActionType.VILLAGECREATION);
        this.villageExpansionBuildAction = new BuildAction(player, location, Terrain.GRASSLANDS);
    }

    @Test
    public void testCanCreateTilePlacementMove() {
        assertTrue(tilePlacement != null);
    }

    @Test
    public void testCanCreateVillageCreationMove() {
        assertTrue(villageCreationBuildAction != null);
    }

    @Test
    public void testCanCreateVillageExpansionMove() { assertTrue(villageExpansionBuildAction != null); }

    @Test
    public void testCanGetMoveTile() {
        assertTrue(tile == tilePlacement.getTile());
    }

    @Test
    public void testCanGetMoveLocation() {
        assertTrue(location == tilePlacement.getLocation());
    }

    @Test
    public void testCanGetMoveRotation() {
        assertTrue(rotation == tilePlacement.getRotation());
    }

    @Test
    public void testCanGetCreationMovePlayer() {
        assertTrue(player == villageCreationBuildAction.getPlayer());
    }

    @Test
    public void testCanGetCreationLocation() {assertTrue(location == villageCreationBuildAction.getLocation()); }

    @Test
    public void testCanGetVillageCreationType() {
        assertTrue(BuildActionType.VILLAGECREATION == villageCreationBuildAction.getBuildActionType());
    }

    @Test
    public void testCanGetExpansionMovePlayer(){ assertTrue(player == villageExpansionBuildAction.getPlayer()); }

    @Test
    public void testCanGetExpansionLocation(){ assertTrue(location == villageExpansionBuildAction.getLocation()); }

    @Test
    public void testCanGetSettlementLocation() {
        assertTrue(settlementTerrain == villageExpansionBuildAction.getExpandTerrain());
    }

    @Test
    public void testCanGetVillageExpansionType() {
        assertTrue(BuildActionType.VILLAGEEXPANSION == villageExpansionBuildAction.getBuildActionType());
    }

}

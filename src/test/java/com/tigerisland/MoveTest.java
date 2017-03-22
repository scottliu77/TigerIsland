package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MoveTest {

    private Tile tile;
    private Location location;
    private Location settlementLocation;
    private Player player;
    private int rotation;

    private Move tilePlacementMove;
    private Move villageCreationMove;
    private Move villageExpansionMove;

    @Before
    public void createDefaultMoves() {
        this.tile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.location = new Location(0, 1);
        this.settlementLocation = new Location(1,0);
        this.player = new Player(Color.ORANGE);
        this.rotation = 0;

        this.tilePlacementMove = new Move(tile, location, rotation);
        this.villageCreationMove = new Move(player, location, MoveType.VILLAGECREATION);
        this.villageExpansionMove = new Move(player, location, settlementLocation);
    }

    @Test
    public void testCanCreateTilePlacementMove() {
        assertTrue(tilePlacementMove != null);
    }

    @Test
    public void testCanCreateVillageCreationMove() {
        assertTrue(villageCreationMove != null);
    }

    @Test
    public void testCanCreateVillageExpansionMove() { assertTrue(villageExpansionMove != null); }

    @Test
    public void testCanGetMoveTile() {
        assertTrue(tile == tilePlacementMove.getTile());
    }

    @Test
    public void testCanGetMoveLocation() {
        assertTrue(location == tilePlacementMove.getLocation());
    }

    @Test
    public void testCanGetMoveRotation() {
        assertTrue(rotation == tilePlacementMove.getRotation());
    }

    @Test
    public void testCanGetCreationMovePlayer() {
        assertTrue(player == villageCreationMove.getPlayer());
    }

    @Test
    public void testCanGetCreationLocation() {assertTrue(location == villageCreationMove.getLocation()); }

    @Test
    public void testCanGetVillageCreationType() {
        assertTrue(MoveType.VILLAGECREATION == villageCreationMove.getMoveType());
    }

    @Test
    public void testCanGetExpansionMovePlayer(){ assertTrue(player == villageExpansionMove.getPlayer()); }

    @Test
    public void testCanGetExpansionLocation(){ assertTrue(location == villageExpansionMove.getLocation()); }

    @Test
    public void testCanGetSettlementLocation() {
        assertTrue(settlementLocation == villageExpansionMove.getSettlementLocation());
    }

    @Test
    public void testCanGetVillageExpansionType() {
        assertTrue(MoveType.VILLAGEEXPANSION == villageExpansionMove.getMoveType());
    }

}

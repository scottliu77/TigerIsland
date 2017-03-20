package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MoveTest {

    private Tile tile;
    private Location location;
    private Player player;
    private int rotation;

    private Move tilePlacementMove;
    private Move villageExpansionMove;

    @Before
    public void createDefaultMoves() {
        this.tile = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.location = new Location(0, 1);
        this.player = new Player(Color.ORANGE);
        this.rotation = 0;

        this.tilePlacementMove = new Move(tile, location, rotation);
        this.villageExpansionMove = new Move(player, location, MoveType.VILLAGEEXPANSION);
    }

    @Test
    public void testCanCreateTilePlacementMove() {
        assertTrue(tilePlacementMove != null);
    }

    @Test
    public void testCanCreateVillageExpansionMove() {
        assertTrue(villageExpansionMove != null);
    }

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
    public void testCanGetMovePlayer() {
        assertTrue(player == villageExpansionMove.getPlayer());
    }

    @Test
    public void testCanGetMoveType() {
        assertTrue(MoveType.VILLAGEEXPANSION == villageExpansionMove.getMoveType());
    }
}

package com.tigerisland;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BoardTest{

    private Board board;
    private Tile tile;
    private Location location;

    @Before
    public void createBoard() {
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.location = new Location(0,0);
    }

    @Test
    public void testCanCreateBoard() {
        assertTrue(board != null);
    }

    @Test
    public void testCreatedBoardHasPlacedHexes() {
        assertTrue(board.placedHexes != null);
    }

    @Test
    public void testCreatedBoardHasEdgeSpaces() {
        assertTrue(board.edgeSpaces != null);
    }

    @Test
    public void testLocationUnavailable() {
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Location location2 = new Location(0,0);
        assertFalse(board.isAnAvailableSpace(location2));
    }

    @Test
    public void testHexAtLocation(){
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Tile tile2 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location2 = new Location(2,1);
        assertFalse(board.hexExistsAtLocation(location2));
    }

    @Test
    public void testCanPlaceTile() {
        board.placeTile(tile, location, 0);
        TextGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());
    }
}
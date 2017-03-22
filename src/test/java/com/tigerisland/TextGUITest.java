package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

public class TextGUITest {

    private TextGUI textGUI;
    private Board board;
    private Tile tile;
    private Tile tile2;
    private Tile tile3;
    private Tile tile4;
    private Location location;
    private Location location2;
    private Location location3;
    private Location location4;

    @Before
    public void createBasicMocks() {
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.tile2 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        this.tile3 = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        this.location = new Location(0, 0);
        this.location2 = new Location(-1, 0);
        this.location3 = new Location(-2, 0);
        this.location4 = new Location(0, -1);

        try {
            board.placeTile(tile, location, 0);
            board.placeTile(tile2, location2, 60);
            board.placeTile(tile3, location3, 120);
            board.placeTile(tile4, location4, 240);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanPrintPlacedHexLocations() {
        TextGUI.printPlacedHexLocations(board.locationsOfPlacedHexes());
    }

    @Test
    public void testCanPrintEdgeSpaces() {
        TextGUI.printEdgeSpaces(board.edgeSpaces);
    }

    @Test
    public void testCanPrintPlacedHexTiles() {
        TextGUI.printPlacedHexTiles(board.hexesOfPlacedHexes());
    }

    @Test
    public void testCanPrintMap() {
        TextGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());
    }
}

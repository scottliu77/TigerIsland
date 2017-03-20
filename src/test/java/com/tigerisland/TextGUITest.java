package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextGUITest {

    private TextGUI textGUI;
    private Board board;
    private Tile tile;
    private Tile tile2;
    private Tile tile3;
    private Location location;
    private Location location2;
    private Location location3;

    @Before
    public void createBasicMocks() {
        this.textGUI = new TextGUI();
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.tile2 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        this.tile3 = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.location = new Location(0,0);
        this.location2 = new Location(-1, 0);
        this.location3 = new Location(-2, 0);
    }

    @Test
    public void testCanCreateTextGUI() {
        assertTrue(textGUI != null);
    }

    @Test
    public void testCanPrintPlacedHexLocations() {
        try {
            board.placeTile(tile, location, 0);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        TextGUI.printPlacedHexLocations(board.locationsOfPlacedHexes());
    }

    @Test
    public void testCanPrintEdgeSpaces() {
        try {
            board.placeTile(tile, location, 0);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        TextGUI.printEdgeSpaces(board.edgeSpaces);
    }

    @Test
    public void testCanPrintPlacedHexTiles() {
        try {
            board.placeTile(tile, location, 0);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        TextGUI.printPlacedHexTiles(board.hexesOfPlacedHexes());
    }

    @Test
    public void testCanPrintMap() {
        try {
            board.placeTile(tile, location, 0);
            board.placeTile(tile2, location2, 60);
            board.placeTile(tile3, location3, 120);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        TextGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());
    }



}

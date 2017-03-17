package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextGUITest {

    private TextGUI textGUI;
    private Board board;
    private Tile tile;
    private Location location;

    @Before
    public void createBasicMocks() {
        this.textGUI = new TextGUI();
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.location = new Location(0,0);
    }

    @Test
    public void testCanCreateTextGUI() {
        assertTrue(textGUI != null);
    }

    @Test
    public void testCanPrintPlacedHexLocations() {
        board.placeTile(tile, location, 0);
        TextGUI.printPlacedHexLocations(board.locationsOfPlacedHexes());
    }

    @Test
    public void testCanPrintEdgeSpaces() {
        board.placeTile(tile, location, 0);
        TextGUI.printEdgeSpaces(board.edgeSpaces);
    }

    @Test
    public void testCanPrintPlacedHexTiles() {
        board.placeTile(tile, location, 0);
        TextGUI.printPlacedHexTiles(board.hexesOfPlacedHexes());
    }

    @Test
    public void testCanPrintMap() {
        board.placeTile(tile, location, 0);
        TextGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());
    }



}

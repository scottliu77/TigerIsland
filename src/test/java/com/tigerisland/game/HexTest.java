package com.tigerisland.game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class HexTest {

    private Hex hex;

    @Before
    public void createHex() {
        this.hex = new Hex("dummyID" , Terrain.VOLCANO);
    }

    @Test
    public void testCanCreateHexUsingIDandTerrain() {
        assertTrue(hex != null);
    }

    @Test
    public void testCanCreateDummyHexUsingNoParameters() {
        Hex dummyHex = new Hex();
        assertTrue(dummyHex != null);
    }

    @Test
    public void testCanCreateHexUsingIDTerrainAndHeight() {
        Hex triParameterHex = new Hex("dummyID", Terrain.GRASSLANDS, 0);
        assertTrue(triParameterHex != null);
    }

    @Test
    public void testCanGetHexTerrain() {
        assertTrue(hex.getHexTerrain() == Terrain.VOLCANO);
    }

    @Test
    public void testCanGetHexHeight() {
        assertTrue(hex.getHeight() == 1);
    }

    @Test
    public void testCanGetHexContentType() {
        assertTrue(hex.getPieceType().equals("Empty"));
    }

    @Test
    public void testCanGetNonEmptyContentType() {
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        assertTrue(hex.getPieceType().equals("Totoro"));
    }

    @Test
    public void testCanGetHexContentCount() {
        assertTrue(hex.getPieceCount() == 0);
    }

    @Test
    public void testCanGetHexID() {
        assertTrue(hex.getTileID().equals("dummyID"));
    }

    @Test
    public void testCanGetHexIDsubstring() {
        assertTrue(hex.getIDFirstChars(2).equals("du"));
    }

    @Test
    public void testCanAddVillagerToHex() {
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        assertTrue(hex.getPieceType() == "Villager" && hex.getPieceCount() == 1);
    }

    @Test
    public void testCanAddTotoroToHex() {
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        assertTrue(hex.getPieceType() == "Totoro" && hex.getPieceCount() == 1);
    }

    @Test
    public void testCanTellIfHexIsNotVolcano() {
        this.hex = new Hex("dummyID", Terrain.ROCKY);
        assertTrue(hex.isNotVolcano());
    }

    @Test
    public void testCanTellIfHexIsVolcano() {
        this.hex = new Hex("dummyID", Terrain.VOLCANO);
        assertFalse(hex.isNotVolcano());
    }

    @Test
    public void testCanTellIfHexIsEmpty() {
        this.hex = new Hex("dummyID", Terrain.LAKE);
        assertTrue(hex.isEmpty());
    }

}

package com.tigerisland;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SettlementTest {

    private Settlement settlement;
    private ArrayList<PlacedHex> allPlacedHexes;

    private Hex hex1;
    private Hex hex2;
    private Hex hex3;
    private Hex hex4;

    private Location location1;
    private Location location2;
    private Location location3;
    private Location location4;

    private PlacedHex placedHex1;
    private PlacedHex placedHex2;
    private PlacedHex placedHex3;
    private PlacedHex placedHex4;



    @Before
    public void createSettlement(){
        createHexes();
        createLocations();
        createPlacedHexes();
        createAllPlacedHexes();
        placedHex1.getHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        placedHex3.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        placedHex4.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
    }

    private void createHexes() {
        this.hex1 = new Hex("hex1", Terrain.GRASSLANDS);
        this.hex2 = new Hex("hex2", Terrain.JUNGLE);
        this.hex3 = new Hex("hex3", Terrain.LAKE);
        this.hex4 = new Hex("hex4", Terrain.ROCKY);
    }

    private void createLocations() {
        location1 = new Location(1, 1);
        location2 = new Location(0, 1);
        location3 = new Location(3,3);
        location4 = new Location(2,3);
    }

    private void createPlacedHexes() {
        placedHex1 = new PlacedHex(hex1, location1);
        placedHex2 = new PlacedHex(hex2, location2);
        placedHex3 = new PlacedHex(hex3, location3);
        placedHex4 = new PlacedHex(hex4, location4);
    }

    private void createAllPlacedHexes() {
        allPlacedHexes = new ArrayList<PlacedHex>();
        allPlacedHexes.add(0, placedHex1);
        allPlacedHexes.add(0, placedHex2);
        allPlacedHexes.add(0, placedHex3);
        allPlacedHexes.add(0, placedHex4);
    }

    @Test
    public void testCanCreateSettlementOnHexContainingPiece() {
        settlement = new Settlement(placedHex1, allPlacedHexes);
        assertTrue(settlement != null);
    }

    @Test
    public void testCantCreateASettlementWithoutPiecePlaced() {
        try {
            Settlement settlement = new Settlement(placedHex2, allPlacedHexes);
        } catch (NullPointerException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testSizeOfSolitaryHex() {
        settlement = new Settlement(placedHex1, allPlacedHexes);
       assertTrue(settlement.size() == 1);
    }

    @Test
    public void testSizeOfNonSolitaryHex() {
        settlement = new Settlement(placedHex4, allPlacedHexes);
        assertTrue(settlement.size() == 2);
    }

    @Test
    public void testSizeOfSettlementOnBorderOfAnotherSettlement(){
        Hex hex5 = new Hex("hex5", Terrain.ROCKY);
        Hex hex6 = new Hex("hex6", Terrain.LAKE);
        Hex hex7 = new Hex("hex7", Terrain.GRASSLANDS);

        Location loc5 = new Location(2, 1);
        Location loc6 = new Location(2, 2);
        Location loc7 = new Location(0, 2);

        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);
        PlacedHex placedHex7 = new PlacedHex(hex7, loc7);

        placedHex5.getHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        placedHex6.getHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        placedHex7.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.TOTORO), 1);

        allPlacedHexes.add(placedHex5);
        allPlacedHexes.add(placedHex6);
        allPlacedHexes.add(placedHex7);

        settlement = new Settlement(placedHex6, allPlacedHexes);
        assertTrue(settlement.size() == 3);
    }

    @Test
    public void testContainsTotoro() {
        settlement = new Settlement(placedHex1, allPlacedHexes);
        assertTrue(settlement.containsTotoro());
    }

    @Test
    public void testCanTellWhenSettlementDoesntHaveTotoro() {
        settlement = new Settlement(placedHex4, allPlacedHexes);
        assertFalse(settlement.containsTotoro());
    }

}
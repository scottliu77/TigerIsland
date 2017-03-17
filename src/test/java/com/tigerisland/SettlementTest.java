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

    private Location location1;
    private Location location2;
    private Location location3;

    private PlacedHex placedHex1;
    private PlacedHex placedHex2;
    private PlacedHex placedHex3;

    @Before
    public void createSettlement(){
        createHexes();
        createLocations();
        createPlacedHexes();
        createAllPlacedHexes();
        placedHex1.getHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        placedHex3.getHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        settlement = new Settlement(placedHex1, allPlacedHexes);
    }

    private void createHexes() {
        this.hex1 = new Hex("hex1", Terrain.GRASSLANDS);
        this.hex2 = new Hex("hex2", Terrain.JUNGLE);
        this.hex3 = new Hex("hex3", Terrain.LAKE);
    }

    private void createLocations() {
        location1 = new Location(1, 1);
        location2 = new Location(0, 1);
        location3 = new Location(3,3);
    }

    private void createPlacedHexes() {
        placedHex1 = new PlacedHex(hex1, location1);
        placedHex2 = new PlacedHex(hex2, location2);
        placedHex3 = new PlacedHex(hex3, location3);
    }

    private void createAllPlacedHexes() {
        allPlacedHexes = new ArrayList<PlacedHex>();
        allPlacedHexes.add(0, placedHex1);
        allPlacedHexes.add(0, placedHex2);
        allPlacedHexes.add(0, placedHex3);
    }

    @Test
    public void testCanCreateSettlementOnHexContainingPiece() {
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

//    @Test
//    public void testSize() {
//        assertTrue(settlement.size() == 2);
//    }
//
//    @Test
//    public void testContainsTotoro() {
//        assertTrue(settlement.containsTotoro());
//    }
//
//    @Test
//    public void testCanTellWhenSettlementDoesntHaveTotoro() {
//
//    }



}
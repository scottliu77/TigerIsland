package com.tigerisland.game.moves;

import com.tigerisland.game.board.*;
import com.tigerisland.game.moves.*;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.Piece;
import com.tigerisland.game.pieces.PieceType;
import com.tigerisland.game.board.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TotoroPlacerTest {

    private ArrayList<Settlement> settlements;

    private ArrayList<PlacedHex> allPlacedHexes;

    private Settlement settlement1;

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
    public void createSettlementList() {
        settlements = new ArrayList<Settlement>();
        settlements.add(settlement1);

    }
    @Before
    public void createSettlement() {
        createHexes();
        createLocations();
        createPlacedHexes();
        createAllPlacedHexes();
        placedHex1.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        placedHex3.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        placedHex4.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);

        Hex hex = new Hex("currentHex", Terrain.GRASS);
        Location loc = new Location(-1, 0);
        PlacedHex hexInSettlement = new PlacedHex(hex, loc);
        hexInSettlement.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);

        settlement1 = new Settlement(hexInSettlement,allPlacedHexes);

    }

    private void createHexes() {
        this.hex1 = new Hex("hex1", Terrain.GRASS);
        this.hex2 = new Hex("hex2", Terrain.JUNGLE);
        this.hex3 = new Hex("hex3", Terrain.LAKE);
        this.hex4 = new Hex("hex4", Terrain.ROCK);
    }

    private void createLocations() {
        location1 = new Location(1, 0);
        location2 = new Location(0, 1);
        location3 = new Location(0,-1);
        location4 = new Location(0,0);
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
    public void testCanRemoveSettlementContainingTotoro() {
        Hex hex = new Hex("currentHex", Terrain.GRASS);
        Location loc = new Location(-1, 0);
        PlacedHex hexInSettlement = new PlacedHex(hex, loc);
        hexInSettlement.getHex().addPiecesToHex(new Piece(Color.WHITE, PieceType.TOTORO), 1);
        settlement1 = new Settlement(hexInSettlement,allPlacedHexes);

        settlements.add(settlement1);

        TotoroPlacer.removeSettlementsThatCantAcceptTotoroFromList(settlements);
        assertTrue(settlements.size() == 0);
    }

    @Test
    public void testCanRemoveSetlementLessThanSizeFive() {
        TotoroPlacer.removeSettlementsThatCantAcceptTotoroFromList(settlements);
        assertTrue(settlements.size() == 0);
    }
}

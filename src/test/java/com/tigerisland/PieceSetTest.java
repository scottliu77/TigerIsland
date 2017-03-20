package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceSetTest {

    private PieceSet pieceSet;

    @Before
    public void createPieces() {
        this.pieceSet = new PieceSet(Color.BLACK);
    }

    @Test
    public void canCreatePieces() {
        assertTrue(pieceSet != null);
    }

    @Test
    public void testCanCreateNonEmptySets() {
        assertTrue(pieceSet.villagerSet.size() > 0 && pieceSet.totoroSet.size() > 0);
    }

    @Test
    public void testCanCreateExactlyTwentyVillager() {
        assertTrue(pieceSet.villagerSet.size() == 20);
    }

    @Test
    public void testCanCreateExactlyThreeTotoro() {
        assertTrue(pieceSet.totoroSet.size() == 3);
    }

    @Test
    public void testCanSetColorOfVillager() {
        Piece meep = pieceSet.villagerSet.get(0);
        assertTrue(meep.getColor().getColorString() == "Black");
    }

    @Test
    public void testCanSetColorOfTotoro() {
        Piece totororo = pieceSet.totoroSet.get(0);
        assertTrue(totororo.getColor().getColorString() == "Black");
    }

    @Test
    public void testCanGetNumberOfVillagersRemaining() {
        assertTrue(pieceSet.getNumberOfVillagersRemaining() == pieceSet.villagerSet.size());
    }

    @Test
    public void testCanGetNumberOfTotoroRemaining() {
        assertTrue(pieceSet.getNumberOfTotoroRemaining() == pieceSet.totoroSet.size());
    }

    @Test
    public void testCanPlaceTotoro() {
        try {
            Piece totoro = pieceSet.placeTotoro();
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertTrue(pieceSet.totoroSet.size() == 2);
    }

    @Test
    public void testCanPlaceAllTotoro() {
        for(int numTotoro = 0; numTotoro < 3; numTotoro ++) {
            try {
                Piece totoro = pieceSet.placeTotoro();
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        assertTrue(pieceSet.totoroSet.size() == 0);
    }

    @Test
    public void testCanPlaceVillager() {
        try {
            Piece villager = pieceSet.placeVillager();
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertTrue(pieceSet.villagerSet.size() == 19);
    }

    @Test
    public void testCanPlaceAllVillager() {
        for(int numVillager = 0; numVillager < 20; numVillager++) {
            try {
                Piece villager = pieceSet.placeVillager();
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        assertTrue(pieceSet.villagerSet.size() == 0);
    }

    @Test
    public void testCannotPlaceMoreWhenHavingZero() {
        try {
            for (int numVillager = 0; numVillager < 21; numVillager++) {
                Piece villager = pieceSet.placeVillager();
            }
            assertTrue(false);
        } catch (InvalidMoveException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanPlaceMultipleVillagersAtOnce() {
        Piece villagers = null;
        try {
            villagers = pieceSet.placeMultipleVillagers(3);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertTrue(villagers != null);
    }

    @Test
    public void testCannotPlaceTooManyVillagersAtOnce() {
        try {
            Piece villager = pieceSet.placeMultipleVillagers(21);
            assertTrue(false);
        } catch (InvalidMoveException except) {
            assertTrue(true);
        }
    }

    @Test
    public void testTryingToPlaceTooManyVillagesDoesntBreakCount() {
        try {
            Piece villager = pieceSet.placeMultipleVillagers(21);
            assertTrue(false);
        } catch (InvalidMoveException except) {
            assertTrue(pieceSet.getNumberOfVillagersRemaining() == 20);
        }
    }

    @Test
    public void testCanCheckInventoryEmpty() {
        try {
            for(int numVillager = 0; numVillager < 20; numVillager++) {
                Piece villager = pieceSet.placeVillager();
            }
            for(int numTotoro = 0; numTotoro < 3; numTotoro++) {
                Piece totoro = pieceSet.placeTotoro();
            }
            assertTrue(pieceSet.inventoryEmpty() == true);
        } catch (InvalidMoveException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testCanCheckInventoryNotEmpty() {
        for(int numVillager = 0; numVillager < 2; numVillager++) {
            try {
                Piece villager = pieceSet.placeVillager();
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        assertTrue(pieceSet.inventoryEmpty() == false);
    }


}

package com.tigerisland.game.pieces;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.Piece;
import com.tigerisland.game.pieces.PieceSet;
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
        assertTrue(pieceSet.villagerSet.size() > 0 && pieceSet.totoroSet.size() > 0 && pieceSet.tigerSet.size() > 0);
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
    public void testCanCreateExactlyTwoTiger() {
        assertTrue(pieceSet.tigerSet.size() == 2);
    }

    @Test
    public void testCanSetColorOfVillager() {
        Piece villager = pieceSet.villagerSet.get(0);
        assertTrue(villager.getColor().getColorString() == "Black");
    }

    @Test
    public void testCanSetColorOfTotoro() {
        Piece totoro = pieceSet.totoroSet.get(0);
        assertTrue(totoro.getColor().getColorString() == "Black");
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
    public void testCanGetNumberOfTigersRemaining() {
        assertTrue(pieceSet.getNumberOfTigersRemaining() == pieceSet.tigerSet.size());
    }

    @Test
    public void testCanPlaceTiger() {
        try {
            Piece tiger = pieceSet.placeTiger();
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertTrue(pieceSet.tigerSet.size() == 1);
    }

    @Test
    public void testCanPlaceAllTiger() {
        for(int numTiger = 0; numTiger < 2; numTiger++) {
            try {
                Piece tiger = pieceSet.placeTiger();
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        assertTrue(pieceSet.tigerSet.size() == 0);
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
        for(int numTotoro = 0; numTotoro < 3; numTotoro++) {
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
            for(int numTiger = 0; numTiger < 2; numTiger++) {
                Piece tiger = pieceSet.placeTiger();
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

    @Test
    public void testCannotPlaceTigerIfNoPiecesExist() {
        String errorMessage = new String();
        for(int numTiger = 0; numTiger < 3; numTiger++) {
            try {
                Piece tiger = pieceSet.placeTiger();
            } catch (InvalidMoveException e) {
                errorMessage = e.getMessage();
            }
        }
        assertTrue(errorMessage.equals("No tiger remaining in game inventory"));
    }

}

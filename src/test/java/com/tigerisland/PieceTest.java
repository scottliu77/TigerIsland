package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceTest {

    private Piece villager;
    private Piece totoro;

    @Before
    public void createPiece() {
        this.villager = new Piece(Color.BLACK, PieceType.VILLAGER);
        this.totoro = new Piece(Color.WHITE, PieceType.TOTORO);
    }

    @Test
    public void testCanCreatePiece() {
        assertTrue(this.villager != null);
    }

    @Test
    public void testCanGetPieceTypeString() {
        assertTrue(this.villager.getTypeString() == "Villager");
    }

    @Test
    public void testCanGetPieceCode() {
        assertTrue(this.villager.getType() == PieceType.VILLAGER);
    }

    @Test
    public void testCanGetPieceColorString() {
        assertTrue(this.totoro.getColorString() == "White");
    }

    @Test
    public void testCanGetPieceColor() {
        assertTrue(this.totoro.getColor() == Color.WHITE);
    }

}

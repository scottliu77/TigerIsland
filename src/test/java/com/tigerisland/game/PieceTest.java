package com.tigerisland.game;

import com.tigerisland.game.Color;
import com.tigerisland.game.Piece;
import com.tigerisland.game.PieceType;
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
        assertTrue(villager != null);
    }

    @Test
    public void testCanGetPieceTypeString() {
        assertTrue(villager.getType().getTypeString() == "Villager");
    }

    @Test
    public void testCanGetPieceCode() {
        assertTrue(villager.getType() == PieceType.VILLAGER);
    }

    @Test
    public void testCanGetPieceColorString() {
        assertTrue(totoro.getColor().getColorString() == "White");
    }

    @Test
    public void testCanGetPieceColor() {
        assertTrue(totoro.getColor() == Color.WHITE);
    }

}

package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ColorTest {

    private Color color;

    @Before
    public void createVillagerColor() {
        this.color = Color.BLACK;
    }

    @Test
    public void testCanCreateVillagerColor() {
        assertTrue(color != null);
    }

    @Test
    public void testCanGetColorString() {
        assertTrue(color.getColorString() == "Black");
    }
}

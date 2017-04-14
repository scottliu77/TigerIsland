package com.tigerisland.client;

import com.tigerisland.game.board.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OfflineDeckTest {

    private OfflineDeck offlineDeck;
    ArrayList<Tile> baseDeck;
    ArrayList<Tile> altDeck;

    @Before
    public void createDeck() {
        this.offlineDeck = new OfflineDeck();
        offlineDeck.createOfflineDeck();
        this.baseDeck = offlineDeck.tileDeck;
    }

    @Test
    public void testCanCreateDeck() {
        assertTrue(offlineDeck != null);
    }

    @Test
    public void testCanCreateDeckViaCopyConstructor() {
        OfflineDeck offlineDeckCopy = new OfflineDeck(offlineDeck);
        assertTrue(offlineDeckCopy != offlineDeck);
    }

    @Test
    public void testCanCreateNonEmptyDeck() {
        assertTrue(offlineDeck.getDeckSize() > 0);
    }

    @Test
    public void testCanCreateDeckOfSize48() {
        assertTrue(offlineDeck.getDeckSize() == 48);
    }

    @Test
    public void testCanShuffleDeckOnceCreated() {
        String baseDeckString = Arrays.toString(offlineDeck.tileDeck.toArray());
        offlineDeck.shuffleDeck();
        String altDeckString = Arrays.toString(offlineDeck.tileDeck.toArray());
        assertNotEquals(baseDeckString, altDeckString);
    }

    @Test
    public void testEachTileHasUniqueId() {
        int deckSize = offlineDeck.getDeckSize();

        Set<String> listOfUniqueIDs = new HashSet<String>();

        for (Tile tile : offlineDeck.tileDeck) {
            listOfUniqueIDs.add(tile.getTileID());
        }

        assertTrue(deckSize == listOfUniqueIDs.size());
    }

    @Test
    public void testCanDrawTile() {
        int deckSize = offlineDeck.getDeckSize();
        offlineDeck.drawTile();
        assertTrue(deckSize - 1 == offlineDeck.getDeckSize());
    }
}
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class DeckTest{

    private Deck deck;
    ArrayList<Tile> baseDeck;
    ArrayList<Tile> altDeck;

    @Before
    public void createDeck() {
        this.deck = new Deck();
        this.baseDeck = deck.tileDeck;
    }

    @Test
    public void testCanCreateDeck() {
        assertTrue(this.deck != null);
    }

    @Test
    public void testCanCreateNonEmptyDeck() {
        assertTrue(this.deck.getDeckSize() > 0);
    }

    @Test
    public void testCanCreateDeckOfSize48() {
        assertTrue(this.deck.getDeckSize() == 48);
    }

    @Test
    public void testCanShuffleDeckOnceCreated() {
        String baseDeckString = Arrays.toString(this.deck.tileDeck.toArray());
        this.deck.shuffleDeck();
        String altDeckString = Arrays.toString(this.deck.tileDeck.toArray());
        assertNotEquals(baseDeckString, altDeckString);
    }
}
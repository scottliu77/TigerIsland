package com.tigerisland;

import com.tigerisland.game.Terrain;
import com.tigerisland.game.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    protected ArrayList<Tile> tileDeck;

    public Deck() {
        this.tileDeck = new ArrayList<Tile>();
    }

    public Deck(Deck deck) {
        this.tileDeck = new ArrayList<Tile>();
        for(Tile tile : deck.tileDeck) {
            this.tileDeck.add(new Tile(tile));
        }
    }

    public void createOfflineDeck() {
        this.populateDeck();
        this.shuffleDeck();
    }

    private void populateDeck() {
        for( Terrain typeOne : Terrain.values()) {
            for( Terrain typeTwo : Terrain.values()) {
                if(typeOne != Terrain.VOLCANO && typeTwo != Terrain.VOLCANO) {
                    addThreeTilesOfThisType(typeOne, typeTwo);
                }
            }
        }
    }

    private void addThreeTilesOfThisType(Terrain typeOne, Terrain typeTwo) {
        tileDeck.add(new Tile(typeOne, typeTwo));
        tileDeck.add(new Tile(typeOne, typeTwo));
        tileDeck.add(new Tile(typeOne, typeTwo));
    }

    public void shuffleDeck() {
        Collections.shuffle(this.tileDeck);
    }

    public int getDeckSize() {
        return tileDeck.size();
    }

    public Tile drawTile() throws IndexOutOfBoundsException {
        return tileDeck.remove(0);
    }
}

package com.tigerisland.client;

import com.tigerisland.TigerIsland;
import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.board.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OfflineDeck {

    protected ArrayList<Tile> tileDeck;

    private Random random;

    public OfflineDeck() {
        this.tileDeck = new ArrayList<Tile>();
        this.random = new Random();
    }

    public OfflineDeck(OfflineDeck offlineDeck) {
        this.tileDeck = new ArrayList<Tile>();
        for(Tile tile : offlineDeck.tileDeck) {
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
        while(getDeckSize() != 48) {
            int index = random.nextInt(getDeckSize());
            tileDeck.remove(index);
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

    public ArrayList<Tile> getTileDeck() {
        return tileDeck;
    }
}

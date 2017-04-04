package com.tigerisland.game;

import java.util.ArrayList;

/**
 * Created by johnzoldos on 4/4/17.
 */
public class SettlementAndTerrainListPair {
    private Settlement settlement;
    private ArrayList<Terrain> terrainList;


    public SettlementAndTerrainListPair(Settlement settlement, ArrayList<Terrain> terrainList){
        this.settlement = settlement;
        this.terrainList = terrainList;
    }
}

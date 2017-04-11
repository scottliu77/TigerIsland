package com.tigerisland.game;

import java.util.UUID;

public class Tile {

    private Hex volcanoHex;
    private Hex leftHex;
    private Hex rightHex;

    private String uniqueID = UUID.randomUUID().toString();

    public Tile(Terrain left, Terrain right) {
        this.volcanoHex = new Hex(this.uniqueID , Terrain.VOLCANO);
        this.leftHex = new Hex(this.uniqueID , left);
        this.rightHex = new Hex(this.uniqueID , right);
    }

    public Tile(Tile tile) {
        String tileID = tile.getCenterHex().getTileID();
        this.volcanoHex = new Hex(tileID, tile.getCenterHex().getHexTerrain());
        this.leftHex = new Hex (tileID, tile.getLeftHex().getHexTerrain());
        this.rightHex = new Hex(tileID, tile.getRightHex().getHexTerrain());
    }

    public Hex getCenterHex() {
        return volcanoHex;
    }

    public Hex getLeftHex() {
        return leftHex;
    }

    public Hex getRightHex() {
        return rightHex;
    }

    public String getTileID() {
        return this.uniqueID;
    }

}

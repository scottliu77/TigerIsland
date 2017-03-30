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
        this.volcanoHex = tile.getCenterHex();
        this.leftHex = tile.getLeftHex();
        this.rightHex = tile.getRightHex();
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

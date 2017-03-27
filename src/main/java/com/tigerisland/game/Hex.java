package com.tigerisland.game;

import java.util.*;

public class Hex {

    private Terrain terrain;
    private int height = 1;
    private int pieceCount = 0;
    private Piece piece;
    private String tileID;

    public Hex() {
        this.tileID = null;
        this.pieceCount = -1;
    }

    public Hex(String tileID, Terrain terrain) {
        this.tileID = tileID;
        this.terrain = terrain;
    }

    public Hex(String tileID, Terrain terrain, int height) {
        this.tileID = tileID;
        this.terrain = terrain;
        this.height = height;
    }

    public Hex(Hex hex) {
        this.terrain = hex.getHexTerrain();
        this.height = hex.getHeight();
        this.pieceCount = hex.getPieceCount();
        this.piece = hex.getPiece();
        this.tileID = hex.getTileID() + " clone";
    }

    public Terrain getHexTerrain() {
        return terrain;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int newHeight ) { this.height = newHeight; }

    public int getPieceCount() {
        return pieceCount;
    }

    public String getPieceType() {
        if(piece == null) {
            return "Empty";
        } else {
            return piece.getType().getTypeString();
        }
    }

    public Color getPieceColor() {
        return piece.getColor();
    }

    public String getTileID() {
        if(tileID == null) {
            return "dummyID";
        } else {
            return tileID;
        }
    }

    public String getIDFirstChars(int size) {
        size = Math.abs(size);
        size = Math.min(tileID.length(), size);

        return tileID.substring(0, size);
    }

    public Piece getPiece() {
        return piece;
    }

    public void addPiecesToHex(Piece piece, int count) {
        this.piece = piece;
        this.pieceCount = count;
    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii = 0; ii < list.size(); ii++){
            Hex hex = list.get(ii);
            String hexTerrain = "T="+hex.terrain.getType();
            String hexHeight = "H="+Integer.toString(hex.getHeight());
            String hexPieceType = hex.getPieceType();
            String hexPieceCount = Integer.toString(hex.getPieceCount());
            String hexID = "ID="+hex.getIDFirstChars(8);
            System.out.println(hexTerrain + " : " + hexHeight + " : " + hexPieceType + '-' + hexPieceCount + " : " + hexID);
        }
    }

    public boolean isNotVolcano() {
        return !this.getHexTerrain().equals(Terrain.VOLCANO);
    }
}

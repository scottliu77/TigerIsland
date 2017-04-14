package com.tigerisland.game.board;

import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.Piece;

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

    public boolean isNotVolcano() {
        return !this.getHexTerrain().equals(Terrain.VOLCANO);
    }
    public boolean isEmpty(){
        return this.getPieceType().equals("Empty");
    }
}

import java.util.*;

public class Hex {

    private Terrain terrain;
    private int height = 1;
    private int pieceCount = 0;
    private Piece piece;
    private String tileID;

    public Hex() {
        this.tileID = null;
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

    public Terrain getHexTerrain() {
        return terrain;
    }

    public int getHeight() {
        return height;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public String getPieceType() {
        if(piece == null) {
            return "Empty";
        } else {
            return piece.getTypeString();
        }
    }

    public String getPieceColor() {
        return piece.getColorString();
    }

    public String getID() {
        return tileID;
    }

    public String getIDfirstChars(int size) {
        size = Math.abs(size);
        size = Math.min(tileID.length(), size);

        return tileID.substring(0, size);
    }

    public void addPiecesToHex(Piece piece, int count) {
        this.piece = piece;
        this.pieceCount = count;
    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii = 0; ii < list.size(); ii++){
            Hex hex = list.get(ii);
            System.out.println(hex.terrain.getType()+":H="+Integer.toString(hex.getHeight())+":ID="+hex.getID());
        }
    }

}

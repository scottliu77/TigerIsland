import java.util.*;

public class Hex {

    private Terrain terrain;
    private int height;
    private int pieceCount;
    private Placeable pieceType;
    private PlayerColor player;
    private String tileID;

    public Hex() {
        this.pieceCount = 0;
        this.pieceType = null;
        this.tileID = null;
    }

    public Hex(String tileID, Terrain terrain) {
        this.pieceCount = 0;
        this.pieceType = null;
        this.tileID = tileID;
        this.terrain = terrain;
    }

    public Hex(String tileID, Terrain terrain, int height) {
        this.pieceCount = 0;
        this.pieceType = null;
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
        return this.pieceCount;
    }

    public String getPieceType() {
        if(this.pieceType == null) {
            return "Empty";
        }
        else {
            return this.pieceType.getPiece();
        }
    }

    public String getPlayer() {
        return this.player.getColor();
    }

    public String getID() {
        return this.tileID;
    }

    public String getIDfirstChars(int size) {
        size = Math.abs(size);
        size = Math.min(this.tileID.length(), size);

        return this.tileID.substring(0, size);
    }

    public void addPiecesToHex(Placeable piece, int pieceCount, PlayerColor owner) {
        this.pieceType = piece;
        this.pieceCount = pieceCount;
        this.player = owner;
    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii = 0; ii < list.size(); ii++){
            Hex hex = list.get(ii);
            System.out.println(hex.terrain.getType()+":H="+Integer.toString(hex.getHeight())+":ID="+hex.getID());
        }
    }

}

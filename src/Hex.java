import java.util.*;

public class Hex {

    private Terrain terrain;
    private int height;
    private int contentCount;
    private Placeable contentType;
    private String tileID;

    public Hex() {
        this.contentCount = 0;
        this.contentType = null;
        this.tileID = null;
    }

    public Hex(String tileID, Terrain terrain) {
        this.contentCount = 0;
        this.contentType = null;
        this.tileID = tileID;
        this.terrain = terrain;
    }

    public Hex(String tileID, Terrain terrain, int height) {
        this.contentCount = 0;
        this.contentType = null;
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

    public int getContentCount() {
        return this.contentCount;
    }

    public String getContentType() {
        if(this.contentType == null) {
            return "Empty";
        }
        else {
            return this.contentType.getPiece();
        }
    }

    public String getID() {
        return this.tileID;
    }

    public String getIDfirstChars(int size) {
        size = Math.abs(size);
        size = Math.min(this.tileID.length(), size);

        return this.tileID.substring(0, size);
    }

    public void addPiecesToHex(Placeable piece, int pieceCount) {
        this.contentType = piece;
        this.contentCount = pieceCount;
    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii = 0; ii < list.size(); ii++){
            Hex hex = list.get(ii);
            System.out.println(hex.terrain.getType()+":H="+Integer.toString(hex.getHeight())+":ID="+hex.getID());
        }
    }

}

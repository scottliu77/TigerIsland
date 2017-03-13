import java.util.*;

public class Hex {

    private Terrain terrain;
    private int height;
    private int contentCount;
    private String contentType;
    private String tileID;

    public Hex() {
        this.contentCount = 0;
        this.contentType = null;
        this.tileID = null;
    }

    public Hex(String tileID) {
        this.contentCount = 0;
        this.contentType = null;
        this.tileID = tileID;
    }

    public Hex(String tileID, Terrain terrain) {
        this.terrain = terrain;
        this.contentCount = 0;
        this.contentType = null;
        this.tileID = tileID;
    }

    public Hex(String tileID, Terrain terrain, int height) {
        this.terrain = terrain;
        this.tileID = tileID;
        this.height = height;
        this.contentCount = 0;
        this.contentType = null;
    }

    public Terrain getHexTerrain() {
        return terrain;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getID() {
        return this.tileID;
    }

    public void addPieceToTile() {

    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii=0;ii<list.size();ii++){
            Hex hex = list.get(ii);
            System.out.println(hex.terrain.getType()+":H="+Integer.toString(hex.getHeight())+":ID="+hex.getID());
        }
    }

//    public List getTileContents() {
//        return contents;
//    }

}

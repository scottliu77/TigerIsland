import java.util.*;

public class Hex {

    private Terrain terrain;
    private int tileID;
    private int height;
    private int contentCount;
    private String contentType;

    public Hex() {
        this.contentCount = 0;
        this.contentType = null;
    }

    public Hex(Terrain terrain) {
        this.terrain = terrain;
        this.contentCount = 0;
        this.contentType = null;
    }
    public Hex(Terrain terrain, int tileID, int height) {
        this.terrain = terrain;
        this.tileID = tileID;
        this.height = height;
        this.contentCount = 0;
        this.contentType = null;
    }

    public Terrain getHexTerrain() {
        return terrain;
    }
    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}
    public int getID() {return tileID;}

    public void addPieceToTile() {

    }

    public static void printHexes(ArrayList<Hex> list){
        for(int ii=0;ii<list.size();ii++){
            Hex hex = list.get(ii);
            System.out.println(hex.terrain.getType()+":H="+Integer.toString(hex.getHeight())+":ID="+Integer.toString(hex.getID()));
        }
    }

//    public List getTileContents() {
//        return contents;
//    }



}

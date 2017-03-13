import java.util.*;

public class Board{
    static int tileID;
    ArrayList<Hex> placedHexTiles;
    ArrayList<Location> placedHexLocations;

    ArrayList<Location> edgeSpaces;

    public Board(){
        placedHexTiles = new ArrayList<Hex>();
        placedHexLocations = new ArrayList<Location>();
        edgeSpaces = new ArrayList<Location>();
    }

    public void placeTile(Tile tile, Location centerLoc, int rotation){
        placeHex(tile.getVolcanoHex(), centerLoc);
        placeHex(tile.getRightHex(), Location.add(centerLoc,rotate(rotation)));
        placeHex(tile.getLeftHex(), Location.add(centerLoc,rotate(rotation+60)));
    }

    private Location rotate(int rotation){
        int xShift=0;
        int yShift=0;
        rotation %= 360;
        switch(rotation){
            case 0:
                xShift = 1;
                yShift = 0;
                break;
            case 60:
                xShift = 0;
                yShift = 1;
                break;
            case 120:
                xShift = -1;
                yShift = 1;
                break;
            case 180:
                xShift = -1;
                yShift = 0;
                break;
            case 240:
                xShift = 0;
                yShift = -1;
                break;
            case 300:
                xShift = 1;
                yShift = -1;
                break;
        }
        return new Location(xShift,yShift);
    }
    public int newHeight(Location loc){
        return hexAt(loc).getHeight()+1;
    }

    private void placeHex(Hex h, Location loc){
        insertHexIntoOrderedArrayList(h, loc);
        removeLocationFromOrderedSurroundingEdgeSpaces(loc);
        for(int i = 0; i<360; i+=60){
            Location newLoc = Location.add(loc,rotate(i));
            if(!hexExistsAtLocation(newLoc))
                insertLocationIntoOrderedSurroundingEdgeSpaces(newLoc);
        }
    }

    public boolean hexExistsAtLocation(Location loc){
        int bot = 0;
        int top = placedHexTiles.size()-1;
        while(top>=bot){
            int mid = (top-bot)/2 + bot;
            Location midLocation = placedHexLocations.get(mid);
            if(loc.greaterThan(midLocation))
                bot = mid + 1;
            else if(loc.lessThan(midLocation))
                top = mid - 1;
            else
                return true;
        }
        return false;
    }
    public Hex hexAt(Location loc){
        int bot = 0;
        int top = placedHexTiles.size()-1;
        while(top>=bot){
            int mid = (top-bot)/2 + bot;
            Location midLocation = placedHexLocations.get(mid);
            if(loc.greaterThan(midLocation))
                bot = mid + 1;
            else if(loc.lessThan(midLocation))
                top = mid - 1;
            else
                return placedHexTiles.get(mid);
        }
        return new Hex();
    }

    public void insertHexIntoOrderedArrayList(Hex h, Location loc){
        if(placedHexTiles.size()==0){
            placedHexTiles.add(h);
            placedHexLocations.add(loc);
        }
        else if(loc.lessThan(placedHexLocations.get(0))){
            placedHexTiles.add(0,h);
            placedHexLocations.add(0,loc);
        }
        else if(loc.greaterThan(placedHexLocations.get(placedHexTiles.size()-1))){
            placedHexTiles.add(h);
            placedHexLocations.add(loc);
        }
        else{
            int bot = 0;
            int top = placedHexTiles.size()-1;
            while(top>=bot){
                int mid = (top-bot)/2 + bot;
                Location midLocation = placedHexLocations.get(mid);
                Location midLocationP1 = placedHexLocations.get(mid+1);
                if(loc.greaterThan(midLocationP1))
                    bot = mid + 1;
                else if(loc.lessThan(midLocation))
                    top = mid - 1;
                else if(loc.equalTo(midLocation)){
                    placedHexTiles.set(mid,h);
                    placedHexLocations.set(mid,loc);
                    return;
                }
                else if(loc.equalTo(midLocationP1)){
                    placedHexTiles.set(mid+1,h);
                    placedHexLocations.set(mid+1,loc);
                    return;
                }
                else{
                    placedHexTiles.add(mid+1,h);
                    placedHexLocations.add(mid+1,loc);
                    return;
                }
            }
        }
    }

    public boolean isAnAvailableSpace(Location loc){
        int bot = 0;
        int top = edgeSpaces.size()-1;
        while(top>=bot){
            int mid = (top-bot)/2 + bot;
            Location midLocation = edgeSpaces.get(mid);
            if(loc.greaterThan(midLocation))
                bot = mid + 1;
            else if(loc.lessThan(midLocation))
                top = mid - 1;
            else
                return true;
        }
        return false;
    }

    public void removeLocationFromOrderedSurroundingEdgeSpaces(Location loc){
        int bot = 0;
        int top = edgeSpaces.size()-1;
        while(top>=bot){
            int mid = (top - bot)/2 + bot;
            Location midLocation = edgeSpaces.get(mid);
            if(loc.greaterThan(midLocation))
                bot = mid + 1;
            else if(loc.lessThan(midLocation))
                top = mid - 1;
            else if(loc.equalTo(midLocation)){
                edgeSpaces.remove(mid);
                return;
            }
        }
    }

    public void insertLocationIntoOrderedSurroundingEdgeSpaces(Location loc){
        if(edgeSpaces.size()==0)
            edgeSpaces.add(loc);
        else if(loc.lessThan(edgeSpaces.get(0)))
            edgeSpaces.add(0,loc);
        else if(loc.greaterThan(edgeSpaces.get(edgeSpaces.size()-1)))
            edgeSpaces.add(loc);
        else{
            int bot = 0;
            int top = edgeSpaces.size()-1;
            while(top>=bot){
                int mid = (top-bot)/2 + bot;
                Location midLocation = edgeSpaces.get(mid);
                Location midLocationP1 = edgeSpaces.get(mid+1);
                if(loc.greaterThan(midLocationP1)){
                    bot = mid + 1;
                }
                else if(loc.lessThan(midLocation)){
                    top = mid - 1;
                }
                else if(loc.equalTo(midLocation) || loc.equalTo(midLocationP1)){
                    return;
                }
                else{
                    edgeSpaces.add(mid+1,loc);
                    return;
                }
            }
        }
    }
    public void printDetails(){
        System.out.println("HexLocations:");
        Location.printLocations(placedHexLocations);
        System.out.println();

        System.out.println("HexDescription");
        Hex.printHexes(placedHexTiles);
        System.out.println();

        System.out.println("OpenEdges");
        Location.printLocations(edgeSpaces);
        System.out.println();
    }

    public void printMap(){
        String[][] theBoard = new String[21][21];
        for(int row = -10; row<=10; row++){
            for(int col = -10; col<=10; col++){
                if(row%2==0){
                    theBoard[col+10][row+10] = (col%2==0)?("----"):("    ");
                }
                else{
                    theBoard[col+10][row+10] = (col%2==0)?("    "):("----");
                }
            }
        }
        for(int ii=0; ii<placedHexTiles.size(); ii++){
            Location loc = placedHexLocations.get(ii);
            int newX = loc.x*2 + loc.y;
            int newY = loc.y;
            Hex hex = placedHexTiles.get(ii);
            theBoard[newX+10][10-newY] = '<'+ Character.toString(hex.getHexTerrain().getType().charAt(0))+hex.getID()+'>';
        }
        for(int ii=0; ii<edgeSpaces.size(); ii++){
            Location loc = edgeSpaces.get(ii);
            int newX = loc.x*2 + loc.y;
            int newY = loc.y;
            theBoard[newX+10][10-newY] = "****";
        }
        for(int row = 0; row<=20; row++){
            for(int col = 0; col<=20; col++){
                System.out.print(theBoard[col][row]);
            }
            System.out.println();
        }
    }
}
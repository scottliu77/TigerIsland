import java.util.*;

public class Board{
    static int tileID;
    protected ArrayList<Hex> placedHexTiles;
    protected ArrayList<Location> placedHexLocations;
    protected ArrayList<Location> edgeSpaces;

    public Board(){
        placedHexTiles = new ArrayList<Hex>();
        placedHexLocations = new ArrayList<Location>();
        edgeSpaces = new ArrayList<Location>();
    }

    public void placeTile(Tile tile, Location centerLoc, int rotation){
        placeHex(tile.getCenterHex(), centerLoc);
        placeHex(tile.getRightHex(), Location.rotateHexRight(centerLoc, rotation));
        placeHex(tile.getLeftHex(), Location.rotateHexRight(centerLoc, rotation + 60));
    }

    private void placeHex(Hex hex, Location loc){
        addHexToListOfPlacedHexes(hex, loc);
        updateListOfEdgeSpaces(loc);
    }

    private void addHexToListOfPlacedHexes(Hex hex, Location loc){

        if(placedHexTiles.size()==0){
            addHex(hex, loc, -1);
        }
        else if(loc.lessThan(placedHexLocations.get(0))){
            addHex(hex, loc, 0);
        }
        else if(loc.greaterThan(placedHexLocations.get(placedHexTiles.size()-1))){
            addHex(hex, loc, -1);
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
                    setHex(hex, loc, mid);
                    return;
                }
                else if(loc.equalTo(midLocationP1)){
                    setHex(hex,loc,mid+1);
                    return;
                }
                else{
                    addHex(hex,loc,mid+1);
                    return;
                }
            }
        }
    }

    private void setHex(Hex hex, Location loc, int mid) {
        placedHexTiles.set(mid,hex);
        placedHexLocations.set(mid,loc);
    }

    private void addHex(Hex hex, Location loc, int index) {
        if (index < 0) {
            placedHexTiles.add(hex);
            placedHexLocations.add(loc);
        }
        else {
            placedHexTiles.add(0,hex);
            placedHexLocations.add(0,loc);
        }
    }

    private void updateListOfEdgeSpaces(Location loc) {
        removeLocationFromOrderedSurroundingEdgeSpaces(loc);
        for(int rotation = 0; rotation < 360; rotation += 60){
            Location newLoc = Location.rotateHexRight(loc, rotation);
            if(!hexExistsAtLocation(newLoc))
                insertLocationIntoOrderedSurroundingEdgeSpaces(newLoc);
        }
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

    public int newHeight(Location loc){
        return hexAt(loc).getHeight()+1;
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
        while(top >= bot){
            int mid = (top - bot) / 2 + bot;
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

    public boolean isAnAvailableSpace(Location loc){
        int bot = 0;
        int top = edgeSpaces.size()-1;
        while(top >= bot){
            int mid = (top - bot)/2 + bot;
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
        for(int row = -10; row <= 10; row++){
            for(int col = -10; col <= 10; col++){
                if(row % 2 == 0){
                    theBoard[col + 10][row + 10] = (col % 2 == 0)?("----------"):("          ");
                }
                else{
                    theBoard[col + 10][row + 10] = (col % 2 == 0)?("          "):("----------");
                }
            }
        }
        for(int ii = 0; ii < placedHexTiles.size(); ii++){
            Location loc = placedHexLocations.get(ii);
            int newX = loc.x * 2 + loc.y;
            int newY = loc.y;
            Hex hex = placedHexTiles.get(ii);
            String hexTerrain = Character.toString(hex.getHexTerrain().getType().charAt(0));
            String hexHeight = Integer.toString(hex.getHeight());
            String hexContentType = Character.toString(hex.getContentType().charAt(0));
            String hexContentCount = Integer.toString(hex.getContentCount());
            String hexIDshort = hex.getIDfirstChars(2);
            theBoard[newX + 10][10 - newY] = '<' + hexTerrain + '-' + hexHeight + '-' + hexContentType + hexContentCount + '-' + hexIDshort + '>';
        }
        for(int ii = 0; ii < edgeSpaces.size(); ii++){
            Location loc = edgeSpaces.get(ii);
            int newX = loc.x * 2 + loc.y;
            int newY = loc.y;
            theBoard[newX + 10][10 - newY] = "**********";
        }
        for(int row = 0; row <= 20; row++){
            for(int col = 0; col <= 20; col++){
                System.out.print(theBoard[col][row]);
            }
            System.out.println();
        }
    }
}
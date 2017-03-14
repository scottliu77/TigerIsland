import java.util.*;

public class Board{

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
            placedHexTiles.add(hex);
            placedHexLocations.add(loc);
        }
        else if(loc.lessThan(placedHexLocations.get(0))){
            placedHexTiles.add(0,hex);
            placedHexLocations.add(0,loc);
        }
        else if(loc.greaterThan(placedHexLocations.get(placedHexTiles.size()-1))){
            placedHexTiles.add(hex);
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
                    placedHexTiles.set(mid,hex);
                    placedHexLocations.set(mid,loc);
                    return;
                }
                else if(loc.equalTo(midLocationP1)){
                    placedHexTiles.set(mid+1,hex);
                    placedHexLocations.set(mid+1,loc);
                    return;
                }
                else{
                    placedHexTiles.add(mid+1,hex);
                    placedHexLocations.add(mid+1,loc);
                    return;
                }
            }
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
}
import java.util.*;

public class Board{
    static final int SIZE_REQUIRED_FOR_TOTORO = 5;

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

    public boolean totoroPlacementPossible(Player player){
        for(int i=0;i<placedHexLocations.size();i++){
            Location currentLocation = placedHexLocations.get(i);
            if(settlementCouldAcceptTororo(currentLocation, player)){
                return true;
            }
        }
        return false;
    }

    private boolean settlementCouldAcceptTororo(Location loc, Player player){
        boolean expansionPossible = false;
        boolean settlementLargeEnoughForTotoro;
        Queue<Location> settlement = new LinkedList<Location>();
        settlement.add(loc);
        HashSet<Location> visitedLocations = new HashSet<Location>();
        while(!settlement.isEmpty()){
            Location currentLocation = settlement.remove();
            Hex currentHex = hexAt(currentLocation);
            int sizeOfSettlementThisLocationIsIn = 0;
            visitedLocations.add(currentLocation);
            ArrayList<Location> adjacentLocationsToTemp = currentLocation.getAdjacentLocations();
            for(Location location : adjacentLocationsToTemp){
                if(ownedBySamePlayer(currentHex, player) && !visitedLocations.contains(location)) {
                    sizeOfSettlementThisLocationIsIn++;
                    settlement.add(location);
                }
            }
            if(totoroAlreadyPresent(currentHex)){
                return false;
            }
            if(hexAvailableForSettlement(currentHex)) {
                expansionPossible = true;
            }
            if(settlementLargeEnoughForTotoro = true);
        }
        if(expansionPossible && settlement.size() >= 5){
            return true;
        }
        return false;
    }

    private boolean hexAvailableForSettlement(Hex currentHex) {
        return currentHex.getPieceCount() == 0;
    }

    private boolean totoroAlreadyPresent(Hex currentHex) {
        return currentHex.getPieceType().equals("Totoro");
    }

    private boolean ownedBySamePlayer(Hex hex, Player player){
        return player.getPlayerColor().equals(hex.getPieceColor());
    }
}
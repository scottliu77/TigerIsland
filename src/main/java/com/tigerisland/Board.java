package com.tigerisland;

import java.util.*;

public class Board{
    static final int SIZE_REQUIRED_FOR_TOTORO = 5;

    protected ArrayList<PlacedHex> placedHexes;
    protected ArrayList<Location> edgeSpaces;
    protected ArrayList<Settlement> settlements;

    public Board(){
        placedHexes = new ArrayList<PlacedHex>();
        edgeSpaces = new ArrayList<Location>();
        settlements = new ArrayList<Settlement>();
    }

    public Board(Board board){
        this.placedHexes = new ArrayList<PlacedHex>();
        for(PlacedHex hex : board.placedHexes) {
            this.placedHexes.add(new PlacedHex(hex));
        }
        this.edgeSpaces = new ArrayList<Location>();
        for(Location location : board.edgeSpaces) {
            this.edgeSpaces.add(new Location(location));
        }
        this.settlements = new ArrayList<Settlement>();
        for(Settlement settlement : board.settlements) {
            this.settlements.add(new Settlement(settlement));
        }
    }

    public void placeTile(Tile tile, Location centerLoc, int rotation) throws InvalidMoveException {
        placeHex(tile.getCenterHex(), centerLoc);
        placeHex(tile.getRightHex(), Location.rotateHexLeft(centerLoc, rotation));
        placeHex(tile.getLeftHex(), Location.rotateHexLeft(centerLoc, rotation + 60));
    }

    private void placeHex(Hex hex, Location loc) throws InvalidMoveException {
        addHexToListOfPlacedHexes(hex, loc);
        updateListOfEdgeSpaces(loc);
    }

    private void addHexToListOfPlacedHexes(Hex hex, Location loc){

        if(placedHexes.size()==0){
            addHex(hex, loc, -1);
        }
        else if(loc.lessThan(placedHexes.get(0).getLocation())){
            addHex(hex, loc, 0);
        }
        else if(loc.greaterThan(placedHexes.get(placedHexes.size()-1).getLocation())){
            addHex(hex, loc, -1);
        }
        else{
            int bot = 0;
            int top = placedHexes.size()-1;
            while(top>=bot){
                int mid = (top-bot)/2 + bot;
                Location midLocation = placedHexes.get(mid).getLocation();
                Location midLocationP1 = placedHexes.get(mid+1).getLocation();
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
        PlacedHex placedHex = new PlacedHex(hex, loc);
        placedHexes.set(mid, placedHex);
    }

    private void addHex(Hex hex, Location loc, int index) {
        PlacedHex placedHex = new PlacedHex(hex, loc);
        if (index < 0) {
            placedHexes.add(placedHex);
        }
        else {
            placedHexes.add(index,placedHex);
        }
    }

    private void updateListOfEdgeSpaces(Location loc) {
        removeLocationFromOrderedSurroundingEdgeSpaces(loc);
        for(int rotation = 0; rotation < 360; rotation += 60){
            Location newLoc = Location.rotateHexLeft(loc, rotation);
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
        int top = placedHexes.size()-1;
        while(top>=bot){
            int mid = (top-bot)/2 + bot;
            Location midLocation = placedHexes.get(mid).getLocation();
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
        int top = placedHexes.size()-1;
        while(top >= bot){
            int mid = (top - bot) / 2 + bot;
            Location midLocation = placedHexes.get(mid).getLocation();
            if(loc.greaterThan(midLocation))
                bot = mid + 1;
            else if(loc.lessThan(midLocation))
                top = mid - 1;
            else
                return placedHexes.get(mid).getHex();
        }
        return new Hex();
    }

    public void createVillage(Player player, Location location) throws InvalidMoveException {
        Hex targetHex = hexAt(location);
        if (targetHex.getPieceCount() == -1) {
            throw new InvalidMoveException("Target hex does not exist");
        } else if (targetHex.getHeight() != 1) {
            throw new InvalidMoveException("Cannot create village above height 1");
        } else if (!hexAvailableForSettlement(targetHex)) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        } else if (targetHex.getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        hexAt(location).addPiecesToHex(new Piece(player.getPlayerColor(), PieceType.VILLAGER), 1);
    }

    public int expandVillage(Player player, Location loc, Location settledLoc) throws InvalidMoveException {
        Settlement settlement = isValidSettlementCheck(settledLoc);
        Hex targetHex = setDesiredTerrain(loc);
        Terrain specifiedTerrain = targetHex.getHexTerrain();
        ArrayList<PlacedHex> hexesInSettlement = settlement.getHexesInSettlement();
        ArrayList<PlacedHex> adjacentHexes;
        int piecesNeeded = 0;

        for (PlacedHex placedHex : hexesInSettlement) {
            adjacentHexes = settlement.findAdjacentHexesFromPlacedHex(placedHex, hexesInSettlement);
            villageExpansionChecks(player, piecesNeeded, specifiedTerrain, settlement, adjacentHexes);
        }
        return piecesNeeded;
    }

    public Settlement isValidSettlementCheck(Location loc) throws InvalidMoveException {
        Hex potentialSettlementHex = hexAt(loc);
        if (potentialSettlementHex.getPieceCount() == -1){
            throw new InvalidMoveException("Settlement hex does not exist");
        }

        for (Settlement settlement : settlements) {
            for (PlacedHex placedHex : settlement.getHexesInSettlement()) {
                if (placedHex.getHex() == potentialSettlementHex) {
                    return settlement;
                }
            }
        }
        throw new InvalidMoveException("Hex does not belong to a settlement");
    }

    public Hex setDesiredTerrain(Location loc) throws InvalidMoveException {
        Hex targetHex = hexAt(loc);
        if(targetHex.getPieceCount() == -1){
            throw new InvalidMoveException("Target hex does not exist");
        } else{
            return targetHex;
        }
    }

    public int villageExpansionChecks(Player player, int piecesNeeded, Terrain specifiedTerrain,
                                      Settlement settlement, ArrayList<PlacedHex> adjacentHexes) throws InvalidMoveException {
        for (PlacedHex potentialHex : adjacentHexes) {
            int hexHeight = potentialHex.getHex().getHeight();
            if (potentialHex.getHex().getPieceCount() > 0){
                continue;
            } else if (potentialHex.getHex().getHexTerrain() != specifiedTerrain){
                continue;
            } else if (player.getPieceSet().getNumberOfVillagersRemaining() != hexHeight){
                throw new InvalidMoveException("Player does not have enough pieces to populate the target hex");
            }
            potentialHex.getHex().addPiecesToHex(player.getPieceSet().placeMultipleVillagers(hexHeight), hexHeight);
            updateSettlements();
            ArrayList<PlacedHex> hexesInSettlement = settlement.getHexesInSettlement();
            piecesNeeded += hexHeight;
            adjacentHexes = settlement.findAdjacentHexesFromPlacedHex(potentialHex, hexesInSettlement);
            villageExpansionChecks(player, piecesNeeded, specifiedTerrain, settlement, adjacentHexes);
        }
        return piecesNeeded;
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


    private boolean totoroAlreadyPresent(Hex currentHex) {
        return currentHex.getPieceType().equals("Totoro");
    }

    public boolean playerHasSettlementThatCouldAcceptTotoro(Player player){
        for(Settlement settlement : settlements) {
            if (settlementBelongsToCurrentPlayer(settlement, player) && settlement.size() >= SIZE_REQUIRED_FOR_TOTORO) {
                return settlement.isExpandable(placedHexes);
            }
        }
        return false;
    }

    private boolean settlementBelongsToCurrentPlayer(Settlement settlement, Player player){
        return settlement.color.equals(player.getPlayerColor());
    }

    private boolean hexAvailableForSettlement(Hex currentHex) {
        return currentHex.getPieceCount() == 0;
    }

    private boolean ownedBySamePlayer(Hex hex, Player player){
        return player.getPlayerColor().equals(hex.getPieceColor());
    }

    public ArrayList<Location> locationsOfPlacedHexes(){
        ArrayList<Location> locationsOfPlacedHexes = new ArrayList<Location>();
        for(PlacedHex placedHex : placedHexes){
            locationsOfPlacedHexes.add(placedHex.getLocation());
        }
        return  locationsOfPlacedHexes;
    }

    public void placeTotoro(Player player, Location location) throws InvalidMoveException{
        PlacedHex placedHex = placedHexAtLocation(location);
        if (placedHex == null) {
            throw new InvalidMoveException("Target hex does not exist");
        }
        if (placedHex.getHex().getPieceCount() > 0) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        }
        if (placedHex.getHex().getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        PlacedHex tempPlacedHex = new PlacedHex(placedHex);
        Player tempPlayer = new Player(player);
        tempPlacedHex.getHex().addPiecesToHex(tempPlayer.getPieceSet().placeVillager(), 1);
        placedHexes.add(tempPlacedHex);
        Settlement settlement = new Settlement(tempPlacedHex, placedHexes);

        if(settlement.containsTotoro()){
            throw new InvalidMoveException("Cannot place totoro in a settlement already containing a Totoro");
        }

        placedHexes.remove(tempPlacedHex);
        placedHex.getHex().addPiecesToHex(player.getPieceSet().placeTotoro(), 1);
        int minimumSizeRequireForTotoroAfterPlacement = SIZE_REQUIRED_FOR_TOTORO + 1;
        settlement = new Settlement(placedHex, placedHexes);

        if(settlement.size() < minimumSizeRequireForTotoroAfterPlacement) {
            throw new InvalidMoveException("Cannot place totoro in a settlement of size 4 or smaller!");
        }

    }

    public void placeTiger(Player player, Location location) throws InvalidMoveException{
        PlacedHex placedHex = placedHexAtLocation(location);
        if (placedHex == null) {
            throw new InvalidMoveException("Target hex does not exist");
        }
        if (placedHex.getHex().getPieceCount() > 0) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        }
        if (placedHex.getHex().getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        PlacedHex tempPlacedHex = new PlacedHex(placedHex);
        Player tempPlayer = new Player(player);
        tempPlacedHex.getHex().addPiecesToHex(tempPlayer.getPieceSet().placeVillager(), 1);
        placedHexes.add(tempPlacedHex);
        Settlement settlement = new Settlement(tempPlacedHex, placedHexes);

        if(settlement.containsTotoro()){
            throw new InvalidMoveException("Cannot place totoro in a settlement already containing a Totoro");
        }

        placedHexes.remove(tempPlacedHex);
        placedHex.getHex().addPiecesToHex(player.getPieceSet().placeTotoro(), 1);
        int minimumSizeRequireForTotoroAfterPlacement = SIZE_REQUIRED_FOR_TOTORO + 1;
        settlement = new Settlement(placedHex, placedHexes);

        if(settlement.size() < minimumSizeRequireForTotoroAfterPlacement) {
            throw new InvalidMoveException("Cannot place totoro in a settlement of size 4 or smaller!");
        }

    }


    public ArrayList<Hex> hexesOfPlacedHexes(){
        ArrayList<Hex> hexesOfPlacedHexes = new ArrayList<Hex>();
        for(PlacedHex placedHex : placedHexes){
            hexesOfPlacedHexes.add(placedHex.getHex());
        }
        return  hexesOfPlacedHexes;
    }

    public void updateSettlements(){
        settlements.clear();
        HashSet<PlacedHex> visitedHexes = new HashSet<PlacedHex>();
        for(PlacedHex hex : placedHexes){
            if (hex.getHex().getPieceCount() > 0) {
                if(visitedHexes.contains(hex)){
                    continue;
                }
                Settlement settlement = new Settlement(hex, placedHexes);
                settlements.add(settlement);
                addHexesOfSettlementToHashSet(settlement, visitedHexes);
            }
        }
    }

    private void addHexesOfSettlementToHashSet(Settlement settlement, HashSet<PlacedHex> visitedHexes){
        for(PlacedHex hex : settlement.getHexesInSettlement()){
            visitedHexes.add(hex);
        }
    }

    private PlacedHex placedHexAtLocation(Location location){
        for(PlacedHex placedHex : placedHexes){
            if(location.x == placedHex.getLocation().x && location.y == placedHex.getLocation().y){
                return placedHex;
            }
        }
        return null;
    }

}
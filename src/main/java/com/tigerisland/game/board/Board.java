package com.tigerisland.game.board;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.moves.TigerPlacer;
import com.tigerisland.game.moves.TotoroPlacer;
import com.tigerisland.game.moves.TilePlacement;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.Score;

import java.util.*;

public class Board{
    public static final int SIZE_REQUIRED_FOR_TOTORO = 5;
    public static final int HEIGHT_REQUIRED_FOR_TIGER = 3;

    protected ArrayList<PlacedHex> placedHexes;
    protected ArrayList<Location> edgeSpaces;
    protected ArrayList<Settlement> settlements;

    public Board() {
        placedHexes = new ArrayList<PlacedHex>();
        edgeSpaces = new ArrayList<Location>();
        settlements = new ArrayList<Settlement>();
        edgeSpaces.add(new Location(0, 0));
    }

    public void placeStartingTile() {
        try {
            String startID = "000000000000000000000000000000000000";
            placeHex(new Hex(startID, Terrain.VOLCANO), new Location(0, 0));
            placeHex(new Hex(startID, Terrain.JUNGLE), new Location(-1, 1));
            placeHex(new Hex(startID, Terrain.LAKE), new Location(0, 1));
            placeHex(new Hex(startID, Terrain.GRASS), new Location(1, -1));
            placeHex(new Hex(startID, Terrain.ROCK), new Location(0, -1));
        }
        catch (InvalidMoveException e){
            System.out.println("Starting Tile Cannot Be Placed");
        }
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
        if(isALegalTilePlacment(centerLoc, rotation)){
            placeHex(tile.getCenterHex(), centerLoc);
            placeHex(tile.getRightHex(), Location.rotateHexLeft(centerLoc, rotation));
            placeHex(tile.getLeftHex(), Location.rotateHexLeft(centerLoc, rotation + 60));
        }
        else {
            try{
                throwTilePlacementException(centerLoc, rotation);
            }
            catch(InvalidMoveException ex){
                throw ex;
            }
        }
        updateSettlements();
    }

    public void placeTile(TilePlacement tilePlacement) throws InvalidMoveException {
        Location centerLoc = tilePlacement.getLocation();
        int rotation = tilePlacement.getRotation();
        Tile tile = tilePlacement.getTile();
        if(isALegalTilePlacment(centerLoc, rotation)){
            placeHex(tile.getCenterHex(), centerLoc);
            placeHex(tile.getRightHex(), Location.rotateHexLeft(centerLoc, rotation));
            placeHex(tile.getLeftHex(), Location.rotateHexLeft(centerLoc, rotation + 60));
        }
        else {
            try{
                throwTilePlacementException(centerLoc, rotation);
            }
            catch(InvalidMoveException ex){
                throw ex;
            }
        }
        updateSettlements();
    }

    public boolean isALegalTilePlacment(Location centerLoc, int rotation) {
        if( (!tileIsPlacedProperlyAtHeight1(centerLoc, rotation) && !isPlacedProperlyOnExistingTiles(centerLoc, rotation))
                || totoroExistsUnderTile(centerLoc, rotation)
                || tigerExistsUnderTile(centerLoc, rotation)
                || completelyCoversSettlement(centerLoc, rotation)  )
            return false;
        else
            return true;
    }

    private void throwTilePlacementException(Location centerLoc, int rotation) throws InvalidMoveException {
        if(!tileIsPlacedProperlyAtHeight1(centerLoc, rotation) && !isPlacedProperlyOnExistingTiles(centerLoc, rotation))
            throw new InvalidMoveException("Illegal Placement Location");
        else if(totoroExistsUnderTile(centerLoc, rotation))
            throw new InvalidMoveException("Totoro exists under tile");
        else if(tigerExistsUnderTile(centerLoc, rotation))
            throw new InvalidMoveException("Tiger exists under tile");
        else if(completelyCoversSettlement(centerLoc, rotation))
            throw new InvalidMoveException("Whole settlement exists under tile");
    }

    private boolean tileIsPlacedProperlyAtHeight1(Location centerLoc, int rotation){
        boolean areAllUnoccupiedLocations =
            !hexExistsAtLocation(centerLoc) &&
            !hexExistsAtLocation(Location.rotateHexLeft(centerLoc, rotation)) &&
            !hexExistsAtLocation(Location.rotateHexLeft(centerLoc, rotation +60));
        boolean isOnTheEdge =
            isAnAvailableEdgeSpace(centerLoc) ||
            isAnAvailableEdgeSpace(Location.rotateHexLeft(centerLoc, rotation)) ||
            isAnAvailableEdgeSpace(Location.rotateHexLeft(centerLoc, rotation +60));
        return (areAllUnoccupiedLocations && isOnTheEdge);
    }

    private boolean isPlacedProperlyOnExistingTiles(Location centerLoc, int rotation){
        Location loc1 = Location.rotateHexLeft(centerLoc, rotation);
        Location loc2 = Location.rotateHexLeft(centerLoc, rotation +60);
        boolean areAllOccupiedLocations =
            hexExistsAtLocation(centerLoc) &&
            hexExistsAtLocation(loc1) &&
            hexExistsAtLocation(loc2);
        boolean areAllTheSameHeight =
            (hexAt(centerLoc).getHeight() == hexAt(loc1).getHeight()) &&
            (hexAt(centerLoc).getHeight() == hexAt(loc2).getHeight());
        boolean isOn2OrMoreTiles =
            !(hexAt(centerLoc).getTileID().equals(hexAt(loc1).getTileID())) ||
            !(hexAt(centerLoc).getTileID().equals(hexAt(loc2).getTileID())) ||
            !(hexAt(loc1).getTileID().equals(hexAt(loc2).getTileID()));
        boolean volcanosOverlap =
            (hexExistsAtLocation(centerLoc))?(hexAt(centerLoc).getHexTerrain().getTerrainString().equals("Volcano")):(false);
        return (areAllOccupiedLocations && areAllTheSameHeight && isOn2OrMoreTiles && volcanosOverlap);
    }

    private boolean totoroExistsUnderTile(Location centerLoc, int rotation){
        return
            totoroAlreadyPresent(hexAt(centerLoc)) ||
            totoroAlreadyPresent(hexAt(Location.rotateHexLeft(centerLoc, rotation))) ||
            totoroAlreadyPresent(hexAt(Location.rotateHexLeft(centerLoc, rotation +60)));
    }

    private boolean totoroAlreadyPresent(Hex currentHex) {
        return currentHex.getPieceType().equals("Totoro");
    }

    private boolean tigerExistsUnderTile(Location centerLoc, int rotation){
        return
            tigerAlreadyPresent(hexAt(centerLoc)) ||
            tigerAlreadyPresent(hexAt(Location.rotateHexLeft(centerLoc, rotation))) ||
            tigerAlreadyPresent(hexAt(Location.rotateHexLeft(centerLoc, rotation + 60)));
    }

    private boolean tigerAlreadyPresent(Hex currentHex) {
        return currentHex.getPieceType().equals("Tiger");
    }

    private boolean completelyCoversSettlement(Location centerLoc, int rotation){
        for(int ii=0; ii<settlements.size(); ii++){
            Settlement currentSettlment = settlements.get(ii);
            if(currentSettlment.hexesInSettlement.size()<=3)
                if(currentSettlment.isConfinedUnderTile(centerLoc, rotation))
                    return true;
        }
        return false;
    }

    protected void placeHex(Hex hex, Location loc) throws InvalidMoveException {
        if(hexExistsAtLocation(loc)) {
            PlacedHex replacedHex = placedHexAtLocation(loc);
            int newHeight = hexAt(loc).getHeight() + 1;
            hex.setHeight(newHeight);
            placedHexes.set(placedHexes.indexOf(replacedHex), new PlacedHex(hex, loc));
        } else {
            hex.setHeight(1);
            addHexToListOfPlacedHexes(hex, loc);
            updateListOfEdgeSpaces(loc);
        }
        updateListOfEdgeSpaces(loc);
    }

    private void addHexToListOfPlacedHexes(Hex hex, Location loc){
        if(placedHexes.size()==0)
            addHex(hex, loc, -1);
        else if(loc.lessThan(placedHexes.get(0).getLocation()))
            addHex(hex, loc, 0);
        else if(loc.greaterThan(placedHexes.get(placedHexes.size()-1).getLocation()))
            addHex(hex, loc, -1);
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
        if (index < 0)
            placedHexes.add(placedHex);
        else
            placedHexes.add(index,placedHex);
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
        } else if (targetHex.getPieceCount() > 0) {
            throw new InvalidMoveException("Target hex already contains piece(s)");
        } else if (targetHex.getHexTerrain() == Terrain.VOLCANO) {
            throw new InvalidMoveException("Cannot place a piece on a volcano hex");
        }
        hexAt(location).addPiecesToHex(player.getPieceSet().placeVillager(), 1);
    }

    public void expandVillage(Player player, Location settledLoc, Terrain expandTerrain) throws InvalidMoveException {
        Settlement settlement = isSettledLocationValid(player, settledLoc);
        Terrain specifiedTerrain = expandTerrain;
        checkForVolcano(expandTerrain);
        ArrayList<PlacedHex> allExpandableHexes = new ArrayList<PlacedHex>();
        allExpandableHexes = getAllExpandableAdjacentHexesToSettlement(settlement, specifiedTerrain);

        while (!allExpandableHexes.isEmpty()) {
            villageExpansionChecks(player, settlement, allExpandableHexes);
            allExpandableHexes = getAllExpandableAdjacentHexesToSettlement(settlement, specifiedTerrain);
        }
    }

    public Settlement isSettledLocationValid(Player player, Location settledLoc) throws InvalidMoveException {
        Hex potentialSettlementHex = hexAt(settledLoc);
        if (potentialSettlementHex.getPieceCount() == -1) {
            throw new InvalidMoveException("This hex does not exist");
        } else if (potentialSettlementHex.getPieceCount() == 0){
            throw new InvalidMoveException("This hex does not belong in a settlement");
        } else if (potentialSettlementHex.getPieceCount() > 0) {
            if (potentialSettlementHex.getPieceColor() != player.getPlayerColor()) {
                throw new InvalidMoveException("Settlement hex does not belong to the current player");
            }
        }

        for (Settlement settlement : settlements) {
            for (PlacedHex placedHex : settlement.getHexesInSettlement()) {
                if ((placedHex.getLocation().x == settledLoc.x) &&(placedHex.getLocation().y == settledLoc.y)) {
                    return settlement;
                }
            }
        }
        return null;
    }

    public void checkForVolcano(Terrain expandTerrain) throws InvalidMoveException {
        if (expandTerrain != Terrain.VOLCANO) {
            return;
        } else{
            throw new InvalidMoveException("Cannot expand into a Volcano");
        }
    }

    public ArrayList<PlacedHex> getAllExpandableAdjacentHexesToSettlement(Settlement settlement, Terrain specifiedTerrain) {
        ArrayList<PlacedHex> allExpandableHexes = new ArrayList<PlacedHex>();
        ArrayList<PlacedHex> hexesInSettlement = settlement.getHexesInSettlement();
        ArrayList<PlacedHex> adjacentHexesToSettledHex;
        for (PlacedHex settledHex : hexesInSettlement) {
            adjacentHexesToSettledHex = settlement.findAdjacentHexesFromPlacedHex(settledHex, placedHexes);
            for (PlacedHex adjacentHex : adjacentHexesToSettledHex) {
                if (adjacentHex.isEmpty() && adjacentHex.getHex().getHexTerrain() == specifiedTerrain) {
                    if (!adjacentHex.getExpansionStatus()) {
                        adjacentHex.setExpansionStatus(true);
                        allExpandableHexes.add(adjacentHex);
                    }

                }
            }
        }
        if (allExpandableHexes.isEmpty()) {
            return new ArrayList<PlacedHex>();
        } else {
            return allExpandableHexes;
        }
    }

    public void villageExpansionChecks(Player player, Settlement settlement,
                                       ArrayList<PlacedHex> allExpandableHexes) throws InvalidMoveException {
        ArrayList<PlacedHex> hexesInSettlement = settlement.getHexesInSettlement();

        for (PlacedHex potentialHex : allExpandableHexes) {
            potentialHex.setExpansionStatus(false);
            int hexHeight = potentialHex.getHex().getHeight();
            if (player.getPieceSet().getNumberOfVillagersRemaining() - hexHeight < 0){
                throw new InvalidMoveException("Player does not have enough pieces to populate the target hex");
            } else {
                potentialHex.getHex().addPiecesToHex(player.getPieceSet().placeMultipleVillagers(hexHeight), hexHeight);
                player.getScore().addPoints(Score.VILLAGER_POINT_VALUE * hexHeight);
                hexesInSettlement.add(potentialHex);
            }
        }

    }

    public boolean isAnAvailableEdgeSpace(Location loc){
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

    public ArrayList<Settlement> settlementsThatCouldAcceptTotoroForGivenPlayer(Color color){
        ArrayList<Settlement> settlementsThatCouldAcceptTotoro = new ArrayList<Settlement>();
        for(Settlement settlement : settlements) {
            if (settlement.getColor() == color && settlement.size() >= SIZE_REQUIRED_FOR_TOTORO && settlement.containsTotoro() == false && settlement.isExpandable(placedHexes)) {
                settlementsThatCouldAcceptTotoro.add(settlement);
            }
        }
        return settlementsThatCouldAcceptTotoro;
    }

    public ArrayList<Settlement> settlementsThatCouldAcceptTigerForGivenPlayer(Color color) {
        ArrayList<Settlement> settlementsThatCouldAcceptTiger = new ArrayList<Settlement>();
        for(Settlement settlement : settlements) {
            if (settlement.getColor() == color && settlement.containsTiger() == false) {
                if (settlementNextToTigerReadyHex(settlement, HEIGHT_REQUIRED_FOR_TIGER)) {
                    settlementsThatCouldAcceptTiger.add(settlement);
                }
            }
        }
        return settlementsThatCouldAcceptTiger;
    }

    public boolean settlementNextToTigerReadyHex(Settlement settlement, int heightRequired) {
        for(PlacedHex hex : settlement.getHexesInSettlement()) {
            for(PlacedHex adjacentHex : settlement.findAdjacentHexesFromPlacedHex(hex, placedHexes )) {
                if (adjacentHex.getHex().getHeight() >= heightRequired && adjacentHex.getHex().getPieceCount() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void placeTotoro(Player player, Location location) throws InvalidMoveException{

        PlacedHex targetHex = placedHexAtLocation(location);
        ArrayList<Settlement> adjacentSettlementsToTargetLocation = findAdjacentSettlementsToLocation(location);
        TotoroPlacer.placeTotoro(player, targetHex, adjacentSettlementsToTargetLocation);
    }

    public void placeTiger(Player player, Location location) throws InvalidMoveException{
        PlacedHex targetHex = placedHexAtLocation(location);
        ArrayList<Settlement> adjacentSettlementsToTargetLocation = findAdjacentSettlementsToLocation(location);
        TigerPlacer.placeTiger(player, targetHex, adjacentSettlementsToTargetLocation);
       // player.getScore().addPoints(Score.TIGER_POINT_VALUE);
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

    public ArrayList<Settlement> findAdjacentSettlementsToLocation(Location targetLoc){
        ArrayList<Settlement> adjacentSettlements = new ArrayList<Settlement>();
        ArrayList<PlacedHex> adjacentHexesToLocation = findAdjacentHexesFromLocation(targetLoc);
        HashSet<PlacedHex> visitedHexes = new HashSet<PlacedHex>();
        for(PlacedHex adjacentHex : adjacentHexesToLocation){
            if(adjacentHex.isEmpty()){
                continue;
            }
            Settlement settlement = new Settlement(adjacentHex, placedHexes);
            if(visitedHexes.contains(settlement.getHexesInSettlement().get(0))){
                continue;
            }
            for(PlacedHex hexInSettlement : settlement.getHexesInSettlement()){
                visitedHexes.add(hexInSettlement);
            }
            adjacentSettlements.add(settlement);
        }
        return adjacentSettlements;
    }

    protected ArrayList<PlacedHex> findAdjacentHexesFromLocation(Location loc) {
        ArrayList<PlacedHex> adjacentHexes = new ArrayList<PlacedHex>();
        for(PlacedHex hex : placedHexes) {
            if(loc.isAdjacentTo(hex.getLocation())){
                adjacentHexes.add(hex);
            }
        }
        return  adjacentHexes;
    }

    public ArrayList<PlacedHex> getPlacedHexes(){
        return placedHexes;
    }

    public ArrayList<Location> getEdgeSpaces(){
        return edgeSpaces;
    }

    public ArrayList<Settlement> getSettlements(){
        return settlements;
    }

    public void setPlacedHexes(ArrayList<PlacedHex> placedHexes) {
        this.placedHexes = placedHexes;
    }
}
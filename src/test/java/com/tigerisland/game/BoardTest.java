package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest{

    private Board board;
    private Tile tile;
    private Location location;
    private ArrayList<PlacedHex> placedHexes;

    @Before
    public void createBoard() {
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        this.location = new Location(0,0);
        this.placedHexes = new ArrayList<PlacedHex>();
    }

    @Test
    public void testCanCreateBoard() {
        assertTrue(board != null);
    }

    @Test
    public void testCreatedBoardHasPlacedHexes() {
        assertTrue(board.placedHexes != null);
    }

    @Test
    public void testCreatedBoardHasEdgeSpaces() {
        assertTrue(board.edgeSpaces != null);
    }

    @Test
    public void testLocationUnavailable() throws InvalidMoveException {
        Tile tile = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Location location2 = new Location(0,0);
        assertFalse(board.isAnAvailableEdgeSpace(location2));
    }

    @Test
    public void testHexAtLocation() throws InvalidMoveException {
        Tile tile = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Tile tile2 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Location location2 = new Location(2,1);
        assertFalse(board.hexExistsAtLocation(location2));
    }

    @Test
    public void testCanPlaceTile() throws InvalidMoveException{
        board.placeTile(tile, location, 0);
    }

    @Test
    public void testUpdateSettlements(){
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        Hex hex2 = new Hex("hex2", Terrain.LAKE);
        Hex hex3 = new Hex("hex3", Terrain.LAKE);
        Hex hex4 = new Hex("hex4", Terrain.LAKE);

        hex1.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex2.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex3.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex4.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        Location loc1 = new Location(0,0);
        Location loc2 = new Location(0,-1);
        Location loc3 = new Location(0,1);
        Location loc4 = new Location(1,0);

        PlacedHex placedHex1 = new PlacedHex(hex1, loc1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);
        PlacedHex placedHex4 = new PlacedHex(hex4, loc4);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        placedHexes.add(placedHex4);

        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        assertTrue(settlement.size() == 4 && !settlement.containsTotoro());

        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.LAKE);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex6.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);

        Location loc5 = new Location(0,2);
        Location loc6 = new Location(2,0);

        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        placedHexes.add(placedHex5);
        placedHexes.add(placedHex6);

        board.updateSettlements();

        assertTrue(board.settlements.get(0).containsTotoro());

        assertTrue(board.settlements.get(0).getHexesInSettlement().size() == 6);

    }

    @Test
    public void testPlayerHasSettlementThatCouldAcceptTotoro(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);


        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        Hex hex2 = new Hex("hex2", Terrain.LAKE);
        Hex hex3 = new Hex("hex3", Terrain.LAKE);
        Hex hex4 = new Hex("hex4", Terrain.LAKE);

        hex1.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex2.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex3.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex4.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        Location loc1 = new Location(0,0);
        Location loc2 = new Location(0,-1);
        Location loc3 = new Location(0,1);
        Location loc4 = new Location(1,0);

        PlacedHex placedHex1 = new PlacedHex(hex1, loc1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);
        PlacedHex placedHex4 = new PlacedHex(hex4, loc4);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        placedHexes.add(placedHex4);

        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        assertFalse(board.settlementsThatCouldAcceptTotoroForGivenPlayer(player.getPlayerColor()).size() > 0);

        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.LAKE);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);


        Location loc5 = new Location(0,2);
        Location loc6 = new Location(2,0);

        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        placedHexes.add(placedHex5);
        placedHexes.add(placedHex6);

        board.updateSettlements();


        assertTrue(board.settlementsThatCouldAcceptTotoroForGivenPlayer(player.getPlayerColor()).size() > 0);

        placedHexes.remove(placedHex6);
        hex6 = new Hex("hex6", Terrain.VOLCANO);
        placedHex6 = new PlacedHex(hex6, loc6);
        placedHexes.add(placedHex6);


        assertFalse(board.settlementsThatCouldAcceptTotoroForGivenPlayer(player.getPlayerColor()).size() > 0);


    }




    private PlacedHex setUpSettlement(){
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        Hex hex2 = new Hex("hex2", Terrain.LAKE);
        Hex hex3 = new Hex("hex3", Terrain.LAKE);
        Hex hex4 = new Hex("hex4", Terrain.LAKE);

        hex1.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex2.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex3.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hex4.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        Location loc1 = new Location(0,0);
        Location loc2 = new Location(0,-1);
        Location loc3 = new Location(0,1);
        Location loc4 = new Location(1,0);

        PlacedHex placedHex1 = new PlacedHex(hex1, loc1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);
        PlacedHex placedHex4 = new PlacedHex(hex4, loc4);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        placedHexes.add(placedHex4);

        return placedHex1;
    }

    @Test
    public void testCanMakeSaveBoardCopyCheckPlacedHexes() throws InvalidMoveException {
        Board boardCopy = new Board(board);
        Tile tile = new Tile(Terrain.LAKE, Terrain.GRASS);
        Location location = new Location(0, 0);
        boardCopy.placeTile(tile, location, 0);

        assertTrue(boardCopy.hexExistsAtLocation(location) == true && board.hexExistsAtLocation(location) == false);
    }

    @Test
    public void testCanMakeSaveBoardCopyCheckSettlements() throws InvalidMoveException {
        Board boardCopy = new Board(board);

        Tile tile = new Tile(Terrain.LAKE, Terrain.GRASS);
        Location location = new Location(0, 0);
        boardCopy.placeTile(tile, location, 0);

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        Location villageLocation = new Location(1, 0);
        boardCopy.createVillage(player, villageLocation);

        boardCopy.updateSettlements();

        assertTrue(boardCopy.settlements.size() == 1 && board.settlements.size() == 0);
    }

    @Test
    public void testCanMakeSaveBoardCopyCheckEdgesSpaces() throws InvalidMoveException {
        Board boardCopy = new Board(board);
        Tile tile = new Tile(Terrain.LAKE, Terrain.GRASS);
        Location location = new Location(0, 0);
        boardCopy.placeTile(tile, location, 0);

        assertTrue(boardCopy.edgeSpaces.size() != board.edgeSpaces.size());
    }

    @Test
    public void testCanSaveOriginalBoardWithCopy() throws InvalidMoveException {
        Board boardCopy = new Board(board);
        Tile tile = new Tile(Terrain.LAKE, Terrain.GRASS);
        Location location = new Location(0, 0);
        boardCopy.placeTile(tile, location, 0);

        board = boardCopy;

        assertTrue(board.hexExistsAtLocation(location) == true);
    }

    @Test
    public void testCanPlaceTigerOnValidHex(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();

        Hex targetHex = new Hex("hex6", Terrain.JUNGLE,3);
        Location targetLocation = new Location(0,-2);
        PlacedHex targetPlacedHex = new PlacedHex(targetHex, targetLocation);
        placedHexes.add(targetPlacedHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        int originalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertFalse(e.getMessage() == "");
        }

        int finalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        assertTrue(finalNumberOfTigersRemaining == originalNumberOfTigersRemaining - 1);
    }

    @Test
    public void testCannotPlaceTigerOnNonexistentHex(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();

        Location targetLocation = new Location(0,-2);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        int originalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Target hex does not exist"));
        }

        int finalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        assertFalse(finalNumberOfTigersRemaining == originalNumberOfTigersRemaining - 1);
    }

    @Test
    public void testCannotPlaceTigerOnVolcano(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();

        Hex targetHex = new Hex("hex5", Terrain.VOLCANO, 3);
        Location targetLocation = new Location(0,-2);
        PlacedHex targetPlacedHex = new PlacedHex(targetHex, targetLocation);
        placedHexes.add(targetPlacedHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        int originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int originalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Cannot place a piece on a volcano hex"));
        }

        int finalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int finalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        assertFalse(finalNumberOfTigersRemaining == originalNumberOfTigersRemaining - 1);
        assertTrue(finalNumberOfVillagersRemaining == originalNumberOfVillagersRemaining);
    }

    @Test
    public void testCannotPlaceTigerOnNonemptyHex(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();

        Hex targetHex = new Hex("hex5", Terrain.JUNGLE, 3);
        targetHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 3);
        Location targetLocation = new Location(0,-2);
        PlacedHex targetPlacedHex = new PlacedHex(targetHex, targetLocation);
        placedHexes.add(targetPlacedHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        int originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int originalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Target hex already contains piece(s)"));
        }

        int finalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int finalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        assertFalse(finalNumberOfTigersRemaining == originalNumberOfTigersRemaining - 1);
        assertTrue(finalNumberOfVillagersRemaining == originalNumberOfVillagersRemaining);
    }

    @Test
    public void testCannotPlaceTigerOnHexOfHeightLessThanThree(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();

        Hex targetHex = new Hex("hex5", Terrain.JUNGLE, 2);
        Location targetLocation = new Location(0,-2);
        PlacedHex targetPlacedHex = new PlacedHex(targetHex, targetLocation);
        placedHexes.add(targetPlacedHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        int originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int originalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Cannot build Tiger playground on hex of level less than 3"));
        }

        int finalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int finalNumberOfTigersRemaining = player.getPieceSet().getNumberOfTigersRemaining();

        assertFalse(finalNumberOfTigersRemaining == originalNumberOfTigersRemaining - 1);
        assertTrue(finalNumberOfVillagersRemaining == originalNumberOfVillagersRemaining);
    }

    @Test
    public void testCannotPlaceTigerOnSettlementSizeZero(){

        Player player = new Player(Color.BLACK,"1", PlayerType.SAFEAI);

        Hex targetHex = new Hex("hex1", Terrain.JUNGLE, 3);
        Location targetLocation = new Location(0,-2);
        PlacedHex targetPlacedHex = new PlacedHex(targetHex, targetLocation);
        placedHexes.add(targetPlacedHex);

        board.placedHexes = this.placedHexes;

        try {
            board.placeTiger(player, targetLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Settlement already contains a tiger or is too small"));
        }
    }

    @Test
    public void testCannotPlaceTigerOnSettlementWithTiger(){

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE,3);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);
        Location loc5 = new Location(0,-2);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex5", Terrain.GRASS,4);
        Location loc6 = new Location(0,-3);
        PlacedHex emptyPlacedHexToTryToPlaceTigerOn = new PlacedHex(hex6, loc6);
        placedHexes.add(emptyPlacedHexToTryToPlaceTigerOn);
        Settlement settlement = new Settlement(placedHex1, placedHexes);


        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.placeTiger(player, emptyPlacedHexToTryToPlaceTigerOn.getLocation()); //this says totoro. Should it say tiger?
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Settlement already contains a tiger or is too small"));
        }
    }

    @Test
    public void testSettlementLocationIsNotValid() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        Location invalidSettlementLocation = new Location(0,1);

        try {
            board.isSettledLocationValid(player, invalidSettlementLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("This hex does not exist"));
        }
    }

    @Test
    public void testLocationDoesNotBelongInASettlement() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex emptyHex = new Hex(tileID, Terrain.GRASS);
        PlacedHex emptyPlacedHex = new PlacedHex(emptyHex, loc);
        placedHexes.add(emptyPlacedHex);
        board.placedHexes = this.placedHexes;

        try {
            board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("This hex does not belong in a settlement"));
        }
    }

    @Test
    public void testSettlementLocationDoesNotBelongToPlayer() {

        Player player1 = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex blackHex = new Hex(tileID, Terrain.GRASS);
        blackHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackPlacedHex = new PlacedHex(blackHex, loc);
        placedHexes.add(blackPlacedHex);
        board.placedHexes = this.placedHexes;

        Player player2 = new Player(Color.WHITE, "1", PlayerType.SAFEAI);

        try {
            board.isSettledLocationValid(player2, loc);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Settlement hex does not belong to the current player"));
        }
    }

    @Test
    public void testIsValidSettlementLocation() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);

        try {
            settlement = board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            assertTrue(settlement != null);
        }
    }
    @Test
    public void testSettlementLocationIsNotValidForExpansion() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        Location invalidSettlementLocation = new Location(0,1);

        try {
            board.isSettledLocationValid(player, invalidSettlementLocation);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("This hex does not exist"));
        }
    }

    @Test
    public void testLocationDoesNotBelongToASettlement() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex emptyHex = new Hex(tileID, Terrain.GRASS);
        PlacedHex emptyPlacedHex = new PlacedHex(emptyHex, loc);
        placedHexes.add(emptyPlacedHex);
        board.placedHexes = this.placedHexes;

        try {
            board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("This hex does not belong in a settlement"));
        }
    }

    @Test
    public void testSettledLocationDoesNotBelongToPlayer() {
        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex blackHex = new Hex(tileID, Terrain.GRASS);
        blackHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackPlacedHex = new PlacedHex(blackHex, loc);
        placedHexes.add(blackPlacedHex);
        board.placedHexes = this.placedHexes;

        Player player2 = new Player(Color.WHITE, "1", PlayerType.SAFEAI);

        try {
            board.isSettledLocationValid(player2, loc);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Settlement hex does not belong to the current player"));
        }
    }

    @Test
    public void testIsValidSettlementLocationExpansion() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,1);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            assertFalse(settlement.size() != 1);
        }

    }

    @Test
    public void testForVolcanoTargetExpansion() {
        try {
            board.checkForVolcano(Terrain.VOLCANO);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Cannot expand into a Volcano"));
        }
    }

    @Test
    public void testNoAdjacentHexesExistForSize1() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }


        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }
    }

    @Test
    public void testNoValidExpandableHexesExistForSize1() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(0,1);
        Location newLoc3 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.VOLCANO);
        Hex newHex3 = new Hex("fakeID3", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);
        PlacedHex placedHex3 = new PlacedHex(newHex3, newLoc3);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        board.placedHexes = this.placedHexes;


        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.LAKE);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }
    }

    @Test
    public void testNoValidExpandableHexesExistForSize2() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        String tileID2 = "fakeTileID2";
        Location loc2 = new Location(0,1);
        Hex secondSettledHex= new Hex(tileID2, Terrain.GRASS);
        secondSettledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        PlacedHex blackSettledHex2 = new PlacedHex(secondSettledHex, loc2);

        placedHexes.add(blackSettledHex);
        placedHexes.add(blackSettledHex2);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.VOLCANO);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.LAKE);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }
    }

    @Test
    public void testExpandableHexesExistForSize1() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.LAKE);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(0,1);
        Location newLoc3 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);
        Hex newHex3 = new Hex("fakeID3", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);
        PlacedHex placedHex3 = new PlacedHex(newHex3, newLoc3);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }
    }

    @Test
    public void testExpandableHexesExistForSize2() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.GRASS);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        String tileID2 = "fakeTileID2";
        Location loc2 = new Location(0,1);
        Hex secondSettledHex= new Hex(tileID2, Terrain.GRASS);
        secondSettledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        PlacedHex blackSettledHex2 = new PlacedHex(secondSettledHex, loc2);

        placedHexes.add(blackSettledHex);
        placedHexes.add(blackSettledHex2);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }
    }

    @Test
    public void testCannotPopulateAdjacentHexesSize1() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.GRASS);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);

        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        try {
            player.getPieceSet().placeMultipleVillagers(19);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes.isEmpty()) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }

        try {
            board.villageExpansionChecks(player, settlement, allExpandableHexes);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals("Player does not have enough pieces to populate the target hex"));
        }
    }

    @Test
    public void testCanMakeExpansionMoveSize1() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.GRASS);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);

        placedHexes.add(blackSettledHex);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes == null) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }

        try {
            board.villageExpansionChecks(player, settlement, allExpandableHexes);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals("Player does not have enough pieces to populate the target hex"));
        }
    }

    @Test
    public void testCannotPopulateAdjacentHexesSize2() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.GRASS);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        String tileID2 = "fakeTileID2";
        Location loc2 = new Location(0,1);
        Hex secondSettledHex= new Hex(tileID2, Terrain.GRASS);
        secondSettledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        PlacedHex blackSettledHex2 = new PlacedHex(secondSettledHex, loc2);

        placedHexes.add(blackSettledHex);
        placedHexes.add(blackSettledHex2);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        try {
            player.getPieceSet().placeMultipleVillagers(19);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes == null) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }

        try {
            board.villageExpansionChecks(player, settlement, allExpandableHexes);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals("Player does not have enough pieces to populate the target hex"));
        }
    }

    @Test
    public void testCanMakeExpansionMoveSize2() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        String tileID = "fakeTileID";
        Location loc = new Location(0,0);
        Hex settledHex = new Hex(tileID, Terrain.GRASS);
        settledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        String tileID2 = "fakeTileID2";
        Location loc2 = new Location(0,1);
        Hex secondSettledHex= new Hex(tileID2, Terrain.GRASS);
        secondSettledHex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        PlacedHex blackSettledHex = new PlacedHex(settledHex, loc);
        PlacedHex blackSettledHex2 = new PlacedHex(secondSettledHex, loc2);

        placedHexes.add(blackSettledHex);
        placedHexes.add(blackSettledHex2);
        board.placedHexes = this.placedHexes;
        Settlement settlement = new Settlement(blackSettledHex, placedHexes);
        board.settlements.add(settlement);

        try {
            settlement = board.isSettledLocationValid(player, loc);
            System.out.println("The settlement exists with a size of: " + settlement.size());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Location newLoc1 = new Location(0,-1);
        Location newLoc2 = new Location(1,0);

        Hex newHex1 = new Hex("fakeID1", Terrain.ROCK);
        Hex newHex2 = new Hex("fakeID2", Terrain.ROCK);

        PlacedHex placedHex1 = new PlacedHex(newHex1, newLoc1);
        PlacedHex placedHex2 = new PlacedHex(newHex2, newLoc2);

        placedHexes.add(placedHex1);
        placedHexes.add(placedHex2);
        board.placedHexes = this.placedHexes;

        ArrayList<PlacedHex> allExpandableHexes = board.getAllExpandableAdjacentHexesToSettlement(settlement, Terrain.ROCK);
        if (allExpandableHexes == null) {
            System.out.println("There are no expandable hexes");
        } else {
            System.out.println("The number of expandable hexes are: " + allExpandableHexes.size());
        }

        try {
            board.villageExpansionChecks(player, settlement, allExpandableHexes);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals("Player does not have enough pieces to populate the target hex"));
        }
    }

    @Test
    public void testCanExpandVillage() throws InvalidMoveException{

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);


        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.VOLCANO);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        Location loc2 = new Location(-1,2);
        Hex hex2 = new Hex("TileID1", Terrain.LAKE);
        PlacedHex pHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(pHex2);

        Location loc3 = new Location(0,2);
        Hex hex3 = new Hex("TileID1", Terrain.ROCK);
        hex3.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex pHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(pHex3);

        Location loc5 = new Location(1,2);
        Hex hex5 = new Hex("TileID2", Terrain.LAKE);
        PlacedHex pHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(pHex5);

        Settlement settlement = new Settlement(pHex3, placedHexes);
        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.expandVillage(player, loc3, Terrain.LAKE);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCanContinueExpansionPastImmediateAdjacentHexes() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);


        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex pHex = new PlacedHex(hex, loc);
        placedHexes.add(pHex);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        Location loc2 = new Location(-1,2);
        Hex hex2 = new Hex("TileID1", Terrain.LAKE);
        PlacedHex pHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(pHex2);

        Location loc3 = new Location(0,2);
        Hex hex3 = new Hex("TileID2", Terrain.LAKE);
        PlacedHex pHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(pHex3);

        Location loc4 = new Location(1,2);
        Hex hex4 = new Hex("TileID2", Terrain.LAKE);
        PlacedHex pHex4 = new PlacedHex(hex4, loc4);
        placedHexes.add(pHex4);

        Settlement settlement = new Settlement(pHex, placedHexes);
        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.expandVillage(player, loc, Terrain.LAKE);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testPlayerCannotPopulateAllExpandableHexes() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);


        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex pHex = new PlacedHex(hex, loc);
        placedHexes.add(pHex);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        Location loc2 = new Location(-1,2);
        Hex hex2 = new Hex("TileID1", Terrain.LAKE);
        PlacedHex pHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(pHex2);

        Location loc3 = new Location(0,2);
        Hex hex3 = new Hex("TileID2", Terrain.LAKE);
        PlacedHex pHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(pHex3);

        Location loc4 = new Location(1,2);
        Hex hex4 = new Hex("TileID2", Terrain.LAKE);
        PlacedHex pHex4 = new PlacedHex(hex4, loc4);
        placedHexes.add(pHex4);

        Settlement settlement = new Settlement(pHex, placedHexes);
        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            player.getPieceSet().placeMultipleVillagers(17);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }

        try {
            board.expandVillage(player, loc, Terrain.LAKE);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Player does not have enough pieces to populate the target hex"));
        }
    }

    @Test
    public void testCanExpandIntoHexesOfDifferingHeights() {

        Player player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex pHex = new PlacedHex(hex, loc);
        placedHexes.add(pHex);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE, 2);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        Location loc2 = new Location(-1,2);
        Hex hex2 = new Hex("TileID1", Terrain.LAKE, 3);
        PlacedHex pHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(pHex2);

        Location loc3 = new Location(0,2);
        Hex hex3 = new Hex("TileID2", Terrain.LAKE, 2);
        PlacedHex pHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(pHex3);

        Location loc4 = new Location(1,2);
        Hex hex4 = new Hex("TileID2", Terrain.LAKE, 4);
        PlacedHex pHex4 = new PlacedHex(hex4, loc4);
        placedHexes.add(pHex4);

        Settlement settlement = new Settlement(pHex, placedHexes);
        board.placedHexes = this.placedHexes;
        board.settlements.add(settlement);

        try {
            board.expandVillage(player, loc, Terrain.LAKE);
            System.out.println(player.getPieceSet().getNumberOfVillagersRemaining());
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCannotMakeIllegalTilePlacement() {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc1 = new Location(0,0);
        int rotation1 = 0;
        try {
            board.placeTile(tile1, loc1, rotation1);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile2 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Location loc2 = new Location(3,3);
        int rotation2 = 0;
        try {
            board.placeTile(tile2, loc2, rotation2);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Illegal Placement Location"));
        }
    }

    @Test
    public void testCannotNukeTotoro() {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        tile1.getRightHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc1 = new Location(0,0);
        int rotation1 = 0;
        try {
            board.placeTile(tile1, loc1, rotation1);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile2 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Location loc2 = new Location(1,-1);
        int rotation2 = 240;
        try {
            board.placeTile(tile2, loc2, rotation2);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc3 = new Location(1,-1);
        int rotation3 = 60;
        try {
            board.placeTile(tile3, loc3, rotation3);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Totoro exists under tile"));
        }
    }

    @Test
    public void testCannotNukeTiger() {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        tile1.getRightHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);
        Location loc1 = new Location(0,0);
        int rotation1 = 0;
        try {
            board.placeTile(tile1, loc1, rotation1);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile2 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Location loc2 = new Location(1,-1);
        int rotation2 = 240;
        try {
            board.placeTile(tile2, loc2, rotation2);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc3 = new Location(1,-1);
        int rotation3 = 60;
        try {
            board.placeTile(tile3, loc3, rotation3);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Tiger exists under tile"));
        }
    }

    @Test
    public void testCannotNukeEntireSettlement() {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        tile1.getRightHex().addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc1 = new Location(0,0);
        int rotation1 = 0;
        try {
            board.placeTile(tile1, loc1, rotation1);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
        board.updateSettlements();

        Tile tile2 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Location loc2 = new Location(1,-1);
        int rotation2 = 240;
        try {
            board.placeTile(tile2, loc2, rotation2);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc3 = new Location(1,-1);
        int rotation3 = 60;
        try {
            board.placeTile(tile3, loc3, rotation3);
        } catch (InvalidMoveException e) {
            assertTrue(e.getMessage().equals("Whole settlement exists under tile"));
        }
    }
}
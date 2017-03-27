package com.tigerisland;


import com.tigerisland.game.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;




import java.util.ArrayList;

import static org.junit.Assert.assertTrue;



public class BuildOptionStepDefs{
    private Board board;
    private ArrayList<PlacedHex> placedHexes;
    private Player player;
    private PlacedHex hexToPlaceTotoro;
    private PlacedHex hexToPlaceTiger;
    private String caughtErrorMessage;
    private String expectedErrorMessage;
    private Location locationToPlaceTotoro;
    private Location locationToPlaceTiger;
    private int originalNumberOfTotoroRemaining;
    private int originalNumberOfVillagersRemaining;


    public BuildOptionStepDefs() {
        this.board = new Board();
        this.placedHexes = new ArrayList<PlacedHex>();
        this.player = new Player(Color.BLACK);
    }

    @Given("^a settlement too small to accept a totoro$")
    public void addSmallSettlementToBoard(){
        expectedErrorMessage = "Cannot place totoro in a settlement of size 4 or smaller!";
        hexToPlaceTotoro = setUpSettlement();
        placedHexes.remove(hexToPlaceTotoro);
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        Location loc1 = new Location(0,0);
        hexToPlaceTotoro = new PlacedHex(hex1, loc1);
        placedHexes.add(0, hexToPlaceTotoro);
        Settlement settlement = new Settlement(placedHexes.get(1), placedHexes);
        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);
    }

    @Given("^a settlement already containing a totoro$")
    public void addSettlementWithTotoroToBoard(){
        expectedErrorMessage = "Cannot place totoro in a settlement already containing a Totoro";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-2);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS);
        Location loc6 = new Location(0,-3);
        hexToPlaceTotoro = new PlacedHex(hex6, loc6);
        placedHexes.add(hexToPlaceTotoro);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);

    }

    @Given("^a settlement already containing a tiger$")
    public void addSettlementWithTigerToBoard() {
        expectedErrorMessage = "Cannot place Tiger in a settlement already containing a Tiger";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);
        Location loc5 = new Location(0,-2);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.LAKE, 3);
        Location loc6 = new Location(0, -3);
        hexToPlaceTiger = new PlacedHex(hex6, loc6);
        placedHexes.add(hexToPlaceTiger);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);

    }

    @Given("^a settlement capable of accepting a totoro$")
    public void addSettlementCapableOfAcceptingTotoroToBoard(){
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc5 = new Location(0,-2);
        hexToPlaceTotoro = new PlacedHex(hex5, loc5);
        placedHexes.add(hexToPlaceTotoro);
        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS);
        Location loc6 = new Location(0,-3);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);
        placedHexes.add(placedHex6);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);
    }

    @Given("^a settlement capable of accepting a totoro with no adjacent placed hex$")
    public void addSettlementCapableOfAcceptingTotoroToBoardAndLocationWithUnplacedHex(){
        expectedErrorMessage = "Target hex does not exist";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-2);
        hexToPlaceTotoro = new PlacedHex(hex5, loc5);
        placedHexes.add(hexToPlaceTotoro);
        Settlement settlement = new Settlement(placedHex1, placedHexes);
        locationToPlaceTotoro = new Location(0,-3);
        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);

    }

    @Given("^a hex is of level three or greater$")
    public void addVolcanoHexOfLevelThreeToBoard() {
        expectedErrorMessage = "Cannot place a piece on a volcano hex";
        Hex volcanoHex = new Hex("volcanoHex", Terrain.VOLCANO, 3);
        locationToPlaceTiger = new Location(0,0);
        hexToPlaceTiger = new PlacedHex(volcanoHex, locationToPlaceTiger);
        placedHexes.add(hexToPlaceTiger);
        board.setPlacedHexes(placedHexes);

    }

    @Given("^a hex is not a volcano$")
    public void addNonVolcanoHexOfHeightLessThanThreeToBoard() {
        expectedErrorMessage = "Cannot build Tiger playground on hex of level less than 3";
        Hex hex = new Hex("hex", Terrain.GRASSLANDS, 2);
        locationToPlaceTiger = new Location(0,0);
        hexToPlaceTiger = new PlacedHex(hex, locationToPlaceTiger);
        placedHexes.add(hexToPlaceTiger);
        board.setPlacedHexes(placedHexes);
    }

    @Given("^an occupied hex")
    public void addAnOccupiedHexToBoard() {
        expectedErrorMessage = "Target hex already contains piece(s)";
        Hex hex = new Hex("hex", Terrain.GRASSLANDS, 2);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        locationToPlaceTiger = new Location(0,0);
        hexToPlaceTiger = new PlacedHex(hex, locationToPlaceTiger);
        placedHexes.add(hexToPlaceTiger);
        board.setPlacedHexes(placedHexes);
    }

    @When("^a player tries to place a totoro in the settlement$")
    public void attemptToPlaceTotoro() throws InvalidMoveException {
        try {
            board.placeTotoro(player, hexToPlaceTotoro.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on an occupied hex of the settlement$")
    public void attemptToPlaceTotoroOnOccupiedHex() throws InvalidMoveException {
        expectedErrorMessage = "Target hex already contains piece(s)";
        try {
            board.placeTotoro(player, hexToPlaceTotoro.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on an unplaced hex adjacent to the settlement$")
    public void attemptToPlaceTotoroOnUnplacedHex() throws InvalidMoveException {
        try {
            board.placeTotoro(player, locationToPlaceTotoro);
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro validly$")
    public void attemptToPlaceTotoroValidly() throws InvalidMoveException {
        locationToPlaceTotoro = new Location(0,-3);
        originalNumberOfTotoroRemaining = player.getPieceSet().getNumberOfTotoroRemaining();
        originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        try {
            board.placeTotoro(player, locationToPlaceTotoro);
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on a volcano$")
    public void attemptToPlaceTotoroOnVolcano() throws InvalidMoveException {
        expectedErrorMessage = "Cannot place a piece on a volcano hex";
        Hex hex6 = new Hex("volcanoHex", Terrain.VOLCANO);
        Location loc6 = new Location(2,0);
        hexToPlaceTotoro = new PlacedHex(hex6, loc6);
        placedHexes.add(hexToPlaceTotoro);
        try {
            board.placeTotoro(player, hexToPlaceTotoro.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a tiger on a hex$")
    public void attemptToPlaceTigerOnVolcano() throws InvalidMoveException {
        try {
            board.placeTiger(player, hexToPlaceTiger.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a tiger in the settlement$")
    public void attemptToPlaceTiger() throws InvalidMoveException {
        try {
            board.placeTiger(player, hexToPlaceTiger.getLocation());
        } catch (Exception e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on the hex bridging the gap$")
    public void attemptToPlaceTotoroBetweenTwoSmallSettlements(){
        try {
            board.placeTotoro(player, hexToPlaceTotoro.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @Then("^the move is rejected")
    public void expectedAndCaughtErrorMessageDontMatch(){
        assertTrue(caughtErrorMessage.equals(expectedErrorMessage));
    }

    @Then("^the move is accepted")
    public void expectedAndCaughtErrorMessageMatch(){
        assertTrue(caughtErrorMessage  == null);
    }

    @And("^the player's inventory updates properly")
    public void inventoryUpdatesProperly(){
        int finalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        int finalNumberOfTotoroRemaining = player.getPieceSet().getNumberOfTotoroRemaining();

        assertTrue(finalNumberOfTotoroRemaining == originalNumberOfTotoroRemaining - 1);
        assertTrue(finalNumberOfVillagersRemaining == originalNumberOfVillagersRemaining);
    }

    @And("^another settlement too small to accept a totoro$")
    public void addAnotherSmallSettlementCloseToTheFirst(){
        PlacedHex placedHex5 = setUpAnotherSettlement();
        Settlement settlement = new Settlement(placedHex5, placedHexes);
        board.setPlacedHexes(placedHexes);
        board.addSettlements(settlement);
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
        Location loc3 = new Location(0,-2);
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

    private PlacedHex setUpAnotherSettlement(){
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.JUNGLE);
        Hex hex7 = new Hex("hex7", Terrain.VOLCANO);
        Hex hex8 = new Hex("hex8", Terrain.GRASSLANDS);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 2);
        hex6.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 4);
        hex8.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 6);


        Location loc5 = new Location(-1,1);
        Location loc6 = new Location(-2,1);
        Location loc7 = new Location(-3,3);
        Location loc8 = new Location(-2,2);

        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);
        PlacedHex placedHex7 = new PlacedHex(hex7, loc7);
        PlacedHex placedHex8 = new PlacedHex(hex8, loc8);

        placedHexes.add(placedHex5);
        placedHexes.add(placedHex6);
        placedHexes.add(placedHex7);
        placedHexes.add(placedHex8);

        return placedHex5;
    }



}

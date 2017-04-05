package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class UpdateSettlementsStepDefs {
    private Board board;
    private ArrayList<PlacedHex> placedHexes;
    private Player player;

    private PlacedHex targetHex;
    private PlacedHex hexToExpandInto;
    private PlacedHex hexInSettlement;
    private Terrain expandTerrain;

    private String caughtErrorMessage;
    private String expectedErrorMessage;

    private Location targetLocation;

    private int originalNumberOfTotoroRemaining;
    private int originalNumberOfVillagersRemaining;

    private Score originalScore;
    private Score finalScore;


    public UpdateSettlementsStepDefs() {
        this.board = new Board();
        this.placedHexes = new ArrayList<PlacedHex>();
        this.player = new Player(Color.BLACK, 1);
    }

    @Given("^a player creates a new settlement$")
    public void aPlayerCreatesANewSettlement() {
        PlacedHex placedHex = createSettlementSizeOne();
        placedHexes.add(placedHex);

        board.placedHexes = placedHexes;
        Settlement settlement = new Settlement(placedHex, placedHexes);
        board.settlements.add(settlement);
    }

    @Given("^a player has two villages adjacent to one another$")
    public void aPlayerHasTwoVillagesAdjacentToOneAnother() {
        PlacedHex placedHex = createSettlementSizeOne();

        Hex hex = new Hex("hex2", Terrain.GRASSLANDS, 1);
        Location location = new Location(0,1);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex placedHex2 = new PlacedHex(hex, location);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex2);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex2, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);
    }

    @Given("^two settlements of different color are adjacent$")
    public void twoSettlementsOfDifferentColorAreAdjacent() {
        PlacedHex placedHex = createSettlementSizeOne();

        Hex hex = new Hex("hex2", Terrain.GRASSLANDS, 1);
        Location location = new Location(0,1);
        hex.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex placedHex2 = new PlacedHex(hex, location);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex2);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex2, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);
    }

    @Given("^a player has expanded an existing settlement becoming adjacent to another settlement of the same color$")
    public void aPlayerHasExpandedBecomingAdjacentToAnotherSettlementOfTheSameColor() {
        PlacedHex placedHex = createSettlementSizeOne();

        populatePlacedHexes();

        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS, 1);
        Location loc6 = new Location(0,4);
        hex6.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex6);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex6, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.expandVillage(player, placedHex.getLocation(), Terrain.ROCKY);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @Given("^a player expands an existing settlement becoming adjacent to a different colored settlement$")
    public void aPlayerExpandsBecomingAdjacentToADifferentColoredSettlement() {
        PlacedHex placedHex = createSettlementSizeOne();

        populatePlacedHexes();

        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS, 1);
        Location loc6 = new Location(0,4);
        hex6.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex6);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex6, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.expandVillage(player, placedHex.getLocation(), Terrain.ROCKY);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @Given("^a player builds a totoro sanctuary connecting two settlements$")
    public void aPlayerBuildsATotoroSanctuaryConnectingTwoSettlements() {
        PlacedHex placedHex = createSettlementSizeOne();

        populatePlacedHexes();

        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS, 1);
        Location loc6 = new Location(0,4);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        Hex hex7 = new Hex("hex7", Terrain.JUNGLE, 1);
        Location loc7 = new Location(0,5);
        hex7.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex placedHex7 = new PlacedHex(hex7, loc7);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex6);
        placedHexes.add(placedHex7);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex7, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.expandVillage(player, placedHex.getLocation(), Terrain.ROCKY);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        try {
            board.placeTotoro(player, placedHex6.getLocation());
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @Given("^a player builds a totoro sactuary between two different colored settlements$")
    public void aPlayerBuildsATotoroBetweenTwoDifferentColoredSettlements() {
        PlacedHex placedHex = createSettlementSizeOne();

        populatePlacedHexes();

        Hex hex6 = new Hex("hex6", Terrain.GRASSLANDS, 1);
        Location loc6 = new Location(0,4);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        Hex hex7 = new Hex("hex7", Terrain.JUNGLE, 1);
        Location loc7 = new Location(0,5);
        hex7.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex placedHex7 = new PlacedHex(hex7, loc7);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex6);
        placedHexes.add(placedHex7);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex7, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.expandVillage(player, placedHex.getLocation(), Terrain.ROCKY);
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        try {
            board.placeTotoro(player, placedHex6.getLocation());
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @Given("^a player builds a tiger connecting two settlements$")
    public void aPlayerBuildsATigerConnectingTwoSettlements() {
        PlacedHex placedHex = createSettlementSizeOne();

        Hex hex2 = new Hex("hex2", Terrain.ROCKY, 3);
        Location loc2 = new Location(0,1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);

        Hex hex3 = new Hex("hex3", Terrain.GRASSLANDS, 1);
        Location loc3 = new Location(0,2);
        hex3.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex3, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.placeTiger(player, placedHex2.getLocation());
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @Given("^a player builds a tiger between two different colored settlements$")
    public void aPlayerBuildsATigerBetweenTwoDifferentColoredSettlements() {
        PlacedHex placedHex = createSettlementSizeOne();

        Hex hex2 = new Hex("hex2", Terrain.ROCKY, 3);
        Location loc2 = new Location(0,1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);

        Hex hex3 = new Hex("hex3", Terrain.GRASSLANDS, 1);
        Location loc3 = new Location(0,2);
        hex3.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);

        placedHexes.add(placedHex);
        placedHexes.add(placedHex2);
        placedHexes.add(placedHex3);
        board.placedHexes = placedHexes;

        Settlement village1 = new Settlement(placedHex, placedHexes);
        Settlement village2 = new Settlement(placedHex3, placedHexes);
        board.settlements.add(village1);
        board.settlements.add(village2);

        try {
            board.placeTiger(player, placedHex2.getLocation());
        } catch (InvalidMoveException e) {
            e.getMessage();
        }
    }

    @When("^the turn is over and updateSettlements is called$")
    public void theTurnIsOverAndUpdateSettlementsIsCalled() {
        board.updateSettlements();
    }

    @Then("^the number of settlements should increase by one$")
    public void theNumberOfSettlementsShouldIncreaseByOne() {
        assertTrue(board.settlements.size() == 1);
    }

    @Then("^there should only be one settlement$")
    public void thereShouldOnlyBeOneSettlement() {
        assertTrue(board.settlements.size() == 1);
    }

    @Then("^there should be two settlements, not one$")
    public void thereShouldBeTwoSettlementsNotOne() {
        assertTrue(board.settlements.size() == 2);
    }

    private PlacedHex createSettlementSizeOne() {
        Hex hex = new Hex("hex1", Terrain.LAKE, 1);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location location = new Location(0,0);
        PlacedHex placedHex = new PlacedHex(hex, location);
        placedHexes.add(placedHex);

        return placedHex;
    }

    private void populatePlacedHexes() {
        Hex hex2 = new Hex("hex2", Terrain.ROCKY, 1);
        Location loc2 = new Location(0,1);
        PlacedHex placedHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(placedHex2);

        Hex hex3 = new Hex("hex3", Terrain.ROCKY, 1);
        Location loc3 = new Location(0, 2);
        PlacedHex placedHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(placedHex3);

        Hex hex4 = new Hex("hex4", Terrain.ROCKY, 1);
        Location loc4 = new Location(0,3);
        PlacedHex placedHex4 = new PlacedHex(hex4, loc4);
        placedHexes.add(placedHex4);

        Hex hex5 = new Hex("hex", Terrain.ROCKY, 1);
        Location loc5 = new Location(0,-1);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
    }


}

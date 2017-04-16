package com.tigerisland.game;


import com.tigerisland.game.board.*;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.Piece;
import com.tigerisland.game.pieces.PieceType;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerType;
import com.tigerisland.game.player.Score;
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

    private PlacedHex targetHex;
    private PlacedHex hexToExpandInto;
    private PlacedHex hexInSettlement;
    private Terrain expandTerrain;

    private String caughtErrorMessage;
    private String expectedErrorMessage;

    private Location targetLocation;

    private int originalNumberOfTotoroRemaining;
    private int originalNumberOfVillagersRemaining;
    private boolean shamanAlreadyPlayed;


    private Score originalScore;
    private Score finalScore;


    public BuildOptionStepDefs() {
        this.board = new Board();
        this.placedHexes = new ArrayList<PlacedHex>();
        this.player = new Player(Color.BLACK, "1", PlayerType.SAFEAI);
    }

    @Given("^a settlement too small to accept a totoro$")
    public void addSmallSettlementToBoard(){
        expectedErrorMessage = "Settlement already contains a totoro or is too small";
        targetHex = setUpSettlement();
        placedHexes.remove(targetHex);
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        Location loc1 = new Location(0,0);
        targetHex = new PlacedHex(hex1, loc1);
        placedHexes.add(0, targetHex);
        Settlement settlement = new Settlement(placedHexes.get(1), placedHexes);
        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement already containing a totoro$")
    public void addSettlementWithTotoroToBoard(){
        expectedErrorMessage = "Settlement already contains a totoro or is too small";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-2);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASS);
        Location loc6 = new Location(0,-3);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);

    }

    @Given("^a settlement already containing a tiger$")
    public void addSettlementWithTigerToBoard() {
        expectedErrorMessage = "Settlement already contains a tiger or is too small";
        PlacedHex placedHex1 = setUpSettlement();

        Location loc5 = new Location(0,-2);
        Location loc6 = new Location(0, -3);

        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.LAKE, 3);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);

        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        targetHex = new PlacedHex(hex6, loc6);

        placedHexes.add(placedHex5);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);

    }

    @Given("^a settlement capable of accepting a totoro$")
    public void addSettlementCapableOfAcceptingTotoroToBoard(){
        PlacedHex placedHex1 = setUpSettlement();

        Location loc5 = new Location(1,-2);
        Location loc6 = new Location(0,-3);

        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.GRASS);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        targetHex = new PlacedHex(hex5, loc5);
        PlacedHex placedHex6 = new PlacedHex(hex6, loc6);

        placedHexes.add(targetHex);
        placedHexes.add(placedHex6);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement capable of accepting a totoro with no adjacent placed hex$")
    public void addSettlementCapableOfAcceptingTotoroToBoardAndLocationWithUnplacedHex(){
        expectedErrorMessage = "Target hex does not exist";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-2);
        targetHex = new PlacedHex(hex5, loc5);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);
        targetLocation = new Location(0,-3);
        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);

    }

    @Given("^a settlement that could build a totoro but the player is out of totoro pieces$")
    public void aSettlementCanPlaceTotoroButIsOutOfTotoroPieces() {
        expectedErrorMessage = "No totoro remaining in game inventory.";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc5 = new Location(2,0);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        Hex hex6 = new Hex("hex6", Terrain.ROCK, 2);
        Location loc6 = new Location(3,0);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);

        try {
            player.getPieceSet().placeTotoro();
            player.getPieceSet().placeTotoro();
            player.getPieceSet().placeTotoro();
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement that can build a tiger but the player is out of tiger pieces$")
    public void aSettlementCanBuildATigerButIsOutOfTigerPieces() {
        expectedErrorMessage = "No tiger remaining in game inventory";
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE,3);
        Location loc5 = new Location(0,-3);
        targetHex = new PlacedHex(hex5, loc5);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        try {
            player.getPieceSet().placeTiger();
            player.getPieceSet().placeTiger();
        } catch (InvalidMoveException e) {
            e.getMessage();
        }

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a volcano hex is of level three or greater$")
    public void addVolcanoHexOfLevelThreeToBoard() {
        expectedErrorMessage = "Cannot place a piece on a volcano hex";
        Hex volcanoHex = new Hex("volcanoHex", Terrain.VOLCANO, 3);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(volcanoHex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);

    }

    @Given("^a hex is not a volcano$")
    public void addNonVolcanoHexOfHeightLessThanThreeToBoard() {
        expectedErrorMessage = "Cannot build Tiger playground on hex of level less than 3";
        Hex hex = new Hex("hex", Terrain.GRASS, 2);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(hex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);
    }

    @Given("^an occupied hex")
    public void addAnOccupiedHexToBoard() {
        expectedErrorMessage = "Target hex already contains piece(s)";
        Hex hex = new Hex("hex", Terrain.GRASS, 1);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(hex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);
    }

    @Given("^a hex is not part of a settlement$")
    public void aHexIsNotPartOfASettlement() throws Throwable {
        expectedErrorMessage = "Settlement already contains a tiger or is too small";
        Hex hex6 = new Hex("hex6", Terrain.LAKE, 3);
        Location loc6 = new Location(0, -3);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);

        board.setPlacedHexes(placedHexes);
    }

    @Given("^a settlement adjacent to a level three valid hex$")
    public void aSettlementAdjacentToALevelThreeHex() {
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        hex1.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc1 = new Location(0,0);
        PlacedHex placedHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(placedHex1);

        Hex hex2 = new Hex("hex2", Terrain.GRASS, 3);
        Location loc2 = new Location(0,1);
        targetHex = new PlacedHex(hex2, loc2);
        placedHexes.add(targetHex);

        Settlement settlement = new Settlement(placedHex1, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement adjacent to a level four valid hex$")
    public void aSettlementAdjacentToALevelFourValidHex() {
        Hex hex1 = new Hex("hex1", Terrain.LAKE);
        hex1.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc1 = new Location(0,0);
        PlacedHex placedHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(placedHex1);

        Hex hex2 = new Hex("hex2", Terrain.GRASS, 4);
        Location loc2 = new Location(0,1);
        targetHex = new PlacedHex(hex2, loc2);
        placedHexes.add(targetHex);

        Settlement settlement = new Settlement(placedHex1, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement containing a totoro and a valid hex for tiger placement$")
    public void aSettlementContainingATotoroAndAValidHexForTigerPlacement() {
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-3);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASS, 3);
        Location loc6 = new Location(2,0);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement containing a totoro and a valid hex directly next to a totoro$")
    public void aSettlementContainingATotoroAndAValidHexDirectlyNextToATotoro() {
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        Location loc5 = new Location(0,-3);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASS, 3);
        Location loc6 = new Location(0,-4);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement containing a tiger and is valid to build a totoro$")
    public void aSettlementContainingATigerAndIsValidToBuildATotoro() {
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE,3);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);
        Location loc5 = new Location(0,-3);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASS);
        Location loc6 = new Location(2,0);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement containing a tiger and a valid hex directly next to the tiger$")
    public void aSettlementContainingATigerAndAValidHexDirectlyNextToTheTiger() {
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE,3);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TIGER), 1);
        Location loc5 = new Location(0,-3);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Hex hex6 = new Hex("hex6", Terrain.GRASS);
        Location loc6 = new Location(0,-4);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement capable of accepting a totoro with a valid hex of height greater than one$")
    public void aSettlementCapableOfAcceptingATotoroWithAValidHexOfHeightGreaterThanOne() {
        PlacedHex placedHex1 = setUpSettlement();
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        Location loc5 = new Location(2,0);
        PlacedHex placedHex5 = new PlacedHex(hex5, loc5);
        placedHexes.add(placedHex5);
        Settlement settlement = new Settlement(placedHex1, placedHexes);

        Hex hex6 = new Hex("hex6", Terrain.ROCK, 2);
        Location loc6 = new Location(3,0);
        targetHex = new PlacedHex(hex6, loc6);
        placedHexes.add(targetHex);

        board.setPlacedHexes(placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a settlement that is of your own color and an adjacent Volcano hex$")
    public void aSettlementAdjacentToAVolcano() {
        expectedErrorMessage = "Cannot expand into a Volcano";

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.VOLCANO);
        hexToExpandInto = new PlacedHex(hex1, loc1);
        placedHexes.add(hexToExpandInto);

        expandTerrain = hex1.getHexTerrain();

        Location loc2 = new Location(0,2);
        Hex hex2 = new Hex("TileID1", Terrain.ROCK);
        hex2.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex2, loc2);
        placedHexes.add(hexInSettlement);

        Settlement settlement = new Settlement(hexInSettlement, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);

    }

    @Given("^a settlement with valid adjacent hexes$")
    public void aSettlementWithValidAdjacentHexes() {
        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex, loc);
        placedHexes.add(hexInSettlement);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE);
        hexToExpandInto = new PlacedHex(hex1, loc1);
        placedHexes.add(hexToExpandInto);

        expandTerrain = hex1.getHexTerrain();

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

        Settlement settlement = new Settlement(hexInSettlement, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a player has fewer pieces than needed to expand$")
    public void aPlayerHasFewerPiecesThanNeededToExpand() {
        expectedErrorMessage = "Player does not have enough pieces to populate the target hex";

        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex, loc);
        placedHexes.add(hexInSettlement);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE);
        hexToExpandInto = new PlacedHex(hex1, loc1);
        placedHexes.add(hexToExpandInto);

        expandTerrain = hex1.getHexTerrain();

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

        Settlement settlement = new Settlement(hexInSettlement, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);

        try {
            player.getPieceSet().placeMultipleVillagers(17);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @Given("^a settlement adjacent to another player's settlement$")
    public void aSettlementAdjacentToAnotherPlayersSettlement() {
        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex, loc);
        placedHexes.add(hexInSettlement);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE);
        hexToExpandInto = new PlacedHex(hex1, loc1);
        placedHexes.add(hexToExpandInto);

        expandTerrain = hex1.getHexTerrain();

        Location loc2 = new Location(-1,2);
        Hex hex2 = new Hex("TileID1", Terrain.LAKE);
        hex2.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex pHex2 = new PlacedHex(hex2, loc2);
        placedHexes.add(pHex2);

        Location loc3 = new Location(0,2);
        Hex hex3 = new Hex("TileID2", Terrain.LAKE);
        hex3.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex pHex3 = new PlacedHex(hex3, loc3);
        placedHexes.add(pHex3);

        Location loc4 = new Location(1,2);
        Hex hex4 = new Hex("TileID2", Terrain.LAKE);
        hex4.addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        PlacedHex pHex4 = new PlacedHex(hex4, loc4);
        placedHexes.add(pHex4);

        Settlement blackSettlement = new Settlement(hexInSettlement, placedHexes);
        Settlement whiteSettlement = new Settlement(pHex2, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(blackSettlement);
        board.getSettlements().add(whiteSettlement);
    }

    @Given("^a settlement adjacent to hexes of different heights$")
    public void aSettlementAdjacentToHexesOfDifferentHeights() {
        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex, loc);
        placedHexes.add(hexInSettlement);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE, 2);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        expandTerrain = hex1.getHexTerrain();

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

        Settlement settlement = new Settlement(hexInSettlement, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);
    }

    @Given("^a player has fewer pieces to expand across different heights$")
    public void aPlayerHasFewerPiecesToExpandAcrossDifferentHeights() {
        expectedErrorMessage = "Player does not have enough pieces to populate the target hex";

        Location loc = new Location(0,0);
        Hex hex = new Hex("TileID1", Terrain.ROCK);
        hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        hexInSettlement = new PlacedHex(hex, loc);
        placedHexes.add(hexInSettlement);

        Location loc1 = new Location(0,1);
        Hex hex1 = new Hex("TileID1", Terrain.LAKE, 2);
        PlacedHex pHex1 = new PlacedHex(hex1, loc1);
        placedHexes.add(pHex1);

        expandTerrain = hex1.getHexTerrain();

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

        Settlement settlement = new Settlement(hexInSettlement, placedHexes);
        board.setPlacedHexes(this.placedHexes);
        board.getSettlements().add(settlement);

        try {
            player.getPieceSet().placeMultipleVillagers(11);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @Given("^a Volcano hex$")
    public void addVolcanoHexToBoard(){
        expectedErrorMessage = "Cannot place a piece on a volcano hex";
        Hex volcanoHex = new Hex("volcanoHex", Terrain.VOLCANO, 1);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(volcanoHex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);
    }

    @Given("^a nonvolcanic hex of height greater than one$")
    public void createHexOnHeightGreaterThanOne() throws Throwable {
        expectedErrorMessage = "Cannot create village above height 1";
        Hex hex = new Hex("hex", Terrain.VOLCANO, 2);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(hex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);
    }

    @Given("^a nonexistent hex location$")
    public void aNonexistentHexLocation() throws Throwable {
        expectedErrorMessage = "Target hex does not exist";
        targetLocation = new Location(0,0);
    }

    @And("^there is a valid hex$")
    public void addHeight1EmptyNonVolcanicHex(){
        Hex hex = new Hex("hex1", Terrain.LAKE, 1);
        targetLocation = new Location(0,0);
        targetHex = new PlacedHex(hex, targetLocation);
        placedHexes.add(targetHex);
        board.setPlacedHexes(placedHexes);
    }

    @And("^the player has no villagers$")
    public void thePlayerHasNoVillagers() throws Throwable {
        expectedErrorMessage = "No villagers remaining in game inventory.";
        originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfRegularVillagersRemaining();
        try {
            player.getPieceSet().placeMultipleVillagers(originalNumberOfVillagersRemaining);
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @And("^the player has no shaman$")
    public void thePlayerHasNoShaman() throws Throwable {
        expectedErrorMessage = "No shaman remaining in game inventory.";
        shamanAlreadyPlayed = true;
        try {
            player.getPieceSet().placeShaman();
        } catch(InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    @When("^a player tries to place a totoro in the settlement$")
    public void attemptToPlaceTotoro() throws InvalidMoveException {
        try {
            board.placeTotoro(player, targetHex.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on an occupied hex of the settlement$")
    public void attemptToPlaceTotoroOnOccupiedHex() throws InvalidMoveException {
        expectedErrorMessage = "Target hex already contains piece(s)";
        try {
            board.placeTotoro(player, targetHex.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on an unplaced hex adjacent to the settlement$")
    public void attemptToPlaceTotoroOnUnplacedHex() throws InvalidMoveException {
        try {
            board.placeTotoro(player, targetLocation);
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro validly$")
    public void attemptToPlaceTotoroValidly() throws InvalidMoveException {
        targetLocation = new Location(0,-3);
        originalNumberOfTotoroRemaining = player.getPieceSet().getNumberOfTotoroRemaining();
        originalNumberOfVillagersRemaining = player.getPieceSet().getNumberOfVillagersRemaining();
        try {
            board.placeTotoro(player, targetLocation);
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on a volcano$")
    public void attemptToPlaceTotoroOnVolcano() throws InvalidMoveException {
        expectedErrorMessage = "Cannot place a piece on a volcano hex";
        Hex hex = new Hex("volcanoHex", Terrain.VOLCANO);
        Location loc = new Location(2,0);
        targetHex = new PlacedHex(hex, loc);
        placedHexes.add(targetHex);
        try {
            board.placeTotoro(player, targetHex.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a tiger on a hex$")
    public void attemptToPlaceTigerOnVolcano() throws InvalidMoveException {
        try {
            board.placeTiger(player, targetHex.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a tiger in the settlement$")
    public void attemptToPlaceTiger() throws InvalidMoveException {
        try {
            board.placeTiger(player, targetHex.getLocation());
        } catch (Exception e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player tries to place a totoro on the hex bridging the gap$")
    public void attemptToPlaceTotoroBetweenTwoSmallSettlements(){
        try {
            board.placeTotoro(player, targetHex.getLocation());
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player attempts to expand$")
    public void aPlayerAttemptsToExpand() {
        try {
            board.expandVillage(player, hexInSettlement.getLocation(), expandTerrain);
            System.out.println(player.getPieceSet().getNumberOfVillagersRemaining());

        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player attempts to create new village on the hex$")
    public void attemptToCreateNewVillageOnHex(){
        try {
            board.createVillageWithVillager(player, targetLocation);
        } catch (InvalidMoveException e) {
            caughtErrorMessage = e.getMessage();
        }
    }

    @When("^a player attempts to create new village on the hex with a shaman$")
    public void attemptToCreateNewVillageOnHexWithShaman(){
        try {
            board.createVillageWithShaman(player, targetLocation);
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

    @Then("^the player has the correct amount of remaining villagers$")
    public void checkThePlayerHasTheCorrectAmountOfRemainingVillagers() {
        assertTrue(player.getPieceSet().getNumberOfVillagersRemaining() == 16);
    }

    @Then("^the player only expands on empty hexes$")
    public void thePlayerOnlyExpandsOnEmptyHexes() {
        assertTrue(player.getPieceSet().getNumberOfVillagersRemaining() == 19);
    }

    @Then("^the player expands if they have necessary amount of pieces$")
    public void checkThePlayerSuccessfullyExpanded() {
        assertTrue(player.getPieceSet().getNumberOfVillagersRemaining() == 9);
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
        board.getSettlements().add(settlement);
    }

    @And("^a nearby settlement containing a totoro$")
    public void addAnotherSettlementContainingATotoroCloseToTheFirst(){
        PlacedHex totoroHex = setUpAnotherSettlementWithATotoro();
        Hex hex = new Hex("targetHex", Terrain.GRASS);
        Location loc = new Location(-1, 1);
        PlacedHex targetHex = new PlacedHex(hex, loc);
        placedHexes.add(targetHex);
        this.targetHex = targetHex;
        Settlement settlement = new Settlement(totoroHex, placedHexes);
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
        Hex hex8 = new Hex("hex8", Terrain.GRASS);

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

    private PlacedHex setUpAnotherSettlementWithATotoro(){
        Hex hex5 = new Hex("hex5", Terrain.LAKE);
        Hex hex6 = new Hex("hex6", Terrain.JUNGLE);
        Hex hex7 = new Hex("hex7", Terrain.VOLCANO);
        Hex hex8 = new Hex("hex8", Terrain.GRASS);

        hex5.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        hex6.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 4);
        hex8.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 6);


        Location loc5 = new Location(-1,2);
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

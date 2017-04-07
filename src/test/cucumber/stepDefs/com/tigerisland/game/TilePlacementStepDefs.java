package com.tigerisland.game;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class TilePlacementStepDefs{
    Board myBoard;
    boolean hasCaughtException;
    String expectedExceptionMessage;
    String caughtExceptionMessage;

    @Given("^existing pieces on a board$")
    public void existingPiecesOnABoard() throws Throwable {
        myBoard = new Board();

        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc1 = new Location(0,0);
        int rotation1 = 240;
        myBoard.placeTile(tile1, loc1, rotation1);

        Tile tile2 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Location loc2 = new Location(1,1);
        int rotation2 = 120;
        myBoard.placeTile(tile2, loc2, rotation2);

        Tile tile3 = new Tile(Terrain.ROCKY, Terrain.JUNGLE);
        Location loc3 = new Location(-1,1);
        int rotation3 = 180;
        myBoard.placeTile(tile3, loc3, rotation3);
    }

    @When("^a player places a tile properly on existing ones$")
    public void aPlayerPlacesATileProperlyOnExistingOnes() throws Throwable {
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(0,0);
            int rotation4 = 60;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @Then("^do not deny the player from doing so$")
    public void doNotDenyThePlayerFromDoingSo() throws Throwable {
        assertTrue(!hasCaughtException);
    }

    @When("^a player places a tile at height one not directly next to existing pieces$")
    public void aPlayerPlacesATileAtHeightOneNotDirectlyNextToExistingPieces() throws Throwable {
        expectedExceptionMessage = "Illegal Placement Location";
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(-3,0);
            int rotation4 = 120;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex) {
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @Then("^deny the player from doing so$")
    public void denyThePlayerFromDoingSo() throws Throwable {
        assertTrue(hasCaughtException);
        assertTrue(caughtExceptionMessage.equals(expectedExceptionMessage));
    }

    @When("^a player places a tile at height one that is directly next to existing pieces$")
    public void aPlayerPlacesATileAtHeightOneThatIsDirectlyNextToExistingPieces() throws Throwable {
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(-2,0);
            int rotation4 = 120;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player places a tile on another tile and the volcanoes do not overlap$")
    public void aPlayerPlacesATileOnAnotherTileAndTheVolcanoesDoNotOverlap() throws Throwable {
        expectedExceptionMessage = "Illegal Placement Location";
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(0,1);
            int rotation4 = 180;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player places a tile on another tile with overhang$")
    public void aPlayerPlacesATileOnAnotherTileWithOverhang() throws Throwable {
        expectedExceptionMessage = "Illegal Placement Location";
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(1,1);
            int rotation4 = 300;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player places a tile directly on top of existing one$")
    public void aPlayerPlacesATileDirectlyOnTopOfExistingOne() throws Throwable {
        expectedExceptionMessage = "Illegal Placement Location";
        hasCaughtException = false;
        try{
            Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc4 = new Location(0,0);
            int rotation4 = 240;
            myBoard.placeTile(tile4, loc4, rotation4);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @Given("^existing pieces and entities on a board$")
    public void existingPiecesAndEntitiesOnABoard() throws Throwable {
        myBoard = new Board();

        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Location loc1 = new Location(0,0);
        int rotation1 = 240;
        myBoard.placeTile(tile1, loc1, rotation1);

        Tile tile2 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Location loc2 = new Location(1,1);
        int rotation2 = 120;
        myBoard.placeTile(tile2, loc2, rotation2);

        Tile tile3 = new Tile(Terrain.ROCKY, Terrain.JUNGLE);
        Location loc3 = new Location(-1,0);
        int rotation3 = 60;
        myBoard.placeTile(tile3, loc3, rotation3);

        Tile tile4 = new Tile(Terrain.ROCKY, Terrain.ROCKY);
        Location loc4 = new Location(2,-1);
        int rotation4 = 60;
        myBoard.placeTile(tile4, loc4, rotation4);

        Tile tile5 = new Tile(Terrain.LAKE, Terrain.LAKE);
        Location loc5 = new Location(-2,0);
        int rotation5 = 240;
        myBoard.placeTile(tile5, loc5, rotation5);

        Tile tile6 = new Tile(Terrain.ROCKY, Terrain.ROCKY);
        Location loc6 = new Location(0,-2);
        int rotation6 = 300;
        myBoard.placeTile(tile6, loc6, rotation6);

        Tile tile7 = new Tile(Terrain.ROCKY, Terrain.ROCKY);
        Location loc7 = new Location(-1,-2);
        int rotation7 = 180;
        myBoard.placeTile(tile7, loc7, rotation7);

        Player myPlayer = new Player(Color.BLUE, "1");
        myBoard.createVillage(myPlayer, new Location(2,0));

        myBoard.createVillage(myPlayer, new Location(0,1));
        myBoard.createVillage(myPlayer, new Location(-1,1));

        myBoard.createVillage(myPlayer, new Location(-2,-1));
        myBoard.createVillage(myPlayer, new Location(-1,-1));
        myBoard.createVillage(myPlayer, new Location(0,-1));
        myBoard.createVillage(myPlayer, new Location(1,-1));
        myBoard.createVillage(myPlayer, new Location(-2,-2));

        myBoard.updateSettlements();
        myBoard.placeTotoro(myPlayer, new Location(1,-2));
    }

    @When("^a player nukes existing meeples that do not comprise a full settlment$")
    public void aPlayerNukesExistingMeeplesThatDoNotCompriseAFullSettlment() throws Throwable {
        myBoard.updateSettlements();
        hasCaughtException = false;
        try{
            Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc5 = new Location(0,0);
            int rotation5 = 120;
            myBoard.placeTile(tile5, loc5, rotation5);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player attempts to nuke a single hex settlment$")
    public void aPlayerAttemptsToNukeASingleHexSettlment() throws Throwable {
        expectedExceptionMessage = "Whole settlement exists under tile";
        myBoard.updateSettlements();
        hasCaughtException = false;
        try{
            Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc5 = new Location(1,1);
            int rotation5 = 240;
            myBoard.placeTile(tile5, loc5, rotation5);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player attempts to nuke a size two hex settlment$")
    public void aPlayerAttemptsToNukeASizeTwoHexSettlment() throws Throwable {
        expectedExceptionMessage = "Whole settlement exists under tile";
        myBoard.updateSettlements();
        hasCaughtException = false;
        try{
            Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc5 = new Location(0,0);
            int rotation5 = 60;
            myBoard.placeTile(tile5, loc5, rotation5);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }

    @When("^a player attempts to nuke a Totoro$")
    public void aPlayerAttemptsToNukeATotoro() throws Throwable {
        expectedExceptionMessage = "Totoro exists under tile";
        myBoard.updateSettlements();
        hasCaughtException = false;
        try{
            Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
            Location loc5 = new Location(0,-2);
            int rotation5 = 0;
            myBoard.placeTile(tile5, loc5, rotation5);
        }
        catch (Exception ex){
            caughtExceptionMessage = ex.getMessage();
            hasCaughtException = true;
        }
    }
}

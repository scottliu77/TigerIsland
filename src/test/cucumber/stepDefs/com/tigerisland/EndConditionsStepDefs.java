package com.tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import static org.junit.Assert.assertTrue;

public class EndConditionsStepDefs {

    private TigerIsland tigerIsland;
    private Game game;
    private Player player;

    public EndConditionsStepDefs() throws ArgumentParserException {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        this.game = tigerIsland.match.games.get(0);
        this.player = this.game.gameSettings.getPlayerOrder().getCurrentPlayer();

    }

    @Given("^it is a player's turn$")
    public void aPlayerHasNoMorePiecesLeft() throws Throwable {
        assertTrue(player == game.gameSettings.getPlayerOrder().getCurrentPlayer());
    }

    @When("^they play their last piece$")
    public void theirTurnEnds() throws Throwable {
        player.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { player.getPieceSet().placeTotoro(); }
    }

    @When("^that player is unable to build$")
    public void thatPlayerIsUnableToBuild() throws Throwable {
        player.getPieceSet().placeMultipleVillagers(20);
        assertTrue(EndConditions.noValidMoves(player, game.board));
    }

    @And("^they have not played their last piece$")
    public void theyHaveNotPlayedTheirLastPiece() throws Throwable {
        assertTrue(player.getPieceSet().inventoryEmpty() == false);
    }

    @Then("^the game ends$")
    public void theGameEnds() throws Throwable {
        assertTrue(EndConditions.noEndConditionsAreMet(player, game.board) == false);
    }
}

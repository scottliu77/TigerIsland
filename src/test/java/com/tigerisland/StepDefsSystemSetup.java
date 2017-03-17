package com.tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class StepDefsSystemSetup {

    private TigerIsland tigerIsland;
    private Game aGame;
    private ArrayList<Player> players;
    private Board board;

    @Given("^a game is created$")
    public void aGameIsCreated() throws Throwable {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        aGame = tigerIsland.match.games.get(0);
    }

    @And("^that game has players$")
    public void thatGameHasPlayers() throws Throwable {
        players = aGame.players;
    }

    @When("^the game is started$")
    public void theGameIsStarted() throws Throwable {
        aGame.start();
    }

    @Then("^all players have (\\d+) points$")
    public void allPlayersHavePoints(int arg0) throws Throwable {
        for(Player player: players) {
            if(player.getScore() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^then the game has a board$")
    public void thenTheGameHasABoard() throws Throwable {
        assertTrue(aGame.board != null);
    }
}

package com.tigerisland;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class MyStepdefs {

    private TigerIsland tigerIsland;
    private Game aGame;
    private ArrayList<Player> players;

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
        // Write code here that turns the phrase above into concrete actions
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
}

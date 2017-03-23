package com.tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SystemSetupStepDefs {

    private TigerIsland tigerIsland;
    private Game aGame;
    private PlayerOrder playerOrder;
    private Board board;

    private boolean gameRunning = false;

    @Given("^a game is created$")
    public void aGameIsCreated() throws Throwable {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        aGame = tigerIsland.match.games.get(0);
    }

    @And("^that game has players$")
    public void thatGameHasPlayers() throws Throwable {
        playerOrder = aGame.gameSettings.getPlayerOrder();
    }

    @When("^the game has not yet started$")
    public void theGameHasNotYetStarted() throws Throwable {
        assertTrue(gameRunning == false);
    }

    @Then("^all players have (\\d+) points$")
    public void allPlayersHavePoints(int arg0) throws Throwable {
        for(Player player: playerOrder.getPlayerList()) {
            if(player.getScore().getScoreValue() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^then the game has a board$")
    public void thenTheGameHasABoard() throws Throwable {
        assertTrue(aGame.board != null);
    }

    @Then("^all players have (\\d+) villagers$")
    public void allPlayersHaveVillagers(int arg0) throws Throwable {
        for(Player player: playerOrder.getPlayerList()) {
            if(player.getPieceSet().villagerSet.size() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^all players have (\\d+) totoros$")
    public void allPlayersHaveTotoros(int arg0) throws Throwable {
        for(Player player: playerOrder.getPlayerList()) {
            if(player.getPieceSet().totoroSet.size() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^then the game has rules the following moves:$")
    public void thenTheGameHasRulesTheFollowingMoves(List<String> moves) throws Throwable {
        int movesToCheck = moves.size();
        try {
            for (String move : moves) {
                for (BuildActionType buildActionType : BuildActionType.values()) {
                    if(buildActionType.getMoveString().equals(move)) {
                        movesToCheck--;
                    }

                }
            }
            if(movesToCheck == 0) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception exception) {
            assertTrue(false);
        }
    }
}

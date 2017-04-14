package com.tigerisland;

import com.tigerisland.game.*;
import com.tigerisland.game.board.Board;
import com.tigerisland.game.moves.BuildActionType;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerSet;
import com.tigerisland.settings.GameSettings;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SystemSetupStepDefs {

    private Game aGame;
    private PlayerSet playerSet;
    private Board board;

    private boolean gameRunning = false;

    @Given("^a game is created$")
    public void aGameIsCreated() throws Throwable {
        GameSettings gameSettings = new GameSettings();
        gameSettings.setGameID("A");
        aGame = new Game(gameSettings);
    }

    @And("^that game has players$")
    public void thatGameHasPlayers() throws Throwable {
        playerSet = aGame.getGameSettings().getPlayerSet();
    }

    @When("^the game has not yet started$")
    public void theGameHasNotYetStarted() throws Throwable {
        assertTrue(gameRunning == false);
    }

    @Then("^all players have (\\d+) points$")
    public void allPlayersHavePoints(int arg0) throws Throwable {
        for(Player player: playerSet.getPlayerList().values()) {
            if(player.getScore().getScoreValue() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^then the game has a board$")
    public void thenTheGameHasABoard() throws Throwable {
        assertTrue(aGame.getBoard() != null);
    }

    @Then("^all players have (\\d+) villagers$")
    public void allPlayersHaveVillagers(int arg0) throws Throwable {
        for(Player player: playerSet.getPlayerList().values()) {
            if(player.getPieceSet().getNumberOfVillagersRemaining() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^all players have (\\d+) totoros$")
    public void allPlayersHaveTotoros(int arg0) throws Throwable {
        for(Player player: playerSet.getPlayerList().values()) {
            if(player.getPieceSet().getNumberOfTotoroRemaining() != arg0) {
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
                    if(buildActionType.getBuildString().equals(move)) {
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

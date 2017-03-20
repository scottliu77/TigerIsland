package com.tigerisland;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SystemSetupStepDefs {

    private TigerIsland tigerIsland;
    private Game aGame;
    private PlayOrder playOrder;
    private Board board;

    @Given("^a game is created$")
    public void aGameIsCreated() throws Throwable {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        aGame = tigerIsland.match.games.get(0);
    }

    @And("^that game has players$")
    public void thatGameHasPlayers() throws Throwable {
        playOrder = aGame.players;
    }

    @Then("^all players have (\\d+) points$")
    public void allPlayersHavePoints(int arg0) throws Throwable {
        for(Player player: playOrder.getPlayerList()) {
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

    @Then("^all players have (\\d+) villagers$")
    public void allPlayersHaveVillagers(int arg0) throws Throwable {
        for(Player player: playOrder.getPlayerList()) {
            if(player.getPieceSet().villagerSet.size() != arg0) {
                assertTrue(false);
            }
        }
        assertTrue(true);
    }

    @Then("^all players have (\\d+) totoros$")
    public void allPlayersHaveTotoros(int arg0) throws Throwable {
        for(Player player: playOrder.getPlayerList()) {
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
                for (MoveType moveType :MoveType.values()) {
                    if(moveType.getMoveString().equals(move)) {
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

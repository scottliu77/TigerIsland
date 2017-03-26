package com.tigerisland;

import com.tigerisland.game.EndConditions;
import com.tigerisland.game.Game;
import com.tigerisland.game.Player;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class EndConditionsStepDefs {

    private TigerIsland tigerIsland;
    private Game game;
    private Player player;
    private ArrayList<Player> players;

    public EndConditionsStepDefs() throws ArgumentParserException {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        this.game = tigerIsland.getMatch().games.get(0);
        this.player = this.game.getGameSettings().getPlayerList().getCurrentPlayer();
        this.players = this.game.getGameSettings().getPlayerList().getPlayerList();
    }

    @Given("^it is a player's turn$")
    public void aPlayerHasNoMorePiecesLeft() throws Throwable {
        assertTrue(player == game.getGameSettings().getPlayerList().getCurrentPlayer());
    }

    @When("^they play their last piece$")
    public void theirTurnEnds() throws Throwable {
        player.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { player.getPieceSet().placeTotoro(); }
    }

    @When("^that player is unable to build$")
    public void thatPlayerIsUnableToBuild() throws Throwable {
        player.getPieceSet().placeMultipleVillagers(20);
        assertTrue(EndConditions.noValidMoves(player, game.getBoard()));
    }

    @And("^they have not played their last piece$")
    public void theyHaveNotPlayedTheirLastPiece() throws Throwable {
        assertTrue(player.getPieceSet().inventoryEmpty() == false);
    }

    @And("^the top scoring players have the same score$")
    public void theTopScoringPlayersHaveTheSameScore() throws Throwable {
        assertTrue(players.get(0).getScore().getScoreValue() == players.get(1).getScore().getScoreValue());
    }

    @And("^only one player has the top score$")
    public void theOnlyOnePlayHasTheTopScore() throws Throwable {
        players.get(1).getScore().addPoints(25);
        assertTrue(players.get(1).getScore().getScoreValue() != players.get(0).getScore().getScoreValue());
    }

    @Then("^the game ends$")
    public void theGameEnds() throws Throwable {
        assertTrue(EndConditions.noEndConditionsAreMet(player, game.getBoard()) == false);
    }

    @Then("^then the next highest scoring player wins$")
    public void thenTheNextHighestScoringPlayerWins() throws Throwable {
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner != player);
    }


    @Then("^then the player who placed all their pieces wins$")
    public void thenThePlayerWhoPlacedAllTheirPiecesWins() throws Throwable {
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner != player);
    }


    @Then("^then the player with the highest score wins$")
    public void thenThePlayerWithTheHighestScoreWins() throws Throwable {
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner.getScore().getScoreValue() > player.getScore().getScoreValue());
    }
}

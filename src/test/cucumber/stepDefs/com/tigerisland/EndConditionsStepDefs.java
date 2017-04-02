package com.tigerisland;

import com.tigerisland.game.EndConditions;
import com.tigerisland.game.Game;
import com.tigerisland.game.Player;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class EndConditionsStepDefs {

    private TigerIsland tigerIsland;
    private Game game;
    private Player player;
    private ArrayList<Player> players;
    private Deck deck;

    public EndConditionsStepDefs() throws ArgumentParserException {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        this.game = tigerIsland.getMatch().games.get(0);
        this.player = this.game.getGameSettings().getPlayerSet().getCurrentPlayer();
        this.players = this.game.getGameSettings().getPlayerSet().getPlayerList();
        this.deck = new Deck();
    }

    @Given("^it is a player's turn$")
    public void aPlayerHasNoMorePiecesLeft() throws Throwable {
        assertTrue(player == game.getGameSettings().getPlayerSet().getCurrentPlayer());
    }

    @When("^they play their last piece$")
    public void theirTurnEnds() throws Throwable {
        player.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { player.getPieceSet().placeTotoro(); }
        for(int tigers = 0; tigers < 2; tigers++) { player.getPieceSet().placeTiger(); }
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
        assertTrue(winner == player);
    }


    @Then("^then the player with the highest score wins$")
    public void thenThePlayerWithTheHighestScoreWins() throws Throwable {
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner.getScore().getScoreValue() > player.getScore().getScoreValue());
    }

    @When("^there are no unplayed tiles left$")
    public void thereAreNoUnplayedTilesLeft() throws Throwable {
        assertTrue(deck.getDeckSize() == 0);
    }

    @And("^the scores are tied$")
    public void theScoresAreTied() throws Throwable {
        assertTrue(player.getScore().getScoreValue() == players.get(1).getScore().getScoreValue());
    }

    @Then("^the player with the fewest remaining totoros wins$")
    public void thePlayerWithTheFewestRemainingTotorosWins() throws Throwable {
        placeAllButTotoros();
        players.get(1).getPieceSet().placeTotoro();
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner == players.get(1));
    }

    private void placeAllButTotoros() throws InvalidMoveException {
        players.get(0).getPieceSet().placeMultipleVillagers(20);
        players.get(1).getPieceSet().placeMultipleVillagers(20);
        for(int tigers = 0; tigers < 2; tigers++) {
            players.get(0).getPieceSet().placeTiger();
            players.get(1).getPieceSet().placeTiger();
        }
    }

    @And("^the number of totoros are tied$")
    public void theNumberOfTotorosAreTied() throws Throwable {
        int currentPlayerTotoros = player.getPieceSet().getNumberOfTotoroRemaining();
        int altPlayerTotoros = player.getPieceSet().getNumberOfTotoroRemaining();
        assertTrue(currentPlayerTotoros == altPlayerTotoros);
    }

    @Then("^the player with the fewest remaining tigers wins$")
    public void thePlayerWithTheFewestRemainingTigersWins() throws Throwable {
        placeAllButTigers();
        players.get(1).getPieceSet().placeTiger();
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner == players.get(1));
    }

    private void placeAllButTigers() throws InvalidMoveException {
        players.get(0).getPieceSet().placeMultipleVillagers(20);
        players.get(1).getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) {
            players.get(0).getPieceSet().placeTotoro();
            players.get(1).getPieceSet().placeTotoro();
        }
    }

    @And("^the number of tigers are tied$")
    public void theNumberOfTigersAreTied() throws Throwable {
        int currentPlayerTigers = player.getPieceSet().getNumberOfTigersRemaining();
        int altPlayerTigers = player.getPieceSet().getNumberOfTigersRemaining();
        assertTrue(currentPlayerTigers == altPlayerTigers);
    }

    @Then("^the player with the fewest remaining villagers wins$")
    public void thePlayerWithTheFewestRemainingVillagersWins() throws Throwable {
        placeAllButVillagers();
        player.getPieceSet().placeVillager();
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner == player);
    }

    private void placeAllButVillagers() throws InvalidMoveException {
        for(int totoros = 0; totoros < 3; totoros++) {
            players.get(0).getPieceSet().placeTotoro();
            players.get(1).getPieceSet().placeTotoro();
        }
        for(int tigers = 0; tigers < 2; tigers++) {
            players.get(0).getPieceSet().placeTiger();
            players.get(1).getPieceSet().placeTiger();
        }
    }
}

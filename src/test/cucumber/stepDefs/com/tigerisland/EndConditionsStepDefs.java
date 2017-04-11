package com.tigerisland;

import com.tigerisland.game.EndConditions;
import com.tigerisland.game.Game;
import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.Player;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class EndConditionsStepDefs {

    private final String playerID = "1";
    private final String opponentID = "2";
    private Game game;
    private Player player;
    private HashMap<String, Player> players;
    private Deck deck;

    public EndConditionsStepDefs() throws ArgumentParserException {
        GlobalSettings globalSettings = new GlobalSettings();
        globalSettings.getServerSettings().setPlayerID(playerID);
        globalSettings.getServerSettings().setOpponentID(opponentID);
        GameSettings gameSettings = new GameSettings();
        gameSettings.setGameID("A");
        this.game = new Game(gameSettings);
        game.getGameSettings().getPlayerSet().setCurrentPlayer(playerID);
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
        assertTrue(players.get(playerID).getScore().getScoreValue() == players.get(playerID).getScore().getScoreValue());
    }

    @And("^only one player has the top score$")
    public void theOnlyOnePlayHasTheTopScore() throws Throwable {
        players.get(opponentID).getScore().addPoints(25);
        assertTrue(players.get(opponentID).getScore().getScoreValue() != players.get(playerID).getScore().getScoreValue());
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
        assertTrue(player.getScore().getScoreValue() == players.get(playerID).getScore().getScoreValue());
    }

    @Then("^the player with the fewest remaining totoros wins$")
    public void thePlayerWithTheFewestRemainingTotorosWins() throws Throwable {
        placeAllButTotoros();
        players.get(playerID).getPieceSet().placeTotoro();
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner == players.get(playerID));
    }

    private void placeAllButTotoros() throws InvalidMoveException {
        players.get(playerID).getPieceSet().placeMultipleVillagers(20);
        players.get(opponentID).getPieceSet().placeMultipleVillagers(20);
        for(int tigers = 0; tigers < 2; tigers++) {
            players.get(playerID).getPieceSet().placeTiger();
            players.get(opponentID).getPieceSet().placeTiger();
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
        players.get(playerID).getPieceSet().placeTiger();
        Player winner = EndConditions.calculateWinner(player, players);
        assertTrue(winner == players.get(playerID));
    }

    private void placeAllButTigers() throws InvalidMoveException {
        players.get(playerID).getPieceSet().placeMultipleVillagers(20);
        players.get(opponentID).getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) {
            players.get(playerID).getPieceSet().placeTotoro();
            players.get(opponentID).getPieceSet().placeTotoro();
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
            players.get(playerID).getPieceSet().placeTotoro();
            players.get(opponentID).getPieceSet().placeTotoro();
        }
        for(int tigers = 0; tigers < 2; tigers++) {
            players.get(playerID).getPieceSet().placeTiger();
            players.get(opponentID).getPieceSet().placeTiger();
        }
    }
}

package com.tigerisland;

import com.tigerisland.game.player.PlayerSet;
import com.tigerisland.game.Game;
import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.board.Tile;
import com.tigerisland.settings.GameSettings;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class OfflineSystemSetupStepDefs {

    private Game aGame;
    private PlayerSet playerSet;
    private Deck deck;
    private Boolean gameRunning = false;

    @Given("^an offline game is created$")
    public void aGameIsCreated() throws Throwable {
        GameSettings gameSettings = new GameSettings();
        gameSettings.setDeck();
        gameSettings.setGameID("A");
        aGame = new Game(gameSettings);
        assertTrue(aGame.getGameSettings().getGlobalSettings().getServerSettings().offline);
    }

    @And("^that offline game has players$")
    public void thatGameHasPlayers() throws Throwable {
        assertTrue(aGame.getGameSettings().getGlobalSettings().getServerSettings().offline);
        playerSet = aGame.getGameSettings().getPlayerSet();
    }

    @When("^the offline game has not yet started$")
    public void theGameHasNotYetStarted() throws Throwable {
        assertTrue(gameRunning == false);
    }

    @Then("^then the game has a tile deck$")
    public void thenTheGameHasATileDeck() throws Throwable {
        deck = aGame.getGameSettings().getDeck();
        assertTrue(deck != null);
    }

    @And("^that deck has (\\d+) total tiles$")
    public void thatDeckHasTotalTiles(int arg0) throws Throwable {
        assertTrue(deck.getDeckSize() == 48);
    }

    @And("^that deck has (\\d+) tiles of each valid combination of terrain types$")
    public void thatDeckHasTilesOfEachValidCombinationOfTerrainTypes(int arg0) throws Throwable {
        Set<String> uniquePossibleCombos = getPossibleUniqueTerrainCombinations();

        ArrayList<String> allTerrainCombinationsInDeck = getAllStringCombinationsFoundInDeck();

        for(String combination: uniquePossibleCombos) {
            assertTrue(Collections.frequency(allTerrainCombinationsInDeck, combination) == arg0);
        }
    }

    private Set<String> getPossibleUniqueTerrainCombinations() {
        Set<String> uniqueTerrainCombinations = new HashSet<String>();
        for (Terrain terrainType1 : Terrain.values()) {
            for (Terrain terrainType2 : Terrain.values()) {
                if (terrainType1 != Terrain.VOLCANO && terrainType2 != Terrain.VOLCANO) {
                    uniqueTerrainCombinations.add(terrainType1.getTerrainString() + terrainType2.getTerrainString());
                }
            }
        }
        return uniqueTerrainCombinations;
    }

    private ArrayList<String> getAllStringCombinationsFoundInDeck() {
        ArrayList<String> combinationsFound = new ArrayList<String>();
        for(Tile tile: deck.tileDeck) {
            combinationsFound.add(tile.getLeftHex().getHexTerrain().getTerrainString() + tile.getRightHex().getHexTerrain().getTerrainString());
        }
        return combinationsFound;
    }
}

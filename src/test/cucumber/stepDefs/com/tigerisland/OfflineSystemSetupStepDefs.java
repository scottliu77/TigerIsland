package com.tigerisland;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class OfflineSystemSetupStepDefs {

    private TigerIsland tigerIsland;
    private Game aGame;
    private ArrayList<Player> players;
    private Deck deck;
    
    @Given("^an offline game is created$")
    public void aGameIsCreated() throws Throwable {
        tigerIsland = new TigerIsland();
        tigerIsland.parseArguments(new String[]{});
        aGame = tigerIsland.match.games.get(0);
        assertTrue(aGame.gameSettings.globalSettings.offline);
    }

    @And("^that offline game has players$")
    public void thatGameHasPlayers() throws Throwable {
        assertTrue(aGame.gameSettings.globalSettings.offline);
        players = aGame.players;
    }

    @When("^the offline game is started$")
    public void theGameIsStarted() throws Throwable {
        assertTrue(aGame.gameSettings.globalSettings.offline);
        aGame.start();
    }

    @Then("^then the game has a tile deck$")
    public void thenTheGameHasATileDeck() throws Throwable {
        deck = aGame.gameSettings.getDeck();
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
                    uniqueTerrainCombinations.add(terrainType1.getType() + terrainType2.getType());
                }
            }
        }
        return uniqueTerrainCombinations;
    }

    private ArrayList<String> getAllStringCombinationsFoundInDeck() {
        ArrayList<String> combinationsFound = new ArrayList<String>();
        for(Tile tile: deck.tileDeck) {
            combinationsFound.add(tile.getLeftHex().getHexTerrain().getType() + tile.getRightHex().getHexTerrain().getType());
        }
        return combinationsFound;
    }
}

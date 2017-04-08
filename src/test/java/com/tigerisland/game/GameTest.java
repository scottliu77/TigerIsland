package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.TigerIsland;
import com.tigerisland.messenger.Message;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class GameTest {

    private GlobalSettings globalSettings = new GlobalSettings();
    private BlockingQueue<Message> inboundMessages;
    private GameSettings gameSettings;
    private Game game;

    private Player playerBlack = new Player(Color.BLACK, "1", PlayerType.SAFEAI);

    private String[] tournamentSettings = new String[]{"--offline", "false", "--turnTime", "1.5", "--ipaddress", "localhost", "--port", "6539", "--username", "username", "--password", "password"};

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.gameSettings.constructPlayerSet();
        this.game = new Game(gameSettings);
    }

    @Before
    public void createPlayerWithNoPieces() {
        try {
            playerBlack.getPieceSet().placeMultipleVillagers(20);
            for (int ii = 0; ii < 3; ii++){
                playerBlack.getPieceSet().placeTotoro();
            }
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    private void placeDummyTilePlacementAndBuildInQueue() {
        inboundMessages.add(new Message("GAME A MOVE 1 PLACE ROCKY+GRASSLANDS AT 0 0 0 1 FOUND SETTLEMENT AT -1 -1 0"));
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }

    @Test
    public void testCanGetGameSettings() {
        GameSettings retrievedGameSettings = game.getGameSettings();
        assertTrue(retrievedGameSettings != null);
    }

    @Test
    public void testCanGetBoard() {
        Board retrievedBoard = game.getBoard();
        assertTrue(retrievedBoard != null);
    }

    @Test
    public void testCanGetGameID() {
        assertTrue(game.getGameID() == "A");
    }

    @Test
    public void testCanPlaceStartingTileOfGame() throws InvalidMoveException {
        game.board.placeStartingTile();
        assertTrue(game.getBoard().placedHexes.size() == 5);
    }

    @Ignore("Ignoring can run game test") @Test
    public void testCanRunGame() {
        Thread gameThread = new Thread(game);
        gameThread.run();
    }

    @Ignore("Ignoring test can run mock online game") @Test
    public void testCanRunMockOnlineGame() {
        GlobalSettings mockSettings = configureGlobalSettingsForMockGame();
        Game mockOnlineGame = new Game(new GameSettings(mockSettings));
        Thread gameThread = new Thread(mockOnlineGame);
        gameThread.run();
    }

    private GlobalSettings configureGlobalSettingsForMockGame() {
        TigerIsland tigerIsland = new TigerIsland();
        try {
            tigerIsland.parseArguments(tournamentSettings);
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
        return tigerIsland.getGlobalSettings();
    }
}

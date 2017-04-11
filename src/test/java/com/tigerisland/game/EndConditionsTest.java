package com.tigerisland.game;

import com.tigerisland.GlobalSettings;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

// NOTE
// Insert the following in any test to render board
// TextGUI textGUI = new TextGUI();
// textGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());

public class EndConditionsTest {

    private Game game;
    private Player currentPlayer;
    private HashMap<String, Player> players;
    private Board board;
    private Tile tile;
    private Tile tile2;
    private Tile tile3;
    private Location location;
    private Location location2;
    private Location location3;

    @Before
    public void createBasicMocks() throws InvalidMoveException {
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        this.tile2 = new Tile(Terrain.LAKE, Terrain.ROCK);
        this.tile3 = new Tile(Terrain.LAKE, Terrain.GRASS);
        this.location = new Location(0,0);
        this.location2 = new Location(-1, 0);
        this.location3 = new Location(-2, 0);

        this.board = new Board();

        board.placeTile(tile, location, 0);
        board.placeTile(tile2, location2, 60);
        board.placeTile(tile3, location3, 120);

        GlobalSettings globalSettings = new GlobalSettings();
        globalSettings.getServerSettings().setPlayerID("1");
        globalSettings.getServerSettings().setOpponentID("2");
        PlayerSet tempPlayerSet = new PlayerSet(globalSettings);
        this.players = tempPlayerSet.getPlayerList();

        tempPlayerSet.setCurrentPlayer("1");
        this.currentPlayer = tempPlayerSet.getCurrentPlayer();

    }

    @Test
    public void testEndConditionsNotMetByDefault() {
        assertTrue(EndConditions.noEndConditionsAreMet(currentPlayer, board) == true);
    }

    @Test
    public void testDoesntErroneouslyFindPlayerToBeOutOfLegalMoves() throws InvalidMoveException {
        for(int totoroMove = 0; totoroMove < 2; totoroMove++) {
            currentPlayer.getPieceSet().placeTotoro();
        }
        currentPlayer.getPieceSet().placeMultipleVillagers(9);
        assertTrue(EndConditions.noValidMoves(currentPlayer, board) == false);
    }

    @Test
    public void testDoesntErroneouslyFindPlayerToBeOutOfPieces() throws InvalidMoveException {
        for(int totoroMove = 0; totoroMove < 3; totoroMove++) {
            currentPlayer.getPieceSet().placeTotoro();
        }
        currentPlayer.getPieceSet().placeMultipleVillagers(19);
        assertTrue(EndConditions.playerIsOutOfPiecesOfTwoTypes(currentPlayer) == false);
    }

    @Test
    public void testEndConditionsMetWhenPlayerOutOfPieces() throws InvalidMoveException {
        for(int totoroMove = 0; totoroMove < 3; totoroMove++) {
            currentPlayer.getPieceSet().placeTotoro();
        }
        for(int tigerMove = 0; tigerMove < 2; tigerMove++) {
            currentPlayer.getPieceSet().placeTiger();
        }
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        assertTrue(EndConditions.playerIsOutOfPiecesOfTwoTypes(currentPlayer));
    }

    @Test
    public void testEndConditionMetNoSizeFiveSettlementAndNoVillagers() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(1, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testEndConditionMetWhenNoVillagersAndNoSize5Settlements() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        for(int tigers = 0; tigers < 2; tigers++) { currentPlayer.getPieceSet().placeTiger(); }
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.WHITE, PieceType.TOTORO), 1);


        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

//    @Test
//    public void testEndConditionNotMetWhenNoVillagersButSize5Settlement() throws InvalidMoveException {
//        currentPlayer.getPieceSet().placeMultipleVillagers(20);
//        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
//        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
//        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
//        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
//        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
//
//        board.updateSettlements();
//        TextGUI.printMap(board);
//        assertTrue(EndConditions.noValidMoves(currentPlayer, board) == false);
//    }

    @Test
    public void testEndConditionMetMetWhenNoVillagersAndSize5SettlementWithTotoro() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.WHITE, PieceType.TOTORO), 1);

        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testEndConditionMetMetWhenNoVillagersAndNoSpaceForTotoro() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);
        board.hexAt(new Location(1, 0)).addPiecesToHex(new Piece(Color.WHITE, PieceType.VILLAGER), 1);

        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testCanGetWinnerWhenPlayerCantMakeAValidMove() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        Player winner = EndConditions.calculateWinner(currentPlayer, players);
        assertTrue(winner != currentPlayer);
    }

    @Test
    public void testCanGetWinnerWhenPlayerPlaysLastPieceCurrentPlayerWins() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { currentPlayer.getPieceSet().placeTotoro(); }
        for(int tigers = 0; tigers < 2; tigers++) { currentPlayer.getPieceSet().placeTiger(); }
        currentPlayer.getScore().addPoints(1);
        Player winner = EndConditions.calculateWinner(currentPlayer, players);
        assertTrue(winner == currentPlayer);
    }

    @Test
    public void testCanGetWinnerWhenPlayerPlaysLastPieceCurrentPlayerLoses() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { currentPlayer.getPieceSet().placeTotoro(); }
        for(int tigers = 0; tigers < 2; tigers++) { currentPlayer.getPieceSet().placeTiger(); }
        players.get("2").getScore().addPoints(1);
        Player winner = EndConditions.calculateWinner(currentPlayer, players);
        assertTrue(winner != currentPlayer);
    }

    @Test
    public void testCanGetWinnerWhenPlayerPlaysLastPieceScoresTied() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        for(int totoros = 0; totoros < 3; totoros++) { currentPlayer.getPieceSet().placeTotoro(); }
        for(int tigers = 0; tigers < 2; tigers++) { currentPlayer.getPieceSet().placeTiger(); }
        Player winner = EndConditions.calculateWinner(currentPlayer, players);
        assertTrue(winner == currentPlayer);
    }
}

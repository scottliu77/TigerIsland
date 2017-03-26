package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

// NOTE
// Insert the following in any test to render board
// TextGUI textGUI = new TextGUI();
// textGUI.printMap(board.locationsOfPlacedHexes(), board.edgeSpaces, board.hexesOfPlacedHexes());

public class EndConditionsTest {

    private Player currentPlayer;
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
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.tile2 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        this.tile3 = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);
        this.location = new Location(0,0);
        this.location2 = new Location(-1, 0);
        this.location3 = new Location(-2, 0);

        this.board = new Board();
        this.currentPlayer = new Player(Color.BLACK);

        board.placeTile(tile, location, 0);
        board.placeTile(tile2, location2, 60);
        board.placeTile(tile3, location3, 120);
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
        assertTrue(EndConditions.playerIsOutOfPieces(currentPlayer) == false);
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
        assertTrue(EndConditions.playerIsOutOfPieces(currentPlayer));
    }

    @Test
    public void testEndConditionMetNoSizeFiveSettlementAndNoVillagers() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(1, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testEndConditionMetMetWhenNoVillagersAndNoSize5Settlements() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        for(int tigers = 0; tigers < 2; tigers++) { currentPlayer.getPieceSet().placeTiger(); }
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);


        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testEndConditionMetNotMetWhenNoVillagersButSize5Settlement() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board) == false);
    }

    @Test
    public void testEndConditionMetMetWhenNoVillagersAndSize5SettlementWithTotoro() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);

        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }

    @Test
    public void testEndConditionMetMetWhenNoVillagersAndNoSpaceForTotoro() throws InvalidMoveException {
        currentPlayer.getPieceSet().placeMultipleVillagers(20);
        board.hexAt(new Location(0, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-1, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-2, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 1)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(-3, 0)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        board.hexAt(new Location(1, 0)).addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);

        board.updateSettlements();
        assertTrue(EndConditions.noValidMoves(currentPlayer, board));
    }
}

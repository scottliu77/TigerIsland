package com.tigerisland.AI;

import com.tigerisland.AI.AI_Info;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AI_InfoTest {
    Board board;
    Tile testTile;
    Player p1;
    Player p2;

    @Before
    public void setUp() {
        this.board = new Board();
        this.testTile = new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS);
        this.p1 = new Player(Color.BLUE, "1");
        this.p2 = new Player(Color.PURPLE, "2");
    }

    @Test
    public void tilePlacementInfoTest1(){
        ArrayList<TilePlacement> legalPlacements = AI_Info.returnValidTilePlacements(testTile, board);
        for(int ii=0; ii<legalPlacements.size(); ii++){
            Location loc = legalPlacements.get(ii).getLocation();
            int rot = legalPlacements.get(ii).getRotation();
            assert(board.isALegalTilePlacment(loc,rot));
        }
    }

    @Test
    public void tilePlacementInfoTest2() throws InvalidMoveException {
        placeTilesForTest2();

        ArrayList<TilePlacement> legalPlacements = AI_Info.returnValidTilePlacements(testTile, board);
        for(int ii=0; ii<legalPlacements.size(); ii++){
            Location loc = legalPlacements.get(ii).getLocation();
            int rot = legalPlacements.get(ii).getRotation();
            assert(board.isALegalTilePlacment(loc,rot));
        }
    }
    public void placeTilesForTest2() throws  InvalidMoveException{
        Tile tile1 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
        Location loc1 = new Location(0,0);
        board.placeTile(tile1, loc1, 60);

        Tile tile2 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
        Location loc2 = new Location(-1,0);
        board.placeTile(tile2, loc2, 240);

        Tile tile3 = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);
        Location loc3 = new Location(1,0);
        board.placeTile(tile3, loc3, 240);

        Tile tile4 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
        Location loc4 = new Location(1,-2);
        board.placeTile(tile4, loc4, 240);

        Tile tile5 = new Tile(Terrain.LAKE,Terrain.LAKE);
        Location loc5 = new Location(0,-2);
        board.placeTile(tile5, loc5, 180);

        Tile tile6 = new Tile(Terrain.LAKE,Terrain.LAKE);
        Location loc6 = new Location(2,-2);
        board.placeTile(tile6, loc6, 300);

        Tile tile7 = new Tile(Terrain.LAKE,Terrain.LAKE);
        Location loc7 = new Location(0,0);
        board.placeTile(tile7, loc7, 300);

        Tile tile8 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
        Location loc8 = new Location(0,-2);
        board.placeTile(tile8, loc8, 60);

        Tile tile9 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
        Location loc9 = new Location(1,-2);
        board.placeTile(tile9, loc9, 300);

        Tile tile10 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
        Location loc10 = new Location(1,-2);
        board.placeTile(tile10, loc10, 60);
    }

    @Test
    public void testReturnValidVillagePlacements() throws InvalidMoveException {
        placeTilesForValidVillagePlacement();
        ArrayList<Location> possiblePlacements = AI_Info.returnValidVillagePlacements(board);
        assertTrue(possiblePlacements.size() == 4);
    }

    public void placeTilesForValidVillagePlacement() throws InvalidMoveException {
        Tile tile1 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
        Location loc1 = new Location(0,0);


        Tile tile2 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
        Location loc2 = new Location(-1,0);

        board.placeTile(tile1, loc1, 240);
        board.placeTile(tile2, loc2, 60);
    }

    @Test
    public void testReturnValidSettlementExpansions() throws InvalidMoveException{
        placeTilesForValidExpansionTest();
        board.updateSettlements();
        ArrayList<SettlementAndTerrainListPair> possibleExpansions = AI_Info.returnValidVillageExpansions(p1, board);
        SettlementAndTerrainListPair settlementAndTerrainListPair = possibleExpansions.get(0);
        assertTrue(settlementAndTerrainListPair.getTerrainList().contains(Terrain.GRASSLANDS));
        assertTrue(settlementAndTerrainListPair.getTerrainList().contains(Terrain.JUNGLE));
        assertTrue(settlementAndTerrainListPair.getTerrainList().size() == 2);

    }

    public void placeTilesForValidExpansionTest() throws InvalidMoveException{
        Tile tile1 = new Tile(Terrain.JUNGLE,Terrain.JUNGLE);
        Location loc1 = new Location(0,0);


        Tile tile2 = new Tile(Terrain.ROCKY,Terrain.ROCKY);
        Location loc2 = new Location(-1,0);

        Tile tile3 = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);
        Location loc3 = new Location(1,1);

        board.placeTile(tile1, loc1, 240);
        board.placeTile(tile2, loc2, 60);
        board.placeTile(tile3, loc3, 180);

        board.createVillage(p1, new Location(1, 0));

    }

    @Test
    public void testForTotoroValidation() throws InvalidMoveException {
        placeTilesForTest2();

        ArrayList<Location> legalTotoroPlacements = AI_Info.returnValidTotoroPlacements(p1.getPlayerColor(), board);
        assert(legalTotoroPlacements.size()==0);

        setUpBoardForTotoroAndTigerTests();

        ArrayList<Location> legalTotoroPlacementsP1 = AI_Info.returnValidTotoroPlacements(p1.getPlayerColor(), board);
        ArrayList<Location> legalTotoroPlacementsP2 = AI_Info.returnValidTotoroPlacements(p2.getPlayerColor(), board);

        assert(legalTotoroPlacementsP1.size()==0);
        assert(legalTotoroPlacementsP2.size()==2);
    }

    @Test
    public void testForTigerValidation() throws InvalidMoveException {
        placeTilesForTest2();


        ArrayList<Location> legalTigerPlacements = AI_Info.returnValidTigerPlacements(p1.getPlayerColor(), board);
        assert(legalTigerPlacements.size()==0);

        setUpBoardForTotoroAndTigerTests();

        ArrayList<Location> legalTigerPlacementsP1 = AI_Info.returnValidTigerPlacements(p1.getPlayerColor(), board);
        ArrayList<Location> legalTigerPlacementsP2 = AI_Info.returnValidTigerPlacements(p2.getPlayerColor(), board);

        assert(legalTigerPlacementsP1.size()==1);
        assert(legalTigerPlacementsP2.size()==2);
    }

    public void setUpBoardForTotoroAndTigerTests() throws InvalidMoveException{
        board.createVillage(p1,new Location(2,-1));
        board.createVillage(p2,new Location(3,-2));

        board.updateSettlements();
        board.expandVillage(p2, new Location(3,-2), Terrain.ROCKY);

        board.updateSettlements();
        board.createVillage(p2,new Location(1,-3));

        board.updateSettlements();
        board.expandVillage(p2, new Location(3,-2), Terrain.LAKE);

        board.updateSettlements();
        board.expandVillage(p2, new Location(3,-2), Terrain.ROCKY);
    }

    @Test
    public void testCanFindNoHabitableLevelThreeHexes() throws InvalidMoveException {
       ArrayList<PlacedHex> habitableLevelThreeHexes = AI_Info.findEmptyHabitableLevelThreePlacedHexes(board);
       assertTrue(habitableLevelThreeHexes.size() == 0);
    }

    @Test
    public void testCanFindHabitableLevelThreeHexes() throws InvalidMoveException {
        setUpBoardForFindingLevelThreeHexes();
        ArrayList<PlacedHex> habitableLevelThreeHexes = AI_Info.findEmptyHabitableLevelThreePlacedHexes(board);
        ArrayList<PlacedHex> expected = new ArrayList<PlacedHex>();
        Hex hex = new Hex("hex", Terrain.GRASSLANDS, 3);
        Location location = new Location(0, 0);
        PlacedHex placedHex = new PlacedHex(hex, location);
        expected.add(placedHex);
        System.out.println(habitableLevelThreeHexes.size());
        System.out.println(expected.size());
        assertTrue(habitableLevelThreeHexes.size() == expected.size());
    }

    private void setUpBoardForFindingLevelThreeHexes() {
        Hex hex = new Hex("hex", Terrain.GRASSLANDS, 3);
        Location location = new Location(0, 0);
        PlacedHex placedHex = new PlacedHex(hex, location);
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        placedHexes.add(placedHex);

    }

    public void testForFindingNukableTotoroAcceptingSettlements() throws InvalidMoveException {
        placeTilesForTest2();
        setUpBoardForTotoroAndTigerTests();
        Tile tileThatWillHostVolcanoToNukeFrom = new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS);
        Location locationForVolcanoToNukeFrom = new Location(1, -4);
        board.placeTile(tileThatWillHostVolcanoToNukeFrom, locationForVolcanoToNukeFrom, 240);
        Tile tileThatWillHostVolcanoToNukeFrom1 = new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS);
        Location locationForVolcanoToNukeFrom1 = new Location(2, -4);
        board.placeTile(tileThatWillHostVolcanoToNukeFrom1, locationForVolcanoToNukeFrom1, 300);
        board.updateSettlements();
        Tile tile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        ArrayList<TilePlacement> tilePlacementsThatWouldPreventTotoroPlacement = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTotoroPlacement(p2.getPlayerColor(),board, tile);
        assertTrue(tilePlacementsThatWouldPreventTotoroPlacement.size() == 3);
    }

}

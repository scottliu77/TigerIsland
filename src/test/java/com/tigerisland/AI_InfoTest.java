package com.tigerisland;

import com.tigerisland.game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AI_InfoTest {
    Board board;
    Player p1;
    Player p2;

    @Before
    public void setUp() {
        this.board = new Board();
        this.p1 = new Player(Color.BLUE);
        this.p2 = new Player(Color.PURPLE);
    }

    @Test
    public void tilePlacementInfoTest1(){
        ArrayList[] legalPlacements = AI_Info.returnValidTilePlacements(board);
        int size = legalPlacements[0].size();
        for(int ii=0; ii<size; ii++){
            Location loc = (Location)(legalPlacements[0].get(ii));
            int rot = (Integer)(legalPlacements[1].get(ii));
            assert(board.isALegalTilePlacment(loc,rot));
        }
    }

    @Test
    public void tilePlacementInfoTest2() throws InvalidMoveException {
        placeTilesForTest2();

        ArrayList[] legalPlacements = AI_Info.returnValidTilePlacements(board);
        int size = legalPlacements[0].size();
        for(int ii=0; ii<size; ii++){
            Location loc = (Location)(legalPlacements[0].get(ii));
            int rot = (Integer)(legalPlacements[1].get(ii));
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

}

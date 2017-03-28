package com.tigerisland;

import com.tigerisland.game.Board;
import com.tigerisland.game.Location;
import com.tigerisland.game.PlacedHex;
import com.tigerisland.game.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AI_Info {
    private static int x_lowerBound;
    private static int x_upperBound;
    private static int y_lowerBound;
    private static int y_upperBound;

    //ToDo : I'm not sure what is the best way to return the valid moves. They need both a Location and a Rotation.
    //ToDo : If you look at the end of the function, you will see that I put them both in an array of size 2.
    //ToDo : Let me know if you have better ideas
    public static ArrayList[] returnValidTilePlacements(Board board){
        ArrayList<Location> locationsOfValidMoves = new ArrayList<Location>();
        ArrayList<Integer> rotationsOfValidMoves = new ArrayList<Integer>();
        getBoundsOfBoard(board);
        for(int xx=x_lowerBound-1; xx<=x_upperBound+1; xx++){
            Location loc;
            int rot;
            for(int yy=y_lowerBound-1; yy<=y_upperBound+1; yy++) {
                //2-hexes toward bottom/1-hex on top - Three different orientations
                loc = new Location(xx, yy);
                rot = 0;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }

                loc = new Location(xx+1, yy);
                rot = 120;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }

                loc = new Location(xx, yy+1);
                rot = 240;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }

                //2-hexes toward top/1-hex on bottom - Three different orientations
                loc = new Location(xx, yy);
                rot = 60;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }

                loc = new Location(xx, yy+1);
                rot = 180;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }

                loc = new Location(xx-1, yy+1);
                rot = 300;
                if (board.isALegalTilePlacment(loc, rot)){
                    locationsOfValidMoves.add(loc);
                    rotationsOfValidMoves.add(rot);
                }
            }
        }
        //Here, I combine Location/Rotation into the array of size 2 because I don't know how else to do it without restructuring a lot of other code.
        ArrayList[] listOfValidMoves = new ArrayList[2];
        listOfValidMoves[0] = locationsOfValidMoves;
        listOfValidMoves[1] = rotationsOfValidMoves;
        return listOfValidMoves;
    }
    private static void getBoundsOfBoard(Board board){
        x_lowerBound = Integer.MAX_VALUE;
        x_upperBound = Integer.MIN_VALUE;
        y_lowerBound = Integer.MAX_VALUE;
        y_upperBound = Integer.MIN_VALUE;
        ArrayList<Location> edgeSpaces = board.getEdgeSpaces();
        for(int ii=0; ii<edgeSpaces.size(); ii++){
            x_lowerBound = Math.min(edgeSpaces.get(ii).getX(), x_lowerBound);
            x_upperBound = Math.max(edgeSpaces.get(ii).getX(), x_upperBound);
            y_lowerBound = Math.min(edgeSpaces.get(ii).getY(), y_lowerBound);
            y_upperBound = Math.max(edgeSpaces.get(ii).getY(), y_upperBound);
        }
    }

    public static ArrayList<Location> returnValidVillagePlacements(Board board){
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        for(int ii=0; ii<placedHexes.size(); ii++){
            PlacedHex currentHex = placedHexes.get(ii);
            if(currentHex.isEmpty() && currentHex.getHeight()==1 && currentHex.isNotVolcano())
                listOfValidPlacements.add(currentHex.getLocation());
        }
        return listOfValidPlacements;
    }

    public static ArrayList<Location> returnValidVillageExpansions(Player player, Board board){
        //ToDo Whoever made the settlement class, HELP ME!! :P
        return null;
    }

    public static ArrayList<Location> returnValidTotoroPlacments(Player player, Board board){
        //ToDo
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();

        return null;
    }

    public static ArrayList<Location> returnValidTigerPlacements(Player player, Board board){
        //ToDo
        return null;
    }
}

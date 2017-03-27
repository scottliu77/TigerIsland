package com.tigerisland.game;

import javax.print.attribute.standard.Chromaticity;
import java.util.ArrayList;

public class TextGUI {

    private ArrayList<Location> placedHexLocations;
    private ArrayList<Location> edgeSpaces;
    private ArrayList<Hex> placedHexTiles;

    private int leftMost;
    private int rightMost;
    private int bottomMost;
    private int topMost;

    private int xWidth;
    private int yHeight;

    private int xDimensionMax;
    private int yDimensionMax;
    private char[][] map;

    public static void printPlacedHexLocations(ArrayList<Location> placedHexLocations) {
        System.out.println("HexLocations:");
        Location.printLocations(placedHexLocations);
        System.out.println();
    }

    public static void printPlacedHexTiles(ArrayList<Hex> placedHexTiles) {
        System.out.println("HexDescription");
        Hex.printHexes(placedHexTiles);
        System.out.println();
    }

    public static void printEdgeSpaces(ArrayList<Location> edgeSpaces) {
        System.out.println("OpenEdges");
        Location.printLocations(edgeSpaces);
        System.out.println();
    }

    public static void printMap(Board board){
        ArrayList<Location> placedHexLocations = board.locationsOfPlacedHexes();
        ArrayList<Location> edgeSpaces = board.edgeSpaces;
        ArrayList<Hex> placedHexTiles = board.hexesOfPlacedHexes();
        TextGUI map = new TextGUI(placedHexLocations, edgeSpaces, placedHexTiles);
        map.constructMap();
    }

    public TextGUI(ArrayList<Location> placedHexLocations, ArrayList<Location> edgeSpaces, ArrayList<Hex> placedHexTiles){
        this.placedHexLocations = placedHexLocations;
        this.edgeSpaces = edgeSpaces;
        this.placedHexTiles = placedHexTiles;
    }

    public void constructMap(){
        initializeMap();
        constructHexagons();
        addDetails();
        display();
    }

    private void initializeMap(){
        calculateLocationBounds();

        xWidth = rightMost - leftMost + 1;
        yHeight = topMost - bottomMost + 1;

        xDimensionMax = 6 + 3*xWidth;
        yDimensionMax = 4 + 5*yHeight;
        map = new char[xDimensionMax][yDimensionMax];
    }

    private void calculateLocationBounds(){
        for(int ii=0; ii<edgeSpaces.size(); ii++) {
            int x = edgeSpaces.get(ii).x;
            int y = edgeSpaces.get(ii).y;

            bottomMost = (y < bottomMost) ? (y) : (bottomMost);
            topMost = (y > topMost) ? (y) : (topMost);

            int xPos = 2 * x + y;
            leftMost = (xPos < leftMost) ? (xPos) : (leftMost);
            rightMost = (xPos > rightMost) ? (xPos) : (rightMost);
        }
    }

    private void constructHexagons(){
        for(int row=yDimensionMax-1; row>=0; row--){
            int rowChoice = (row + (5*bottomMost)%10 + 10)%10;
            for(int col=0; col<xDimensionMax; col++){
                int colChoice = (col + (3*leftMost)%6 + 6)%6;
                switch(colChoice){
                    case 0:
                        switch(rowChoice){
                            case 2:
                                map[col][row]='/';
                                break;
                            case 6:
                                map[col][row]='\\';
                                break;
                        }
                        break;
                    case 1:
                        switch(rowChoice){
                            case 3:
                            case 4:
                            case 5:
                                map[col][row]='|';
                                break;
                        }
                        break;
                    case 2:
                        switch(rowChoice){
                            case 2:
                                map[col][row]='\\';
                                break;
                            case 6:
                                map[col][row]='/';
                                break;
                        }
                        break;
                    case 3:
                        switch(rowChoice){
                            case 1:
                                map[col][row]='\\';
                                break;
                            case 7:
                                map[col][row]='/';
                                break;
                        }
                        break;
                    case 4:
                        switch(rowChoice){
                            case 9:
                            case 0:
                            case 8:
                                map[col][row]='|';
                                break;
                        }
                        break;
                    case 5:
                        switch(rowChoice){
                            case 1:
                                map[col][row]='/';
                                break;
                            case 7:
                                map[col][row]='\\';
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void addDetails(){
        markZeroLocation();
        placeHexesOnMap();
        placeEdgesOnMap();
    }

    private void markZeroLocation(){
        int centerX = 4 + (-leftMost)*3;
        int centerY = 4 + (topMost)*5;

        map[centerX-1][(yDimensionMax-1)-(centerY+2)] = '0';
        map[centerX][(yDimensionMax-1)-(centerY+2)] = ',';
        map[centerX+1][(yDimensionMax-1)-(centerY+2)] = '0';

        map[centerX-1][(yDimensionMax-1)-(centerY-2)] = '0';
        map[centerX][(yDimensionMax-1)-(centerY-2)] = ',';
        map[centerX+1][(yDimensionMax-1)-(centerY-2)] = '0';
    }

    private void placeHexesOnMap(){
        for(int ii=0; ii<placedHexTiles.size(); ii++){
            Hex hex = placedHexTiles.get(ii);
            int x = placedHexLocations.get(ii).x;
            int y = placedHexLocations.get(ii).y;

            int centerX = 4 + (x*2-leftMost)*3 + y*3;
            int centerY = 4 + (topMost-y)*5;

            char hexTerrain = hex.getHexTerrain().getType().charAt(0);
            map[centerX-1][(yDimensionMax-1)-(centerY-1)] = hexTerrain;
            map[centerX][(yDimensionMax-1)-(centerY-1)] = '-';
            char hexHeight = Integer.toString(hex.getHeight()).charAt(0);
            map[centerX+1][(yDimensionMax-1)-(centerY-1)] = hexHeight;

            String hexIDshort = hex.getIDFirstChars(2);
            map[centerX-2][(yDimensionMax-1)-(centerY)] = 'I';
            map[centerX-1][(yDimensionMax-1)-(centerY)] = 'D';
            map[centerX][(yDimensionMax-1)-(centerY)] = '=';
            map[centerX+1][(yDimensionMax-1)-(centerY)] = hexIDshort.charAt(0);
            map[centerX+2][(yDimensionMax-1)-(centerY)] = hexIDshort.charAt(1);

            try {
                char hexColor = hex.getPieceColor().getColorString().charAt(0);
                map[centerX-2][(yDimensionMax-1)-(centerY+1)] = hexColor;
                map[centerX-1][(yDimensionMax-1)-(centerY+1)] = '-';
            } catch (NullPointerException exception) {
                // do nothing
            }

            char hexContentType = hex.getPieceType().charAt(0);
            map[centerX][(yDimensionMax-1)-(centerY+1)] = hexContentType;
            map[centerX+1][(yDimensionMax-1)-(centerY+1)] = '-';
            char hexContentCount = Integer.toString(hex.getPieceCount()).charAt(0);
            map[centerX+2][(yDimensionMax-1)-(centerY+1)] = hexContentCount;

        }
    }

    private void placeEdgesOnMap(){
        for(int ii=0; ii<edgeSpaces.size(); ii++) {
            int x = edgeSpaces.get(ii).x;
            int y = edgeSpaces.get(ii).y;

            int centerX = 4 + (x * 2 - leftMost) * 3 + y * 3;
            int centerY = 4 + (topMost - y) * 5;

            map[centerX][(yDimensionMax-1)-(centerY)] = '*';
            map[centerX+1][(yDimensionMax-1)-(centerY)] = '*';
            map[centerX-1][(yDimensionMax-1)-(centerY)] = '*';
            map[centerX][(yDimensionMax-1)-(centerY+1)] = '*';
            map[centerX][(yDimensionMax-1)-(centerY-1)] = '*';
        }
    }

    private void display(){
        for(int row=yDimensionMax-1; row>=0; row--){
            for(int col=0; col<xDimensionMax; col++){
                if(map[col][row] == 0)
                    map[col][row] = ' ';
                System.out.print(map[col][row]);
            }
            System.out.println();
        }
    }

}

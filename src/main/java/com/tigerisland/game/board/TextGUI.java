package com.tigerisland.game.board;

import java.util.ArrayList;

public class TextGUI {

    private ArrayList<PlacedHex> placedHexes;
    private ArrayList<Location> edgeSpaces;

    private int leftMost;
    private int rightMost;
    private int bottomMost;
    private int topMost;

    private int xWidth;
    private int yHeight;

    private int xDimensionMax;
    private int yDimensionMax;
    private char[][] map;

    public static void printPlacedHexes(ArrayList<PlacedHex> placedHexes) {
        System.out.println("HexLocations:");
        PlacedHex.printPlacedHexes(placedHexes);
        System.out.println();
    }

    public static void printEdgeSpaces(ArrayList<Location> edgeSpaces) {
        System.out.println("OpenEdges");
        Location.printLocations(edgeSpaces);
        System.out.println();
    }

    public static void printMap(Board board){
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        ArrayList<Location> edgeSpaces = board.edgeSpaces;
        TextGUI map = new TextGUI(placedHexes, edgeSpaces);
        map.constructMap();
    }

    public TextGUI(ArrayList<PlacedHex> placedHexes, ArrayList<Location> edgeSpaces){
        this.placedHexes = placedHexes;
        this.edgeSpaces = edgeSpaces;
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
        for(int ii=0; ii<placedHexes.size(); ii++){
            Hex hex = placedHexes.get(ii).getHex();
            int x = placedHexes.get(ii).getLocation().x;
            int y = placedHexes.get(ii).getLocation().y;

            int centerX = 4 + (x*2-leftMost)*3 + y*3;
            int centerY = 4 + (topMost-y)*5;

            char hexTerrain = hex.getHexTerrain().getTerrainString().charAt(0);
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

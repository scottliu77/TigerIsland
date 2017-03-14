import java.util.ArrayList;

public class TextGUI {

    private static int boardWidth = 21;
    private static int boardHeight = 21;

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

    public static void printMap(ArrayList<Location> placedHexLocations, ArrayList<Location> edgeSpaces, ArrayList<Hex> placedHexTiles){
        String[][] theBoard = new String[boardWidth][boardHeight];
        for(int row = -10; row <= 10; row++){
            for(int col = -10; col <= 10; col++){
                if(row % 2 == 0){
                    theBoard[col + 10][row + 10] = (col % 2 == 0)?("----------"):("          ");
                }
                else{
                    theBoard[col + 10][row + 10] = (col % 2 == 0)?("          "):("----------");
                }
            }
        }
        for(int ii = 0; ii < placedHexTiles.size(); ii++){
            Location loc = placedHexLocations.get(ii);
            int newX = loc.x * 2 + loc.y;
            int newY = loc.y;
            Hex hex = placedHexTiles.get(ii);
            String hexTerrain = Character.toString(hex.getHexTerrain().getType().charAt(0));
            String hexHeight = Integer.toString(hex.getHeight());
            String hexContentType = Character.toString(hex.getContentType().charAt(0));
            String hexContentCount = Integer.toString(hex.getContentCount());
            String hexIDshort = hex.getIDfirstChars(2);
            theBoard[newX + 10][10 - newY] = '<' + hexTerrain + '-' + hexHeight + '-' + hexContentType + hexContentCount + '-' + hexIDshort + '>';
        }
        for(int ii = 0; ii < edgeSpaces.size(); ii++){
            Location loc = edgeSpaces.get(ii);
            int newX = loc.x * 2 + loc.y;
            int newY = loc.y;
            theBoard[newX + 10][10 - newY] = "**********";
        }
        for(int row = 0; row <= boardHeight - 1; row++){
            for(int col = 0; col <= boardWidth - 1; col++){
                System.out.print(theBoard[col][row]);
            }
            System.out.println();
        }
    }
}

import java.util.ArrayList;

public class Board {

    Hex[][] boardMap;

    public Board() {
        this.boardMap = new Hex[100][100];
    }

    public void addTileToMap(int volcano_x, int volcano_y, int left_x, int left_y, int right_x, int right_y, Tile newTile) {

        // check valid methods here later

        this.addHexToMap(volcano_x, volcano_y, newTile.getVolcanoHex());
        this.addHexToMap(left_x, left_y, newTile.getLeftHex());
        this.addHexToMap(right_x, right_y, newTile.getRightHex());
    }

    public void addHexToMap(int x, int y, Hex hex) {

        this.boardMap[x][y] = hex;
    }

//    public Hex getHexAt() {
//
//    }
}

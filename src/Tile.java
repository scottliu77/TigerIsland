public class Tile {

    private Hex volcanoHex;
    private Hex leftHex;
    private Hex rightHex;

    public Tile(Terrain left, Terrain right) {
        this.volcanoHex = new Hex(Terrain.VOLCANO);
        this.leftHex = new Hex(left);
        this.rightHex = new Hex(right);
    }

    public Hex getVolcanoHex() {
        return volcanoHex;
    }

    public Hex getLeftHex() {
        return leftHex;
    }

    public Hex getRightHex() {
        return rightHex;
    }
}

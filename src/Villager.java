public class Villager {

    private VillagerColor color;

    public Villager(VillagerColor color) {
        this.color = color;
    }

    public Villager(int code) {
        switch (code) {
            case 0:
                this.color = VillagerColor.BLACK;
                break;
            case 1:
                this.color = VillagerColor.WHITE;
                break;
        }
    }

    public String getVillagerColor() {
        return this.color.getColor();
    }

    public int getVillagerCode() {
        return this.color.getCode();
    }
}

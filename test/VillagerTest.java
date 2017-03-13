import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VillagerTest {

    private Villager villagerWhite;
    private Villager villagerBlack;

    @Before
    public void createVillager() {
        this.villagerWhite = new Villager(VillagerColor.WHITE);
        this.villagerBlack = new Villager(VillagerColor.BLACK);
    }

    @Test
    public void testCanCreateVillager() {
        assertTrue(this.villagerWhite != null);
    }

    @Test
    public void testCanGetVillagerColor() {
        assertTrue(this.villagerWhite.getVillagerColor() == "White");
    }

    @Test
    public void testCanGetVillagerCode() {
        assertTrue(this.villagerWhite.getVillagerCode() == 1);
    }

    @Test
    public void testCanCreateVillagerWithCodeZero() {
        Villager villagerZero = new Villager(0);
        assertTrue(villagerZero != null);
    }

    @Test
    public void testCanCreateVillagerWithCodeOne() {
        Villager villagerOne = new Villager(1);
        assertTrue(villagerOne != null);
    }
}


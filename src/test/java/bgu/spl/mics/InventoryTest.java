package bgu.spl.mics;

import main.java.bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class InventoryTest {
    private Inventory inv;
    private String[] gadgets;

    @BeforeEach
    public void setUp(){
        inv = Inventory.getInstance();
        gadgets = new String[]{"sky hook", "space knife", "camera pen", "poisoned shoes"};
        inv.load(gadgets);
    }

    @Test
    public void test(){
        assertEquals(true, inv.getItem("sky hook"));
    }
    //sss
}

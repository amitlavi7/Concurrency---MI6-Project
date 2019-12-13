package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import com.sun.org.apache.xpath.internal.operations.String;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class InventoryTest {
    private Inventory inv;
    private String[] gadgets;

    @BeforeEach
    public void setUp() {
        inv = new Inventory();
        gadgets = new String[]{"sky hook", "space knife", "camera pen", "poisoned shoes"};
    }

    @Test
    public void test() {
        assertNotNull(inv);
        inv.load(gadgets);
        assertTrue(inv.getItem("sky hook"));
        assertFalse(inv.getItem("sword"));
    }

}

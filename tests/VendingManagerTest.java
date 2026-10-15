import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VendingManagerTest {

    @Test
    void managerHasFiveVendors() {
        // Create a manager where every vendor has 5 candy and 5 gum, numbered 1 to 5
        VendingManager manager = new VendingManager(5, 5);
        assertEquals(5, manager.getNumVendors());
        assertNotNull(manager.getVendor(1));
        assertNotNull(manager.getVendor(5));
        assertNull(manager.getVendor(0));
        assertNull(manager.getVendor(6));
    }

    @Test
    void managedVendorsAreIndependent() {
        VendingManager manager = new VendingManager(5, 5);
        // Buy a candy from vendor 1 only
        Vending first = manager.getVendor(1);
        first.addMoney(1.25);
        first.select("Candy");
        // Vendor 1 should change, vendor 2 should not
        assertEquals(4, manager.getVendor(1).getStock("Candy"));
        assertEquals(5, manager.getVendor(2).getStock("Candy"));
    }

    @Test
    void allInventoriesIncludesEveryVendor() {
        VendingManager manager = new VendingManager(5, 5);
        String output = manager.getAllInventories();
        // The output has heading for each of the 5 vendors
        for (int i = 1; i <= 5; i++) {
            assertTrue(output.contains("Vendor " + i + ":"));
        }
    }
}

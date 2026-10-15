import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {

    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }
    @Test
    void addMoneyIncreasesBalance() {
        Vending vendor = new Vending(5, 5);
        vendor.addMoney(1.00);
        assertEquals(1.00, vendor.getBalance(), 0.001);
    }
    @Test
    void buyItem() {
        Vending vendor = new Vending(5, 5);
        vendor.addMoney(2.00);
        vendor.select("Candy");
        // Stock should drop from 5 to 4
        assertEquals(4, vendor.getStock("Candy"));
        assertEquals(0.75, vendor.getBalance());
    }
    @Test
    void buyAllItems() {
        Vending vendor = new Vending(1, 1);
        vendor.addMoney(1.75);
        vendor.select("Candy");
        vendor.select("Gum");
        assertEquals(0, vendor.getStock("Candy"));
        assertEquals(0, vendor.getStock("Gum"));
        assertEquals(0.0, vendor.getBalance());
    }
    @Test
    void cannotBuyFromEmptyInventory() {
        Vending vendor = new Vending(0, 0);
        vendor.addMoney(2.00);
        vendor.select("Candy");
        assertEquals(0, vendor.getStock("Candy"));
        assertEquals(2.00, vendor.getBalance());
    }
    @Test
    void restockIncreasesStock(){
        Vending vendor = new Vending(2, 2);
        vendor.restock("Candy", 3);
        assertEquals(5, vendor.getStock("Candy"));
        assertEquals(2, vendor.getStock("Gum"));
    }
    @Test
    void buyItemAfterRestocking(){
        Vending vendor = new Vending(0, 0);
        vendor.restock("Gum", 1);
        vendor.addMoney(.50);
        vendor.select("Gum");
        assertEquals(0, vendor.getStock("Gum"));
        assertEquals(0.0, vendor.getBalance());
    }
    @Test
    void restockInvalidAmount() {
        Vending vendor = new Vending(2, 2);
        vendor.restock("Candy", 0);
        vendor.restock("Candy", -5);
        assertEquals(2, vendor.getStock("Candy"));
    }
}
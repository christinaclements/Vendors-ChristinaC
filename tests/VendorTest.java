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
}
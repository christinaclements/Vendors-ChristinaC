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
    @Test
    void vendorsHaveSeparateStocks() {
        Vending first = new Vending(5, 5);
        Vending second = new Vending(5, 5);
        first.addMoney(1.25);
        first.select("Candy");
        assertEquals(4, first.getStock("Candy"));
        assertEquals(5, second.getStock("Candy"));
    }
    @Test
    void restockingNewInventory() {
        Vending vendor = new Vending(5, 5);
        vendor.restock("Chips", 4, 1.00);
        assertEquals(4, vendor.getStock("Chips"));
        vendor.addMoney(1.00);
        vendor.select("Chips");
        assertEquals(3, vendor.getStock("Chips"));
        assertEquals(0.00, vendor.getBalance());
    }

    @Test
    void restockingExistingItemWithPrice() {
        Vending vendor = new Vending(5, 5);
        //try to change gum price fails
        vendor.restock("Gum", 2, 9.99);
        assertEquals(7, vendor.getStock("Gum"));
        vendor.addMoney(0.50);
        vendor.select("Gum");
        assertEquals(6, vendor.getStock("Gum"));
        assertEquals(0.0, vendor.getBalance());
    }

    @Test
    void newItemWithInvalidPrice() {
        Vending vendor = new Vending(5, 5);
        // Try to add new items with a zero and a negative price
        vendor.restock("Chips", 4, 0);
        vendor.restock("Soda", 4, -1.00);
        vendor.addMoney(5.00);
        vendor.select("Chips");
        vendor.select("Soda");
        assertEquals(0, vendor.getStock("Chips"));
        assertEquals(0, vendor.getStock("Soda"));
        assertEquals(5.00, vendor.getBalance());
    }
    @Test
    void renameItemKeepsStockAndPrice() {
        Vending vendor = new Vending(5, 5);
        vendor.renameItem("Gum", "Bubblegum");
        // The stock should move to the new name, and the old name should be gone
        assertEquals(5, vendor.getStock("Bubblegum"));
        assertEquals(0, vendor.getStock("Gum"));
        vendor.addMoney(0.50);
        vendor.select("Bubblegum");
        assertEquals(4, vendor.getStock("Bubblegum"));
        assertEquals(0.0, vendor.getBalance());
    }

    @Test
    void renameToExistingName() {
        Vending vendor = new Vending(5, 5);
        vendor.renameItem("Gum", "Candy");
        assertEquals(5, vendor.getStock("Candy"));
        assertEquals(5, vendor.getStock("Gum"));
    }

    @Test
    void renameWithInvalidItem() {
        Vending vendor = new Vending(5, 5);
        // Try to rename an item that doesn't exist
        vendor.renameItem("Chips", "Crisps");
        assertEquals(0, vendor.getStock("Crisps"));
        // Try to rename Gum to an empty name
        vendor.renameItem("Gum", "");
        assertEquals(5, vendor.getStock("Gum"));
    }
}
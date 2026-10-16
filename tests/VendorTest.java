import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void renameItem() {
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
    void renameWithInvalidName() {
        Vending vendor = new Vending(5, 5);
        // Try to rename an item that doesn't exist
        vendor.renameItem("Chips", "Crisps");
        assertEquals(0, vendor.getStock("Crisps"));
        // Try to rename Gum to an empty name
        vendor.renameItem("Gum", "");
        assertEquals(5, vendor.getStock("Gum"));
    }
    @Test
    void inventoryList() {
        Vending vendor = new Vending(5, 5);
        String inventory = vendor.getInventory();
        assertTrue(inventory.contains("Candy: 5 in stock, $1.25"));
        assertTrue(inventory.contains("Gum: 5 in stock, $0.50"));
    }
    @Test
    void removeItem() {
        Vending vendor = new Vending(5, 5);
        vendor.removeItem("Gum");
        assertFalse(vendor.getInventory().contains("Gum"));
        assertEquals(5, vendor.getStock("Candy"));
    }

    @Test
    void cannotBuyRemovedItem() {
        Vending vendor = new Vending(5, 5);
        vendor.removeItem("Candy");
        vendor.addMoney(2.00);
        vendor.select("Candy");
        assertEquals(2.00, vendor.getBalance());
    }

    @Test
    void removeUnknownItem() {
        Vending vendor = new Vending(5, 5);
        vendor.removeItem("Chips");
        assertEquals(5, vendor.getStock("Candy"));
        assertEquals(5, vendor.getStock("Gum"));
    }
    @Test
    void purchasesCountedPerItem() {
        Vending vendor = new Vending(5, 5);
        vendor.addMoney(5.00);
        vendor.select("Candy");
        vendor.select("Candy");
        vendor.select("Gum");
        assertEquals(2, vendor.getPurchaseCount("Candy"));
        assertEquals(1, vendor.getPurchaseCount("Gum"));
    }

    @Test
    void failedPurchasesNotCounted() {
        Vending vendor = new Vending(5, 0);
        vendor.addMoney(0.25);
        vendor.select("Candy");
        vendor.addMoney(1.00);
        vendor.select("Gum");
        assertEquals(0, vendor.getPurchaseCount("Candy"));
        assertEquals(0, vendor.getPurchaseCount("Gum"));
    }

    @Test
    void mostPopularItemHasMostPurchases() {
        Vending vendor = new Vending(5, 5);

        // Buy 3 gum and 1 candy
        vendor.addMoney(5.00);
        vendor.select("Gum");
        vendor.select("Gum");
        vendor.select("Gum");
        vendor.select("Candy");
        assertEquals("Gum", vendor.getMostPopularItem());
    }

    @Test
    void mostPopularItemBeforeAnyPurchases() {
        Vending vendor = new Vending(5, 5);
        assertEquals("None", vendor.getMostPopularItem());
    }

    @Test
    void purchaseCountRenamedItem() {
        Vending vendor = new Vending(5, 5);
        vendor.addMoney(1.00);
        vendor.select("Gum");
        vendor.select("Gum");
        vendor.renameItem("Gum", "Bubblegum");
        assertEquals(2, vendor.getPurchaseCount("Bubblegum"));
    }
    @Test
    void descriptionShowsInDetails() {
        Vending vendor = new Vending(5, 5);
        vendor.setDescription("Candy", "Chocolate bar with caramel");
        String details = vendor.getDetails("Candy");
        assertTrue(details.contains("Chocolate bar with caramel"));
        assertTrue(details.contains("$1.25"));
        assertTrue(details.contains("5 in stock"));
    }

    @Test
    void itemWithDefaultDescription() {
        Vending vendor = new Vending(5, 5);
        assertTrue(vendor.getDetails("Gum").contains("No description available"));
    }

    @Test
    void invalidDescriptionIgnored() {
        Vending vendor = new Vending(5, 5);
        vendor.setDescription("Gum", "Mint flavored");
        vendor.setDescription("Gum", "");
        assertTrue(vendor.getDetails("Gum").contains("Mint flavored"));
        assertEquals("Sorry, don't know that item", vendor.getDetails("Chips"));
    }

    @Test
    void discountLowersPrice() {
        Vending vendor = new Vending(5, 5);
        vendor.applyDiscount("Candy", 20);
        assertEquals(1.00, vendor.getPrice("Candy"), 0.001);
        assertEquals(0.50, vendor.getPrice("Gum"), 0.001);
    }

    @Test
    void buyAtDiscountedPrice() {
        Vending vendor = new Vending(5, 5);
        vendor.applyDiscount("Candy", 20);
        vendor.addMoney(1.00);
        vendor.select("Candy");
        assertEquals(4, vendor.getStock("Candy"));
        assertEquals(0.0, vendor.getBalance());
    }

    @Test
    void removeDiscount() {
        Vending vendor = new Vending(5, 5);
        vendor.applyDiscount("Candy", 20);
        vendor.removeDiscount("Candy");
        assertEquals(1.25, vendor.getPrice("Candy"));
    }

    @Test
    void categoryDiscountApplies() {
        Vending vendor = new Vending(5, 5);
        vendor.restock("Chips", 5, 1.00);
        vendor.setCategory("Candy", "Snacks");
        vendor.setCategory("Chips", "Snacks");
        vendor.applyCategoryDiscount("Snacks", 50);
        assertEquals(0.625, vendor.getPrice("Candy"));
        assertEquals(0.50, vendor.getPrice("Chips"));
        assertEquals(0.50, vendor.getPrice("Gum"));
        vendor.removeCategoryDiscount("Snacks");
        assertEquals(1.25, vendor.getPrice("Candy"));
        assertEquals(1.00, vendor.getPrice("Chips"));
    }

    @Test
    void invalidDiscountIsIgnored() {
        Vending vendor = new Vending(5, 5);
        vendor.applyDiscount("Candy", 0);
        vendor.applyDiscount("Candy", -10);
        vendor.applyDiscount("Candy", 150);
        assertEquals(1.25, vendor.getPrice("Candy"));
    }


}
import java.util.HashMap;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private HashMap<String, Item> Stock = new HashMap<String,Item>();
    private double balance;

    Vending(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
        this.balance = 0;
    }

    /** resets the Balance to 0 */
    void resetBalance () {
        this.balance = 0;
    }

    /** returns the current balance */
    double getBalance () {
        return this.balance;
    }

    /** adds money to the machine's balance
     * @param amt how much money to add
     * */
    void addMoney (double amt) {
        this.balance = this.balance + amt;
    }

    /** attempt to purchase named item.  Message returned if
     * the balance isn't sufficient to cover the item cost.
     *
     * @param name The name of the item to purchase ("Candy" or "Gum")
     */
    void select (String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            if (item.stock <= 0){
                //checks item is in stock
                System.out.println("Sorry, that item is out of stock");
            }
            else if (balance >= item.getCurrentPrice()) {
                item.purchase(1);
                this.balance = this.balance - item.getCurrentPrice();
            }
            else
                System.out.println("Gimme more money");
        }
        else System.out.println("Sorry, don't know that item");
    }
    /** returns how many of the named item are in stock, or 0 if the item doesn't exist
     * @param name The name of the item ("Candy" or "Gum")
     */
    int getStock(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            return item.stock;
        }
        else {
            return 0;
        }
    }
    /** adds more of an existing item to the machine's stock
     * @param name The name of the item to restock
     * @param amount How many pieces to add (must be positive)
     */
    void restock(String name, int amount) {
        if (amount <= 0) {
            System.out.println("Restock amount must be positive");
        }
        else if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            item.restock(amount);
        }
        else {
            System.out.println("Sorry, don't know that item");
        }
    }
    /** restocks an item, adding it to the machine as a new item if it isn't there yet
     * @param name The name of the item
     * @param amount How many pieces to add (must be positive)
     * @param price The price to use if this is a new item (must be positive)
     */
    void restock(String name, int amount, double price) {
        if (Stock.containsKey(name)) {
            // Item already exists, so just add stock and keep its current price
            restock(name, amount);
        }
        else if (amount <= 0 || price <= 0) {
            System.out.println("Amount and price must be positive");
        }
        else {
            // Item is new, so create it with the given price and amount
            Stock.put(name, new Item(price, amount));
        }
    }
    /** changes the name of an item, keeping its price and stock
     * @param oldName The item's current name
     * @param newName The name to change it to
     */
    void renameItem(String oldName, String newName) {
        if (!Stock.containsKey(oldName)) {
            System.out.println("Sorry, don't know that item");
        }
        else if (newName == null || newName.isEmpty()) {
            System.out.println("New name can't be empty");
        }
        else if (Stock.containsKey(newName)) {
            System.out.println("An item with that name already exists");
        }
        else {
            // Take the item out under its old name, amd put it back under the new name
            Item item = Stock.remove(oldName);
            Stock.put(newName, item);
        }
    }

    /** removes an item from the machine
     * @param name The name of the item to remove
     */
    void removeItem(String name) {
        if (Stock.containsKey(name)) {
            Stock.remove(name);
        }
        else {
            System.out.println("Sorry, don't know that item");
        }
    }

    /** returns how many times the named item has been purchased, or 0 if the item doesn't exist
     * @param name The name of the item
     */
    int getPurchaseCount(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            return item.timesPurchased;
        }
        else {
            return 0;
        }
    }

    /** returns the name of the item purchased the most, or "None" if nothing has been bought yet */
    String getMostPopularItem() {
        String mostPopular = "None";
        int highestCount = 0;
        for (String name : Stock.keySet()) {
            Item item = Stock.get(name);
            // Keep track of the item with the highest count seen so far
            if (item.timesPurchased > highestCount) {
                highestCount = item.timesPurchased;
                mostPopular = name;
            }
        }
        return mostPopular;
    }

    /** sets the description of an item
     * @param name The name of the item
     * @param description What the item is, its benefits, or its uses
     */
    void setDescription(String name, String description){
        if (!Stock.containsKey(name)){
            System.out.println("Sorry, don't know that item");
        }
        else if (description == null || description.isEmpty()){
            System.out.println("Description can't be empty");
        }
        else {
            Item item = Stock.get(name);
            item.description = description;
        }
    }

    /** returns the details of an item (price, stock, and description) so a customer can check before buying
     * @param name The name of the item
     */
    String getDetails(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            return name + " - $" + String.format("%.2f", item.getCurrentPrice()) + ", "
                    + item.stock + " in stock. " + item.description;
            //String format so it displays nice as $0.50
        }
        else {
            return "Sorry, don't know that item";
        }
    }

    /** returns the current price of an item (after any discount), or 0 if the item doesn't exist
     * @param name The name of the item
     */
    double getPrice(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            return item.getCurrentPrice();
        }
        else {
            return 0;
        }
    }

    /** sets the category of an item, such as "Snacks" or "Drinks"
     * @param name The name of the item
     * @param category The category to put it in
     */
    void setCategory(String name, String category) {
        if (!Stock.containsKey(name)) {
            System.out.println("Sorry, don't know that item");
        }
        else if (category == null || category.isEmpty()) {
            System.out.println("Category can't be empty");
        }
        else {
            Item item = Stock.get(name);
            item.category = category;
        }
    }

    /** checks whether a discount percent is valid (more than 0 and less than 100) */
    private boolean isValidDiscount(double percent) {
        if (percent > 0 && percent < 100) {
            return true;
        }
        else {
            System.out.println("Discount must be between 0 and 100 percent");
            return false;
        }
    }

    /** puts one item on sale
     * @param name The name of the item
     * @param percent How much to take off, for example 20 for 20% off
     */
    void applyDiscount(String name, double percent) {
        if (!Stock.containsKey(name)) {
            System.out.println("Sorry, don't know that item");
        }
        else if (isValidDiscount(percent)) {
            Item item = Stock.get(name);
            item.discountPercent = percent;
        }
    }

    /** ends the sale on one item, returning it to its original price
     * @param name The name of the item
     */
    void removeDiscount(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            item.discountPercent = 0;
        }
        else {
            System.out.println("Sorry, don't know that item");
        }
    }

    /** puts every item in a category on sale
     * @param category The category to discount
     * @param percent How much to take off, for example 20 for 20% off
     */
    void applyCategoryDiscount(String category, double percent) {
        if (isValidDiscount(percent)) {
            for (String name : Stock.keySet()) {
                Item item = Stock.get(name);
                if (item.category.equals(category)) {
                    item.discountPercent = percent;
                }
            }
        }
    }

    /** ends the sale on every item in a category
     * @param category The category to stop discounting
     */
    void removeCategoryDiscount(String category) {
        for (String name : Stock.keySet()) {
            Item item = Stock.get(name);
            if (item.category.equals(category)) {
                item.discountPercent = 0;
            }
        }
    }

    /** builds a text listing of every item, how many are in stock, and its price */
    String getInventory() {
        String result = "";
        for (String name : Stock.keySet()) {
            Item item = Stock.get(name);
            result = result + name + ": " + item.stock + " in stock, $"
                    + String.format("%.2f", item.getCurrentPrice()) + "\n";
            //String format so it displays nice as $0.50
        }
        return result;
    }

    /** prints this machine's inventory to the console */
    void printInventory() {
        System.out.print(getInventory());
    }
}

class Examples {
}


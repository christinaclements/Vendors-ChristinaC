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
            else if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
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
}

class Examples {
}


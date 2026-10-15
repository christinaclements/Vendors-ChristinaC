import java.util.ArrayList;

/**
 * Manages a group of vending machines so they can be accessed and printed together.
 */
class VendingManager {
    private static final int NUM_VENDORS = 5;
    private ArrayList<Vending> vendors = new ArrayList<Vending>();

    /** creates 5 vending machines, each stocked with the given amounts of candy and gum */
    VendingManager(int numCandy, int numGum) {
        for (int i = 0; i < NUM_VENDORS; i++) {
            vendors.add(new Vending(numCandy, numGum));
        }
    }

    /** returns how many vendors are being managed */
    int getNumVendors() {
        return vendors.size();
    }

    /** returns the vendor with the given number (1 to 5), or null if the number is invalid
     * @param number The vendor's number, starting at 1
     */
    Vending getVendor(int number) {
        if (number < 1 || number > vendors.size()) {
            return null;
        }
        else {
            return vendors.get(number - 1);
        }
    }

    /** builds a text listing of every vendor's inventory */
    String getAllInventories() {
        String result = "";
        for (int i = 0; i < vendors.size(); i++) {
            result = result + "Vendor " + (i + 1) + ":\n";
            result = result + vendors.get(i).getInventory();
        }
        return result;
    }

    /** prints every vendor's inventory to the console */
    void printAllInventories() {
        System.out.print(getAllInventories());
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Ultra Shop!");
        System.out.println("Maintained by Christina Clements");


        VendingManager manager = new VendingManager(5, 5);
        manager.printAllInventories();

    }
}
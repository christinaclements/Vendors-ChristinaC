class Item {
    double price;
    int stock;
    int timesPurchased;
    String description;

    Item(double price, int numPieces) {
        this.price = price;
        this.stock = numPieces;
        this.timesPurchased = 0;
        this.description = "No description available";
    }

    void restock(int amount) {
        this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        this.stock = this.stock - amount;
        this.timesPurchased = this.timesPurchased + amount;
    }
}
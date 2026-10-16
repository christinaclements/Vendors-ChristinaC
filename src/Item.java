class Item {
    double price;
    int stock;
    int timesPurchased;
    String description;
    String category;
    double discountPercent;

    Item(double price, int numPieces) {
        this.price = price;
        this.stock = numPieces;
        this.timesPurchased = 0;
        this.description = "No description available";
        this.category = "Uncategorized";
        this.discountPercent = 0;
    }

    double getCurrentPrice(){
        return this.price * (100 - this.discountPercent) / 100;
    }

    void restock(int amount) {
        this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        this.stock = this.stock - amount;
        this.timesPurchased = this.timesPurchased + amount;
    }
}
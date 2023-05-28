//Base class for all products the store will sell
public abstract class Product {
    private double price;
    private int stockQuantity;
    private int cartQuantity; // amount of products in the cart awaiting purchase
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        cartQuantity = 0;
    }

    // I didn't realize there was a pre-given function for this, so I made my own
    // Because of simplicity I removed the pre-given one from this code
    public void sellProduct() {
        stockQuantity -= cartQuantity;
        soldQuantity += cartQuantity;
        cartQuantity = 0;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public int getSoldQuantity() {
        return soldQuantity;
    }
    public double getPrice() {
        return price;
    }
    public int getCartQuantity() {
        return cartQuantity;
    }
    public void changeCartQuantity(int integer) {
        // the integer can be used to increment/decrement the cartQuantity.
        // if the int is -1, it will decrement, if it is +1, it will increment.
        cartQuantity += integer;
    }

}
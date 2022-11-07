package Assignment8;

import java.io.Serializable;

public class InvTextbook implements Serializable {

    private final int sku;
    private final String title;
    private final double price;
    private final int quantity;

    public InvTextbook(int sku, String title, double price, int quantity) {
        this.sku = sku;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public int getSku() {
        return sku;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

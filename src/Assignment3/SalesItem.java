package Assignment3;

public class SalesItem {

    private String name;
    private double price; // per item
    private int quantity;

    public SalesItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getItemTotal() {
        return price * quantity;
    }

    public String toString() {
        return name + "\t" + MoneyUtil.costToString(getItemTotal()) + "\t" + quantity;
    }
}

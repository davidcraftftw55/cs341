package Assignment3;

import java.util.ArrayList;

public class SalesSlip {

    private ArrayList<SalesItem> salesList;

    public SalesSlip() {
        salesList = new ArrayList<>();
    }

    public void addItem(SalesItem item) {
        salesList.add(item);
    }

    public double getTotal() {
        double total = 0.0;
        for (SalesItem item : salesList) {
            total += item.getItemTotal();
        }
        return total;
    }

    public String toString() {
        if (salesList.isEmpty()) {
            return "";
        }
        StringBuilder output = new StringBuilder(salesList.get(0).toString());
        for (int i = 1; i < salesList.size(); i++) {
            output.append("\n").append(salesList.get(i));
        }
        return output.toString();
    }
}

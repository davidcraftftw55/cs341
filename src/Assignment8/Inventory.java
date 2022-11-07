package Assignment8;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {

    private final Map<Integer, InvTextbook> inventory;

    @SuppressWarnings("unchecked")
    public Inventory() {
        Map<Integer, InvTextbook> tempInventory;
        try {
            tempInventory = (Map<Integer, InvTextbook>)
                    new ObjectInputStream(new FileInputStream("inventory.ser")).readObject();
        } catch (Exception e) {
            tempInventory = new HashMap<>();
        }
        inventory = tempInventory;
    }

    public void addTextbook(int sku, String title, double price, int quantity) {
        inventory.put(sku, new InvTextbook(sku, title, price, quantity));
        saveInventory();
    }

    public boolean removeTextbook(int sku) {
        if (inventory.containsKey(sku)) {
            inventory.remove(sku);
            saveInventory();
            return true;
        } else {
            return false;
        }
    }

    public String getTextbookInfo(int sku) {
        if (inventory.containsKey(sku)) {
            InvTextbook textbook = inventory.get(sku);
            return "     Sku: " + textbook.getSku() + "\n" +
                    "   Title: " + textbook.getTitle() + "\n" +
                    "   Price: " + textbook.getPrice() + "\n" +
                    "Quantity: " + textbook.getQuantity();
        } else {
            return null;
        }
    }

    public String getInvTable() {
        final String spacing = "\t";
        StringBuilder output =
                new StringBuilder("Sku " + spacing + " Price " + spacing + " Quantity " + spacing + " Title\n");
        for (Map.Entry<Integer, InvTextbook> entry : inventory.entrySet()) {
            InvTextbook textbook = entry.getValue();
            output.append(textbook.getSku()).append(spacing).append(textbook.getPrice()).append(spacing)
                    .append(textbook.getQuantity()).append(spacing).append(textbook.getTitle()).append("\n");
        }
        return output.toString();
    }

    private void saveInventory() {
        try {
            new ObjectOutputStream(new FileOutputStream("inventory.ser")).writeObject(inventory);
        } catch (Exception e) {
            System.exit(1);
        }
    }
}

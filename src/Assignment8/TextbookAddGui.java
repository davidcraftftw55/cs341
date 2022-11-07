package Assignment8;

import javax.swing.*;

public class TextbookAddGui {
    private JPanel panel;
    private JTextField skuField;
    private JTextField titleField;
    private JTextField priceField;
    private JTextField quantityField;
    private JButton addButton;
    private JLabel outputLabel;

    public TextbookAddGui(Inventory inventory) {
        addButton.addActionListener(event -> {
            int sku;
            String title;
            double price;
            int quantity;

            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                outputLabel.setText("Quantity needs to be an integer");
                return;
            }
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                outputLabel.setText("Price needs to be a number");
                return;
            }
            title = titleField.getText();
            try {
                sku = Integer.parseInt(skuField.getText());
            } catch (NumberFormatException e) {
                outputLabel.setText("SKU needs to be an integer");
                return;
            }

            if (price > 0) {
                if (quantity > 0) {
                    inventory.addTextbook(sku, title, price, quantity);
                    outputLabel.setText("Textbook added successfully");
                } else {
                    outputLabel.setText("Quantity must be positive");
                }
            } else {
                outputLabel.setText("Price must be positive");
            }
        });
    }

    public static void open(Inventory inventory) {
        JFrame frame = new JFrame("Textbook Registration");
        frame.setContentPane(new TextbookAddGui(inventory).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

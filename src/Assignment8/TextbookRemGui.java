package Assignment8;

import javax.swing.*;

public class TextbookRemGui {
    private JPanel panel;
    private JTextField skuField;
    private JButton removeButton;
    private JLabel outputLabel;

    public TextbookRemGui(Inventory inventory) {
        removeButton.addActionListener(event -> {
            try {
                int sku = Integer.parseInt(skuField.getText());
                if (inventory.removeTextbook(sku)) {
                    outputLabel.setText("Textbook removed successfully");
                } else {
                    outputLabel.setText("Textbook not found in inventory");
                }
            } catch (NumberFormatException e) {
                outputLabel.setText("SKU must be an integer");
            }
        });
    }

    public static void open(Inventory inventory) {
        JFrame frame = new JFrame("TextbookRemGui");
        frame.setContentPane(new TextbookRemGui(inventory).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

package Assignment8;

import javax.swing.*;

public class TextbookInfoGui {
    private JPanel panel;
    private JTextField skuField;
    private JButton loadButton;
    private JTextPane outputPane;

    public TextbookInfoGui(Inventory inventory) {
        loadButton.addActionListener(event -> {
            try {
                int sku = Integer.parseInt(skuField.getText());
                String textbookInfo = inventory.getTextbookInfo(sku);
                outputPane.setText(textbookInfo != null ? textbookInfo : "Textbook not found, enter a different SKU");
            } catch (NumberFormatException e) {
                outputPane.setText("SKU must be an integer");
            }
        });
    }

    public static void open(Inventory inventory) {
        JFrame frame = new JFrame("TextbookInfoGui");
        frame.setContentPane(new TextbookInfoGui(inventory).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

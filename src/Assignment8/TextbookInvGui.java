package Assignment8;

import javax.swing.*;

public class TextbookInvGui {
    private JPanel panel;
    private JButton addTextbookButton;
    private JButton removeTextbookButton;
    private JButton textbookInfoButton;
    private JButton inventoryTableButton;

    private final Inventory inventory;

    public TextbookInvGui() {
        inventory = new Inventory();

        addTextbookButton.addActionListener(e -> {
            TextbookAddGui.open(inventory);
        });

        removeTextbookButton.addActionListener(e -> {
            TextbookRemGui.open(inventory);
        });

        textbookInfoButton.addActionListener(e -> {
            TextbookInfoGui.open(inventory);
        });

        inventoryTableButton.addActionListener(e -> {
            TextbookTableGui.open(inventory);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Textbook Inventory");
        frame.setContentPane(new TextbookInvGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

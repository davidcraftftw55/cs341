package Assignment8;

import javax.swing.*;

public class TextbookTableGui {
    private JPanel panel;
    private JButton refreshButton;
    private JTextArea outputPane;

    private final Inventory inventory;

    public TextbookTableGui(Inventory inventory) {
        this.inventory = inventory;
        updateTable();
        refreshButton.addActionListener(event -> updateTable());
    }

    private void updateTable() {
        outputPane.setText(inventory.getInvTable());
    }

    public static void open(Inventory inventory) {
        JFrame frame = new JFrame("TextbookTableGui");
        frame.setContentPane(new TextbookTableGui(inventory).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

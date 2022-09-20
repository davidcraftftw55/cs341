package Assignment3;

import javax.swing.*;
import java.awt.*;

public class SalesApplication {
    private JPanel panel;
    private JButton addItemButton;
    private JTextField fieldName;
    private JTextField fieldCost;
    private JTextField fieldQuantity;
    private JTextPane outputSalesList;
    private JTextField outputTotal;

    private SalesSlip salesSlip;

    public SalesApplication() {
        salesSlip = new SalesSlip();
        addItemButton.addActionListener(e -> {
            salesSlip.addItem(new SalesItem(fieldName.getText(), Double.parseDouble(fieldCost.getText()),
                    Integer.parseInt(fieldQuantity.getText())));

            outputSalesList.setText(salesSlip.toString());
            outputTotal.setText(MoneyUtil.costToString(salesSlip.getTotal()));
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}

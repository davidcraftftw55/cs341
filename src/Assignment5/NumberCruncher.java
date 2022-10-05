package Assignment5;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NumberCruncher {

    private static JFrame frame;
    private JPanel panel;
    private JButton pickFileButton;
    private JButton calculateButton;
    private JTextPane outputPane;
    private JLabel fileLabel;

    private File file;

    public NumberCruncher() {
        file = null;

        pickFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileLabel.setText(file.getAbsolutePath());
                frame.setSize((int) fileLabel.getPreferredSize().getWidth() + 20,
                        (int) panel.getPreferredSize().getHeight() + 30);
            }
        });

        calculateButton.addActionListener(event -> {
            if (file == null) {
                return;
            }
            try {
                // get input from file
                LinkedNumNode list = new LinkedNumNode();
                Scanner input = new Scanner(file);
                if (!input.hasNext()) {
                    outputPane.setText("File is empty");
                }
                list.num = Double.parseDouble(input.next());
                while (input.hasNext()) {
                    list.addNode(Double.parseDouble(input.next()));
                }
                int size = list.size();

                // calculate mean
                double mean = 0;
                LinkedNumNode iterator = list;
                while (iterator != null) {
                    mean += iterator.num;
                    iterator = iterator.next;
                }
                mean /= size;

                // calculate standard deviation
                double standardDeviation = 0;
                iterator = list;
                while (iterator != null) {
                    standardDeviation += Math.pow(iterator.num - mean, 2);
                    iterator = iterator.next;
                }
                standardDeviation /= size;
                standardDeviation = Math.sqrt(standardDeviation);

                // display output
                outputPane.setText(
                        "\tMean:\t" + mean + "\n" +
                        "Standard Deviation:\t" + standardDeviation);
            } catch (FileNotFoundException e) {
                outputPane.setText("FileNotFoundException");
            } catch (NumberFormatException e) {
                outputPane.setText("File contains non-numbers");
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("NumberCruncher");
        frame.setContentPane(new NumberCruncher().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

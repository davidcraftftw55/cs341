package Assignment7;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KeywordCounter {

    private JPanel panel;
    private JButton codeFileButton;
    private JLabel codeFileLabel;
    private JButton keywordFileButton;
    private JLabel keywordFileLabel;
    private JButton countButton;
    private JTextPane outputPane;

    private File codeFile;
    private File keywordFile;

    public KeywordCounter() {
        codeFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                codeFile = fileChooser.getSelectedFile();
                codeFileLabel.setText(codeFile.getName());
            }
        });

        keywordFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                keywordFile = fileChooser.getSelectedFile();
                keywordFileLabel.setText(keywordFile.getName());
            }
        });

        countButton.addActionListener(e -> {
            if (codeFile != null && keywordFile != null) {
                outputPane.setText(countWords());
            } else if (codeFile == null) {
                outputPane.setText("Code File needed");
            } else {
                outputPane.setText("Keyword File needed");
            }
        });
    }

    private String countWords() {
        Vector<String> words = new Vector<>();
        try {
            Scanner scanner = new Scanner(keywordFile);
            scanner.useDelimiter("[^a-zA-Z0-9$_]");
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return "keywordFile caused FileNotFoundException";
        }
        int mod = words.size();

        Map<Keyword, Integer> wordCounter = new HashMap<>();
        for (String word : words) {
            wordCounter.put(new Keyword(word, words.size()), 0);
        }
        int codeLineCounter = 0;

        try {
            long time = System.currentTimeMillis();
            Scanner scanner = new Scanner(codeFile);
            while (scanner.hasNextLine()) {
                String line = trimLine(scanner.nextLine());
                if (!line.isEmpty()) {
                    codeLineCounter++;

                    Scanner wordScanner = new Scanner(line);
                    wordScanner.useDelimiter("[^a-zA-Z0-9_$]");
                    while (wordScanner.hasNext()) {
                        String word = wordScanner.next();
                        if (!word.isEmpty()) {
                            Keyword keyword = new Keyword(word, mod);
                            Integer count = wordCounter.get(keyword);
                            if (count != null) {
                                wordCounter.put(keyword, count + 1);
                            }

                        }
                    }
                }
            }
            time = System.currentTimeMillis() - time;

            StringBuilder output = new StringBuilder();
            output.append("Code File: ").append(codeFile.getName()).append("\n");
            output.append("Keyword File: ").append(keywordFile.getName()).append("\n");
            output.append("Num of code lines: ").append(codeLineCounter).append("\n");
            output.append("Time to completion: ").append(time).append(" milliseconds").append("\n\n");

            output.append("Keywords:");
            for (Map.Entry<Keyword, Integer> keyword : wordCounter.entrySet()) {
                output.append("\n\t").append(keyword.getKey().getWord()).append(": ").append(keyword.getValue());
            }

            return output.toString();
        } catch (FileNotFoundException e) {
            return "codeFile caused FileNotFoundException";
        }
    }

    private boolean inQuote;
    private boolean inMultiLineComment;
    private String trimLine(String line) {
        // cut string literals out of line so that they don't count as keywords or operators
        // seems arbitrary, but remove "\\" so future lines don't error
        while (line.contains("\\\\")) {
            int index = line.indexOf("\\\\");
            line = line.substring(0, index) + line.substring(index + 2);
        }
        // first remove any quotes that have an escape character in front of it (so they don't count as quotes)
        while (line.contains("\\\"")) {
            int index = line.indexOf("\\\"");
            line = line.substring(0, index) + line.substring(index + 2);
        }
        if (inQuote) {
            if (line.contains("\"")) {
                line = line.substring(line.indexOf("\"") + 1);
                inQuote = false;
            } else {
                line = "";
            }
        }
        while (line.contains("\"")) {
            int firstQuote = line.indexOf("\"");
            int nextQuote = line.indexOf("\"", firstQuote + 1);
            if (nextQuote < 0) {
                // there isn't a 2nd quote
                inQuote = true;
                continue;
            }
            line = line.substring(0, firstQuote) + line.substring(nextQuote + 1);
        }

        // cut comments out of line
        if (inMultiLineComment) {
            if (line.contains("*/")) {
                line = line.substring(line.indexOf("*/") + 2);
                inMultiLineComment = false;
            } else {
                line = "";
            }
        }
        while (line.contains("/*")) {
            if (line.contains("*/")) {
                line = line.substring(0, line.indexOf("/*")) + line.substring(line.indexOf("*/") + 2);
            } else {
                line = line.substring(0, line.indexOf("/*"));
                inMultiLineComment = true;
            }
        }
        if (line.contains("//")) {
            line = line.substring(0, line.indexOf("//"));
        }

        return line.trim();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("KeywordCounter");
        frame.setContentPane(new KeywordCounter().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

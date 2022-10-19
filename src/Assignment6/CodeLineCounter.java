package Assignment6;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeLineCounter {

    private static final String METHOD_REGEX = ".*\\b(?!new\\b)([a-zA-Z_$][a-zA-Z0-9_$]*" +
            "(<[a-zA-Z_$][a-zA-Z0-9_$]*>)?(\\[])?\\s+[a-zA-Z_$][a-zA-Z0-9_$]*\\s*\\(.*\\)).*";

    private static JFrame frame;
    private JPanel panel;
    private JLabel fileNameLabel;
    private JButton chooseFileButton;
    private JButton countLinesButton;
    private JTextPane outputPane;

    private File file;

    public CodeLineCounter() {
        file = null;

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileNameLabel.setText(file.getAbsolutePath());
                frame.setSize((int) fileNameLabel.getPreferredSize().getWidth() + 20,
                        (int) panel.getPreferredSize().getHeight() + 30);
            }
        });

        countLinesButton.addActionListener(event -> {
            if (file == null) {
                return;
            }
            try {
                String fileClassName = file.getName().substring(0, file.getName().indexOf("."));
                final String constructorRegex = "((public|protected|private)?\\s*" + fileClassName + "\\s*\\(.*\\))\\s*\\{";

                int codeLineCounter = 0;
                int forCounter = 0;
                int whileCounter = 0;
                int ifCounter = 0;
                int switchCounter = 0;
                boolean inMultiLineComment = false;
                boolean inQuote = false;

                Map<String, Integer> constructors = new HashMap<>();
                Map<String, Integer> methods = new HashMap<>();
                int scopeLevel = -2;
                boolean methodIsConstructor = false;
                String methodName = "";
                int methodCodeLineCounter = -1;

                Scanner fileScan = new Scanner(file);
                while (fileScan.hasNextLine()) {
                    String line = fileScan.nextLine();
                    System.out.println(line);

                    // cut string literals out of line so that they don't count as keywords or operators
                    // seems arbitrary, but remove "\\" so line 75 doesn't error
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
                            continue;
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
                            continue;
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

                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    codeLineCounter++;
                    if (!methodName.isEmpty()) {
                        methodCodeLineCounter++;
                    }

                    if (line.matches(".*if\s*\\(.*")) {
                        ifCounter++;
                    } else if (line.matches(".*while\s*\\(.*")) {
                        whileCounter++;
                    } else if (line.matches(".*for\s*\\(.*")) {
                        forCounter++;
                    } else if (line.matches(".*switch\s*\\(.*")) {
                        switchCounter++;
                    } else if (line.matches(METHOD_REGEX) || line.matches(constructorRegex)) {
                        methodIsConstructor = line.matches(constructorRegex);
                        String regex = methodIsConstructor ? constructorRegex : METHOD_REGEX;
                        Matcher matcher = Pattern.compile(regex).matcher(line);
                        if (matcher.find()) {
                            methodName = matcher.group(1);
                            methodCodeLineCounter = 1;
                        }
                    }

                    Matcher matcher = Pattern.compile("[{}]").matcher(line);
                    while (matcher.find()) {
                        if (matcher.group(0).contentEquals("{")) {
                            scopeLevel++;
                        } else {
                            scopeLevel--;
                            if (scopeLevel == -1 && !methodName.isEmpty()) {
                                if (methodIsConstructor) {
                                    constructors.put(methodName, methodCodeLineCounter);
                                } else {
                                    methods.put(methodName, methodCodeLineCounter);
                                }
                                methodName = "";
                                methodCodeLineCounter = -1;
                            }
                        }
                    }
                }

                StringBuilder output = new StringBuilder();
                output.append(file.getName()).append("\n")
                        .append(file.getAbsoluteFile()).append("\n")
                        .append(codeLineCounter).append(" lines of code\n")
                        .append(ifCounter).append(" if statements\n")
                        .append(whileCounter).append(" while loops\n")
                        .append(forCounter).append(" for loops\n")
                        .append(switchCounter).append(" switch statements\n");
                constructors.forEach((name, lineCount) -> output.append("\nConstructor ").append(name).append(" has ")
                        .append(lineCount).append(" lines of code"));
                methods.forEach((name, lineCount) -> output.append("\nMethod ").append(name).append(" has ")
                        .append(lineCount).append(" lines of code"));

                double oldHeight = outputPane.getPreferredSize().getHeight();
                outputPane.setText(output.toString());


                frame.setSize((int) panel.getPreferredSize().getWidth() + 20,
                        (int) (panel.getPreferredSize().getHeight() + 20 +
                                outputPane.getPreferredSize().getHeight() - oldHeight));
            } catch (FileNotFoundException e) {
                outputPane.setText("FileNotFoundException");
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("CodeLineCounter");
        frame.setContentPane(new CodeLineCounter().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

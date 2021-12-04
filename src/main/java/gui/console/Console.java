package gui.console;

import gui.util.SystemPrintCaptured;

import javax.swing.*;
import java.awt.*;

public final class Console {
    private final JScrollPane scrollPane;
    private final JTextArea consoleTextArea;

    public Console() {
        consoleTextArea = new JTextArea();
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setEditable(false);
        consoleTextArea.setBackground(Color.DARK_GRAY);
        consoleTextArea.setForeground(Color.WHITE);

        this.scrollPane = new JScrollPane(consoleTextArea);

        System.setOut(new SystemPrintCaptured(consoleTextArea, System.out));
        System.setErr(new SystemPrintCaptured(consoleTextArea, System.err, "[ERROR] "));
    }

    public JScrollPane getConsole() {
        return this.scrollPane;
    }

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }
}

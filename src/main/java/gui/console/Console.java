package gui.console;

import gui.util.SystemPrintCaptured;

import javax.swing.*;
import java.awt.*;

public final class Console {
    private final JScrollPane scrollPane;

    public Console() {
        JTextArea consoleTextArea = new JTextArea();
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
}

package gui.console;

import javax.swing.*;

public final class ConsoleShortcuts {
    private final JPanel jPanel;
    private final JButton collapseButton;
    private final JButton copyAllButton;
    private final JButton outputToFileButton;

    public ConsoleShortcuts() {
        this.jPanel = new JPanel();
        this.jPanel.setLayout(new BoxLayout(this.jPanel, BoxLayout.Y_AXIS));

        collapseButton = new JButton(">");
        copyAllButton = new JButton("C");
        outputToFileButton = new JButton("F");

        this.jPanel.add(collapseButton);
        this.jPanel.add(copyAllButton);
        this.jPanel.add(outputToFileButton);
    }

    public JPanel getConsoleShortcutPanel() {
        return this.jPanel;
    }

    public JButton getCollapseButton() {
        return collapseButton;
    }

    public JButton getCopyAllButton() {
        return copyAllButton;
    }

    public JButton getOutputToFileButton() {
        return outputToFileButton;
    }
}

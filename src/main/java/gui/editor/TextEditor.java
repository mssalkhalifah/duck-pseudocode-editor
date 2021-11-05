package gui.editor;

import gui.console.Console;
import gui.console.ConsoleShortcuts;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public final class TextEditor {
    private final JPanel jPanel;
    private final RSyntaxTextArea SYNTAX_TEXT_AREA;

    public TextEditor() {
        this.jPanel = new JPanel(new GridBagLayout());
        this.jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.SYNTAX_TEXT_AREA = new RSyntaxTextArea();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        RTextScrollPane rTextScrollPane = new RTextScrollPane(this.SYNTAX_TEXT_AREA);

        rTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rTextScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.SYNTAX_TEXT_AREA.setLineWrap(true);
        AbstractTokenMakerFactory tokenMakerFactory = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        tokenMakerFactory.putMapping("text/Duck",  "gui.editor.DuckSyntaxTokenMaker");
        this.SYNTAX_TEXT_AREA.setSyntaxEditingStyle("text/Duck");
        Font font = this.SYNTAX_TEXT_AREA.getFont();
        float fontSize = font.getSize() + 5.0f;
        this.SYNTAX_TEXT_AREA.setFont(font.deriveFont(fontSize));

        // Add text area
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.jPanel.add(rTextScrollPane, gridBagConstraints);

        // Console shortcut
        ConsoleShortcuts consoleShortcuts = new ConsoleShortcuts();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 0.0001;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.jPanel.add(consoleShortcuts.getConsoleShortcutPanel(), gridBagConstraints);

        // Console
        Console console = new Console();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        this.jPanel.add(console.getConsole(), gridBagConstraints);

        setConsolePanelCollapsable(console.getConsole(), consoleShortcuts.getCollapseButton());
    }

    public JPanel getJPanel() {
        return this.jPanel;
    }

    public String getCurrentSourceCode() {
        return this.SYNTAX_TEXT_AREA.getText();
    }

    private void setConsolePanelCollapsable(JComponent panelCollapsable, JButton collapseButton) {
        collapseButton.addActionListener(actionEvent -> {
            panelCollapsable.setVisible(!panelCollapsable.isVisible());
            this.jPanel.revalidate();
            this.jPanel.repaint();
        });
    }
}

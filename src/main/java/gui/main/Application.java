package gui.main;

import controller.CompilerWorker;
import gui.editor.AppMenuBar;
import gui.editor.TextEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class Application {
    private final JFrame jFrame;
    private final TextEditor TEXT_EDITOR;
    private final AppMenuBar APP_MENU_BAR;

    public Application() {
        this.jFrame = new JFrame("Duck Pseudocode Editor");
        this.jFrame.getContentPane().setLayout(new BorderLayout());
        this.TEXT_EDITOR = new TextEditor();
        this.APP_MENU_BAR = new AppMenuBar();

        initializeGUI();
        initializeActionEvents();

        this.jFrame.setIconImage(new ImageIcon(Objects
                .requireNonNull(Application.class.getClassLoader().getResource("logoo.png"))).getImage());
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setPreferredSize(new Dimension(1280, 720));
        this.jFrame.setResizable(true);
    }

    public void startGUI() {
        this.jFrame.pack();
        this.jFrame.setVisible(true);
    }

    private void initializeGUI() {
        this.jFrame.add(this.APP_MENU_BAR.getMenuBar(), BorderLayout.PAGE_START);
        this.jFrame.add(this.TEXT_EDITOR.getJPanel(), BorderLayout.CENTER);
    }

    private void initializeActionEvents() {
        this.APP_MENU_BAR.getRunButton().addActionListener(actionEvent -> {
            String sourceCode = this.TEXT_EDITOR.getCurrentSourceCode();
            CompilerWorker compilerWorker = new CompilerWorker(sourceCode);
            compilerWorker.execute();
        });
    }
}

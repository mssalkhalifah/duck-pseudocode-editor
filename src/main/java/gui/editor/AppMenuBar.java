package gui.editor;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

public class AppMenuBar {
    private final JMenuBar MENU_BAR;
    private final JButton RUN_BUTTON;

    public AppMenuBar() {
        this.MENU_BAR = new JMenuBar();
        this.RUN_BUTTON = initializeRunButton();

        HashMap<String, JMenu> jMenuHashMap = new HashMap<>();
        jMenuHashMap.put("File", new JMenu("File"));
        jMenuHashMap.put("Run", new JMenu("Run"));
        jMenuHashMap.put("About", new JMenu("About"));

        initializeFileMenu(jMenuHashMap.get("File"));
        initializeRunMenu(jMenuHashMap.get("Run"));
        initializeAboutMenu(jMenuHashMap.get("About"));

        this.MENU_BAR.add(jMenuHashMap.get("File"));
        this.MENU_BAR.add(jMenuHashMap.get("Run"));
        this.MENU_BAR.add(jMenuHashMap.get("About"));

        this.MENU_BAR.add(Box.createHorizontalGlue());
        this.MENU_BAR.add(this.RUN_BUTTON);
    }

    public JMenuBar getMenuBar() {
        return this.MENU_BAR;
    }

    public JButton getRunButton() {
        return this.RUN_BUTTON;
    }

    private void initializeFileMenu(JMenu menu) {
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportSampleTableItem = new JMenuItem("Export Sample Table");
        JMenuItem exitItem = new JMenuItem("Exit");

        menu.add(newItem);
        menu.add(openItem);
        menu.addSeparator();
        menu.add(saveItem);
        menu.addSeparator();
        menu.add(exportSampleTableItem);
        menu.add(exitItem);
    }

    private void initializeRunMenu(JMenu menu) {
        JMenuItem runItem = new JMenuItem("Run Code");

        menu.add(runItem);
    }

    private void initializeAboutMenu(JMenu menu) {

    }

    private JButton initializeRunButton() {
        ImageIcon runImageIcon = new ImageIcon(Objects.requireNonNull(AppMenuBar.class.getClassLoader().getResource("s.png")));

        return new JButton("Run", runImageIcon);
    }
}

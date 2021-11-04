package main;

import gui.DesignScreen;

import javax.swing.*;

public class Main {

    private Main() {}

    public static void start() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DesignScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        DesignScreen designscreen = new DesignScreen();
        designscreen.setVisible(true);
    }
}

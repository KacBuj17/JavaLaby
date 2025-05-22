package org.example.gui;

import javax.swing.*;
import com.formdev.flatlaf.*;

public class GUI extends JFrame {
    public static String currentUserLogin;
    public static String currentAdminLogin;

    public static void run() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e){
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(WelcomeScreen::new);
    }
}
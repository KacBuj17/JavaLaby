package org.example;

import javax.swing.*;

public class GUI extends JFrame {
    public static String currentUserLogin;

    public static void run() {
        SwingUtilities.invokeLater(() -> new WelcomeScreen().setVisible(true));
    }
}
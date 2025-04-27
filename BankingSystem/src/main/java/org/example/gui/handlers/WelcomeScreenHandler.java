package org.example.gui.handlers;

import org.example.gui.AdminLoginPanel;
import org.example.gui.RegisterPanel;
import org.example.gui.LoginPanel;

import javax.swing.*;

public class WelcomeScreenHandler {

    public static void handleLoginButton(JFrame welcomeScreen) {
        welcomeScreen.dispose();
        new LoginPanel();
    }

    public static void handleRegisterButton(JFrame welcomeScreen) {
        welcomeScreen.dispose();
        new RegisterPanel();
    }

    public static void handleAdminLoginButton(JFrame welcomeScreen) {
        welcomeScreen.dispose();
        new AdminLoginPanel();
    }

    public static void handleExitButton(JFrame welcomeScreen) {
        int confirm = JOptionPane.showConfirmDialog(welcomeScreen, "Czy na pewno chcesz zamknąć program?",
                "Potwierdzenie", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

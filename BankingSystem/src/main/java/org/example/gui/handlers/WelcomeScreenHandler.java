package org.example.gui.handlers;

import org.example.gui.AdminLoginPanel;
import org.example.gui.RegisterPanel;
import org.example.gui.LoginPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class WelcomeScreenHandler {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenHandler.class);

    public static void handleLoginButton(JFrame welcomeScreen) {
        logger.debug("Kliknięto przycisk logowania.");
        welcomeScreen.dispose();
        new LoginPanel();
    }

    public static void handleRegisterButton(JFrame welcomeScreen) {
        logger.debug("Kliknięto przycisk rejestracji.");
        welcomeScreen.dispose();
        new RegisterPanel();
    }

    public static void handleAdminLoginButton(JFrame welcomeScreen) {
        logger.debug("Kliknięto przycisk logowania administratora.");
        welcomeScreen.dispose();
        new AdminLoginPanel();
    }

    public static void handleExitButton(JFrame welcomeScreen) {
        logger.debug("Kliknięto przycisk wyjścia.");
        int confirm = JOptionPane.showConfirmDialog(welcomeScreen, "Czy na pewno chcesz zamknąć program?",
                "Potwierdzenie", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            logger.debug("Użytkownik potwierdził zamknięcie programu.");
            System.exit(0);
        } else {
            logger.debug("Użytkownik anulował zamknięcie programu.");
        }
    }
}

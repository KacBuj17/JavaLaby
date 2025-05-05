package org.example.gui.handlers;

import org.example.gui.AdminLoginPanel;
import org.example.gui.GUI;
import org.example.gui.AdminPanel;
import org.example.gui.WelcomeScreen;
import org.example.db.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class AdminLoginPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdminLoginPanelHandler.class);

    public static void attachHandlers(AdminLoginPanel panel) {
        logger.debug("Przypisywanie akcji do przycisków panelu logowania administratora.");
        panel.getLoginBtn().addActionListener(e -> handleLogin(panel));
        panel.getBackBtn().addActionListener(e -> handleBack(panel));
    }

    private static void handleLogin(AdminLoginPanel panel) {
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());
        logger.debug("Próba logowania administratora: {}", login);

        try {
            if (DBManager.validateAdminLogin(login, password)) {
                logger.debug("Logowanie administratora zakończone sukcesem: {}", login);
                GUI.currentAdminLogin = login;
                panel.dispose();
                new AdminPanel();
            } else {
                logger.debug("Nieudane logowanie administratora: {}", login);
                JOptionPane.showMessageDialog(panel, "Błędny login lub hasło.");
            }
        } catch (Exception ex) {
            logger.error("Błąd podczas logowania administratora: {}", login, ex);
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd podczas logowania.");
        }
    }

    private static void handleBack(AdminLoginPanel panel) {
        logger.debug("Powrót do ekranu powitalnego z panelu logowania administratora.");
        panel.dispose();
        new WelcomeScreen();
    }
}

package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.LoginPanel;
import org.example.gui.GUI;
import org.example.gui.UserPanel;
import org.example.gui.WelcomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class LoginPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginPanelHandler.class);

    public static void attachHandlers(LoginPanel panel) {
        logger.debug("Przypisywanie handlerów do przycisków logowania użytkownika");
        panel.getLoginBtn().addActionListener(_ -> handleLogin(panel));
        panel.getBackBtn().addActionListener(_ -> handleBack(panel));
    }

    private static void handleLogin(LoginPanel panel) {
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());

        logger.debug("Próba logowania użytkownika: {}", login);

        if (DBManager.validateLogin(login, password)) {
            logger.debug("Logowanie udane dla użytkownika: {}", login);
            GUI.currentUserLogin = login;
            panel.dispose();
            new UserPanel();
        } else {
            logger.error("Nieudana próba logowania dla użytkownika: {}", login);
            JOptionPane.showMessageDialog(panel, "Błędny login lub hasło.");
        }
    }

    private static void handleBack(LoginPanel panel) {
        logger.debug("Kliknięto przycisk powrotu w panelu logowania");
        panel.dispose();
        new WelcomeScreen();
    }
}

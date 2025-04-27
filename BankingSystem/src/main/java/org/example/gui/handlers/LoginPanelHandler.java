package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.LoginPanel;
import org.example.gui.GUI;
import org.example.gui.UserPanel;
import org.example.gui.WelcomeScreen;

import javax.swing.*;

public class LoginPanelHandler {

    public static void attachHandlers(LoginPanel panel) {
        panel.getLoginBtn().addActionListener(e -> handleLogin(panel));
        panel.getBackBtn().addActionListener(e -> handleBack(panel));
    }

    private static void handleLogin(LoginPanel panel) {
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());

        if (DBManager.validateLogin(login, password)) {
            GUI.currentUserLogin = login;
            panel.dispose();
            new UserPanel();
        } else {
            JOptionPane.showMessageDialog(panel, "Błędny login lub hasło.");
        }
    }

    private static void handleBack(LoginPanel panel) {
        panel.dispose();
        new WelcomeScreen();
    }
}

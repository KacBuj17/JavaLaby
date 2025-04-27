package org.example.gui.handlers;

import org.example.gui.AdminLoginPanel;
import org.example.gui.GUI;
import org.example.gui.AdminPanel;
import org.example.gui.WelcomeScreen;
import org.example.db.DBManager;

import javax.swing.*;

public class AdminLoginPanelHandler {

    public static void attachHandlers(AdminLoginPanel panel) {
        panel.getLoginBtn().addActionListener(e -> handleLogin(panel));
        panel.getBackBtn().addActionListener(e -> handleBack(panel));
    }

    private static void handleLogin(AdminLoginPanel panel) {
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());
        if (DBManager.validateAdminLogin(login, password)) {
            GUI.currentAdminLogin = login;
            panel.dispose();
            new AdminPanel();
        } else {
            JOptionPane.showMessageDialog(panel, "Błędny login lub hasło.");
        }
    }

    private static void handleBack(AdminLoginPanel panel) {
        panel.dispose();
        new WelcomeScreen();
    }
}

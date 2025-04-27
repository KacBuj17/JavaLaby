package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.AdminRegisterPanel;
import javax.swing.*;

public class AdminRegisterPanelHandler {

    public static void attachHandlers(AdminRegisterPanel panel) {
        panel.getRegisterButton().addActionListener(e -> handleRegister(panel));
    }

    private static void handleRegister(AdminRegisterPanel panel) {
        String firstName = panel.getFirstNameField().getText();
        String lastName = panel.getLastNameField().getText();
        String email = panel.getEmailField().getText();
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());

        if (DBManager.registerAdmin(firstName, lastName, email, login, password)) {
            JOptionPane.showMessageDialog(panel, "Administrator zarejestrowany pomyślnie");
            panel.dispose();
        } else {
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd podczas rejestracji");
        }
    }
}

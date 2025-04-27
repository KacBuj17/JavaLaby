package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.LoginPanel;

import javax.swing.*;

public class RegisterPanelHandler {

    public static void handleRegisterButton(JTextField nameField, JTextField surnameField, JTextField emailField,
                                            JTextField loginField, JPasswordField passwordField,
                                            JPasswordField confirmPasswordField, JFrame registerPanel) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (password.equals(confirmPassword)) {
            if (DBManager.registerUser(name, surname, email, login, password)) {
                JOptionPane.showMessageDialog(registerPanel, "Rejestracja zakończona sukcesem!");
                registerPanel.dispose();
                new LoginPanel();
            } else {
                JOptionPane.showMessageDialog(registerPanel, "Błąd rejestracji.");
            }
        } else {
            JOptionPane.showMessageDialog(registerPanel, "Hasła się nie zgadzają.");
        }
    }
}

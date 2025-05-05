package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.LoginPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class RegisterPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegisterPanelHandler.class);

    public static void handleRegisterButton(JTextField nameField, JTextField surnameField, JTextField emailField,
                                            JTextField loginField, JPasswordField passwordField,
                                            JPasswordField confirmPasswordField, JFrame registerPanel) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        logger.debug("Rozpoczęcie procesu rejestracji użytkownika: {}", login);

        if (password.equals(confirmPassword)) {
            if (DBManager.registerUser(name, surname, email, login, password)) {
                logger.debug("Rejestracja zakończona sukcesem dla użytkownika: {}", login);
                JOptionPane.showMessageDialog(registerPanel, "Rejestracja zakończona sukcesem!");
                registerPanel.dispose();
                new LoginPanel();
            } else {
                logger.error("Błąd podczas rejestracji użytkownika: {}", login);
                JOptionPane.showMessageDialog(registerPanel, "Błąd rejestracji.");
            }
        } else {
            logger.error("Hasła nie zgadzają się dla użytkownika: {}", login);
            JOptionPane.showMessageDialog(registerPanel, "Hasła się nie zgadzają.");
        }
    }
}

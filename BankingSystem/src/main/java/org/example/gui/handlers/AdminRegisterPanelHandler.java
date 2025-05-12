package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.AdminRegisterPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class AdminRegisterPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdminRegisterPanelHandler.class);

    public static void attachHandlers(AdminRegisterPanel panel) {
        logger.debug("Przypisywanie handlera do przycisku rejestracji administratora");
        panel.getRegisterButton().addActionListener(_ -> handleRegister(panel));
    }

    private static void handleRegister(AdminRegisterPanel panel) {
        logger.debug("Kliknięto przycisk: Zarejestruj administratora");

        String firstName = panel.getFirstNameField().getText();
        String lastName = panel.getLastNameField().getText();
        String email = panel.getEmailField().getText();
        String login = panel.getLoginField().getText();
        String password = new String(panel.getPasswordField().getPassword());

        logger.debug("Dane rejestracji - Imię: {}, Nazwisko: {}, Email: {}, Login: {}", firstName, lastName, email, login);

        if (DBManager.registerAdmin(firstName, lastName, email, login, password)) {
            logger.debug("Administrator zarejestrowany pomyślnie");
            JOptionPane.showMessageDialog(panel, "Administrator zarejestrowany pomyślnie");
            panel.dispose();
        } else {
            logger.error("Wystąpił błąd podczas rejestracji administratora");
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd podczas rejestracji");
        }
    }
}

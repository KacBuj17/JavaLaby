package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.AdminPanel;
import org.example.gui.AdminRegisterPanel;
import org.example.gui.WelcomeScreen;
import org.example.gui.GUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdminPanelHandler.class);

    public static void attachHandlers(AdminPanel panel) {
        logger.debug("Przypisywanie handlerów do przycisków panelu administratora");
        panel.getRegisterAdminButton().addActionListener(_ -> handleRegisterAdmin());
        panel.getViewUsersButton().addActionListener(_ -> handleViewUsers(panel));
        panel.getLogoutButton().addActionListener(_ -> handleLogout(panel));
    }

    private static void handleRegisterAdmin() {
        logger.debug("Kliknięto przycisk: Zarejestruj administratora");
        new AdminRegisterPanel();
    }

    private static void handleViewUsers(AdminPanel panel) {
        logger.debug("Kliknięto przycisk: Wyświetl użytkowników");
        ResultSet rs = DBManager.getAllUsers();
        try {
            if (rs != null) {
                String[] columnNames = {"Login", "Imię", "Nazwisko", "Email", "Numer konta", "Stan konta"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                while (rs.next()) {
                    String login = rs.getString("login");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String accountNumber = rs.getString("account_number");
                    double balance = rs.getDouble("balance");

                    Object[] rowData = {login, firstName, lastName, email, accountNumber, balance};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(600, 300));

                JOptionPane.showMessageDialog(panel, scrollPane, "Lista użytkowników", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("Pomyślnie załadowano i wyświetlono dane użytkowników");
            } else {
                logger.error("ResultSet z użytkownikami jest nullem");
                JOptionPane.showMessageDialog(panel, "Błąd podczas pobierania danych użytkowników.");
            }
        } catch (SQLException ex) {
            logger.error("Błąd SQL podczas pobierania użytkowników: {}", ex.getMessage(), ex);
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd: " + ex.getMessage());
        }
    }

    private static void handleLogout(AdminPanel panel) {
        logger.debug("Kliknięto przycisk: Wyloguj");
        GUI.currentAdminLogin = null;
        JOptionPane.showMessageDialog(panel, "Wylogowano pomyślnie.");
        panel.dispose();
        new WelcomeScreen();
    }
}

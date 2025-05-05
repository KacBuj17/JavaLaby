package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UserPanelHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserPanelHandler.class);

    public static void attachHandlers(UserPanel panel) {
        panel.getDepositButton().addActionListener(e -> handleDeposit(panel));
        panel.getWithdrawButton().addActionListener(e -> handleWithdraw(panel));
        panel.getTransferButton().addActionListener(e -> handleTransfer(panel));
        panel.getTransfersButton().addActionListener(e -> handleTransfers(panel));
        panel.getInfoButton().addActionListener(e -> handleInfo(panel));
        panel.getLogoutButton().addActionListener(e -> handleLogout(panel));
    }

    private static void handleDeposit(UserPanel panel) {
        String amountStr = JOptionPane.showInputDialog(panel, "Wpisz kwotę do wpłaty:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                DBManager.updateBalance(GUI.currentUserLogin, amount);
                logger.debug("Wpłata {:.2f} PLN zakończona sukcesem przez użytkownika: {}", amount, GUI.currentUserLogin);
                JOptionPane.showMessageDialog(panel, "Wpłata zakończona sukcesem!");
                panel.dispose();
                new UserPanel();
            } catch (NumberFormatException e) {
                logger.error("Nieprawidłowy format liczby podczas wpłaty: {}", amountStr, e);
                JOptionPane.showMessageDialog(panel, "Nieprawidłowy format kwoty.");
            }
        }
    }

    private static void handleWithdraw(UserPanel panel) {
        String amountStr = JOptionPane.showInputDialog(panel, "Wpisz kwotę do wypłaty:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (DBManager.getBalance(GUI.currentUserLogin) >= amount) {
                    DBManager.updateBalance(GUI.currentUserLogin, -amount);
                    logger.debug("Wypłata {:.2f} PLN zakończona sukcesem przez użytkownika: {}", amount, GUI.currentUserLogin);
                    JOptionPane.showMessageDialog(panel, "Wypłata zakończona sukcesem!");
                    panel.dispose();
                    new UserPanel();
                } else {
                    logger.error("Nieudana próba wypłaty: brak środków. Użytkownik: {}, Kwota: {}", GUI.currentUserLogin, amount);
                    JOptionPane.showMessageDialog(panel, "Niewystarczająca ilość środków!");
                }
            } catch (NumberFormatException e) {
                logger.error("Nieprawidłowy format liczby podczas wypłaty: {}", amountStr, e);
                JOptionPane.showMessageDialog(panel, "Nieprawidłowy format kwoty.");
            }
        }
    }

    private static void handleTransfer(UserPanel panel) {
        String receiverAccount = JOptionPane.showInputDialog(panel, "Wpisz numer konta odbiorcy:");
        String amountStr = JOptionPane.showInputDialog(panel, "Wpisz kwotę do przelewu:");
        String subject = JOptionPane.showInputDialog(panel, "Wpisz tytuł przelewu:");
        if (receiverAccount != null && amountStr != null && subject != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (DBManager.makeTransfer(GUI.currentUserLogin, receiverAccount, amount, subject)) {
                    logger.debug("Przelew {:.2f} PLN do {} zakończony sukcesem. Użytkownik: {}", amount, receiverAccount, GUI.currentUserLogin);
                    JOptionPane.showMessageDialog(panel, "Przelew zakończony sukcesem!");
                    panel.dispose();
                    new UserPanel();
                } else {
                    logger.error("Błąd przelewu. Nadawca: {}, Odbiorca: {}, Kwota: {}", GUI.currentUserLogin, receiverAccount, amount);
                    JOptionPane.showMessageDialog(panel, "Błąd przelewu!");
                }
            } catch (NumberFormatException e) {
                logger.error("Nieprawidłowy format liczby podczas przelewu: {}", amountStr, e);
                JOptionPane.showMessageDialog(panel, "Nieprawidłowy format kwoty.");
            }
        }
    }

    private static void handleTransfers(UserPanel panel) {
        ResultSet rs = DBManager.getTransfers(DBManager.getAccountNumber(GUI.currentUserLogin));
        try {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                String from = rs.getString("from_account");
                String to = rs.getString("to_account");
                String description;

                if (from == null) {
                    description = "Wpłata na konto";
                } else if (to == null) {
                    description = "Wypłata z konta";
                } else {
                    description = "Przelew do: " + to;
                }

                sb.append("Data: ").append(rs.getString("timestamp")).append("\n")
                        .append("Kwota: ").append(rs.getDouble("amount")).append(" PLN\n")
                        .append("Tytuł: ").append(rs.getString("subject")).append("\n")
                        .append(description).append("\n\n");
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(panel, scrollPane);
            logger.debug("Pobrano historię transakcji dla użytkownika: {}", GUI.currentUserLogin);
        } catch (Exception ex) {
            logger.error("Błąd podczas pobierania historii transakcji użytkownika: {}", GUI.currentUserLogin, ex);
            JOptionPane.showMessageDialog(panel, "Błąd podczas ładowania historii.");
        }
    }

    private static void handleInfo(UserPanel panel) {
        ResultSet rs = DBManager.getUserInfo(GUI.currentUserLogin);
        try {
            if (rs != null && rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Imię: ").append(rs.getString("first_name")).append("\n")
                        .append("Nazwisko: ").append(rs.getString("last_name")).append("\n")
                        .append("Email: ").append(rs.getString("email")).append("\n")
                        .append("Login: ").append(rs.getString("login")).append("\n")
                        .append("Numer rachunku: ").append(rs.getString("account_number"));
                JOptionPane.showMessageDialog(panel, sb.toString(), "Informacje o koncie", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("Pobrano dane użytkownika: {}", GUI.currentUserLogin);
            } else {
                logger.error("Nie znaleziono danych użytkownika: {}", GUI.currentUserLogin);
                JOptionPane.showMessageDialog(panel, "Nie udało się pobrać danych użytkownika.");
            }
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek przy pobieraniu danych użytkownika: {}", GUI.currentUserLogin, ex);
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd podczas pobierania informacji.");
        }
    }

    private static void handleLogout(UserPanel panel) {
        logger.debug("Użytkownik wylogował się: {}", GUI.currentUserLogin);
        GUI.currentUserLogin = null;
        JOptionPane.showMessageDialog(panel, "Wylogowano pomyślnie.");
        panel.dispose();
        new WelcomeScreen();
    }
}

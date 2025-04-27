package org.example.gui.handlers;

import org.example.db.DBManager;
import org.example.gui.*;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UserPanelHandler {

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
            double amount = Double.parseDouble(amountStr);
            DBManager.updateBalance(GUI.currentUserLogin, amount);
            JOptionPane.showMessageDialog(panel, "Wpłata zakończona sukcesem!");
            panel.dispose();
            new UserPanel();
        }
    }

    private static void handleWithdraw(UserPanel panel) {
        String amountStr = JOptionPane.showInputDialog(panel, "Wpisz kwotę do wypłaty:");
        if (amountStr != null) {
            double amount = Double.parseDouble(amountStr);
            if (DBManager.getBalance(GUI.currentUserLogin) >= amount) {
                DBManager.updateBalance(GUI.currentUserLogin, -amount);
                JOptionPane.showMessageDialog(panel, "Wypłata zakończona sukcesem!");
                panel.dispose();
                new UserPanel();
            } else {
                JOptionPane.showMessageDialog(panel, "Niewystarczająca ilość środków!");
            }
        }
    }

    private static void handleTransfer(UserPanel panel) {
        String receiverAccount = JOptionPane.showInputDialog(panel, "Wpisz numer konta odbiorcy:");
        String amountStr = JOptionPane.showInputDialog(panel, "Wpisz kwotę do przelewu:");
        String subject = JOptionPane.showInputDialog(panel, "Wpisz tytuł przelewu:");
        if (receiverAccount != null && amountStr != null && subject != null) {
            double amount = Double.parseDouble(amountStr);
            if (DBManager.makeTransfer(GUI.currentUserLogin, receiverAccount, amount, subject)) {
                JOptionPane.showMessageDialog(panel, "Przelew zakończony sukcesem!");
                panel.dispose();
                new UserPanel();
            } else {
                JOptionPane.showMessageDialog(panel, "Błąd przelewu!");
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
        } catch (Exception ex) {
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
            } else {
                JOptionPane.showMessageDialog(panel, "Nie udało się pobrać danych użytkownika.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Wystąpił błąd podczas pobierania informacji.");
        }
    }

    private static void handleLogout(UserPanel panel) {
        GUI.currentUserLogin = null;
        JOptionPane.showMessageDialog(panel, "Wylogowano pomyślnie.");
        panel.dispose();
        new WelcomeScreen();
    }
}

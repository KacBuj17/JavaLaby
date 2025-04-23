package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

class UserPanel extends JFrame {
    public UserPanel() {
        setTitle("Panel użytkownika");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Witaj w swoim banku, " + GUI.currentUserLogin, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        JLabel balanceLabel = new JLabel("Stan konta: " + DBManager.getBalance(GUI.currentUserLogin) + " PLN");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balancePanel.add(balanceLabel);

        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        optionsPanel.setBackground(Color.WHITE);

        RoundedButton depositButton = new RoundedButton("Wpłać pieniądze");
        RoundedButton withdrawButton = new RoundedButton("Wypłać pieniądze");
        RoundedButton transferButton = new RoundedButton("Przelew do innego użytkownika");

        depositButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(this, "Wpisz kwotę do wpłaty:");
            if (amount != null) {
                DBManager.updateBalance(GUI.currentUserLogin, Double.parseDouble(amount));
                JOptionPane.showMessageDialog(this, "Wpłata zakończona sukcesem!");
                dispose();
                new UserPanel();
            }
        });

        withdrawButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(this, "Wpisz kwotę do wypłaty:");
            if (amount != null && DBManager.getBalance(GUI.currentUserLogin) >= Double.parseDouble(amount)) {
                DBManager.updateBalance(GUI.currentUserLogin, -Double.parseDouble(amount));
                JOptionPane.showMessageDialog(this, "Wypłata zakończona sukcesem!");
                dispose();
                new UserPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Niewystarczająca ilość środków!");
            }
        });

        transferButton.addActionListener(e -> {
            String receiverAccount = JOptionPane.showInputDialog(this, "Wpisz numer konta odbiorcy:");
            String amount = JOptionPane.showInputDialog(this, "Wpisz kwotę do przelewu:");
            String subject = JOptionPane.showInputDialog(this, "Wpisz tytuł przelewu:");
            if (receiverAccount != null && amount != null && subject != null) {
                if (DBManager.makeTransfer(GUI.currentUserLogin, receiverAccount, Double.parseDouble(amount), subject)) {
                    JOptionPane.showMessageDialog(this, "Przelew zakończony sukcesem!");
                    dispose();
                    new UserPanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd przelewu!");
                }
            }
        });

        optionsPanel.add(depositButton);
        optionsPanel.add(withdrawButton);
        optionsPanel.add(transferButton);

        JPanel transfersPanel = new JPanel();
        transfersPanel.setBackground(Color.WHITE);
        RoundedButton transfersButton = new RoundedButton("Historia transakcji");
        RoundedButton infoButton = new RoundedButton("Informacje o koncie");
        RoundedButton logoutButton = new RoundedButton("Wyloguj się");

        transfersButton.addActionListener(e -> {
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

                // Tworzenie tekstu w panelu historii z przewijaniem
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false); // Tylko do odczytu
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                JOptionPane.showMessageDialog(this, scrollPane);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd podczas ładowania historii.");
            }
        });

        infoButton.addActionListener(e -> {
            ResultSet rs = DBManager.getUserInfo(GUI.currentUserLogin);
            try {
                if (rs != null && rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Imię: ").append(rs.getString("first_name")).append("\n")
                            .append("Nazwisko: ").append(rs.getString("last_name")).append("\n")
                            .append("Email: ").append(rs.getString("email")).append("\n")
                            .append("Login: ").append(rs.getString("login")).append("\n")
                            .append("Numer rachunku: ").append(rs.getString("account_number"));
                    JOptionPane.showMessageDialog(this, sb.toString(), "Informacje o koncie", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Nie udało się pobrać danych użytkownika.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas pobierania informacji.");
            }
        });

        logoutButton.addActionListener(e -> {
            // Wylogowywanie użytkownika
            GUI.currentUserLogin = null;
            JOptionPane.showMessageDialog(this, "Wylogowano pomyślnie.");
            dispose();
            new WelcomeScreen();
        });


        transfersPanel.add(transfersButton);
        transfersPanel.add(infoButton);
        transfersPanel.add(logoutButton);

        add(balancePanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(transfersPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

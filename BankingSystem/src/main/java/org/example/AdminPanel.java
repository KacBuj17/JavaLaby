package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Panel Administratora");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Witaj w panelu administratora, " + GUI.currentAdminLogin, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        optionsPanel.setBackground(Color.WHITE);

        RoundedButton registerAdminButton = new RoundedButton("Zarejestruj nowego administratora");
        RoundedButton viewUsersButton = new RoundedButton("Przeglądaj użytkowników");
        RoundedButton logoutButton = new RoundedButton("Wyloguj się");

        registerAdminButton.addActionListener(e -> {
            new AdminRegisterPanel();
        });

        viewUsersButton.addActionListener(e -> {
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

                    JOptionPane.showMessageDialog(this, scrollPane, "Lista użytkowników", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd podczas pobierania danych użytkowników.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Wystąpił błąd: " + ex.getMessage());
            }
        });


        logoutButton.addActionListener(e -> {
            GUI.currentAdminLogin = null;
            JOptionPane.showMessageDialog(this, "Wylogowano pomyślnie.");
            dispose();
            new WelcomeScreen();
        });

        optionsPanel.add(registerAdminButton);
        optionsPanel.add(viewUsersButton);
        optionsPanel.add(logoutButton);

        add(optionsPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

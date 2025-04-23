package org.example;

import javax.swing.*;
import java.awt.*;

class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("🏦 Bank Centralny");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Witamy w Banku Centralnym!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 20));

        RoundedButton loginButton = new RoundedButton("Zaloguj się");
        RoundedButton registerButton = new RoundedButton("Zarejestruj się");

        loginButton.addActionListener(e -> {
            dispose();
            new LoginPanel();
        });

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterPanel();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

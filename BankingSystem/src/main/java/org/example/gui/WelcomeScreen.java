package org.example.gui;

import org.example.gui.handlers.WelcomeScreenHandler;
import org.example.gui.utils.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Bank Centralny");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Witamy w systemie naszego Banku!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 20));

        RoundedButton loginButton = new RoundedButton("Zaloguj się");
        RoundedButton registerButton = new RoundedButton("Zarejestruj się");
        RoundedButton adminLoginButton = new RoundedButton("Zaloguj się jako administrator");
        RoundedButton exitButton = new RoundedButton("Zamknij program");

        loginButton.addActionListener(e -> WelcomeScreenHandler.handleLoginButton(this));
        registerButton.addActionListener(e -> WelcomeScreenHandler.handleRegisterButton(this));
        adminLoginButton.addActionListener(e -> WelcomeScreenHandler.handleAdminLoginButton(this));
        exitButton.addActionListener(e -> WelcomeScreenHandler.handleExitButton(this));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminLoginButton);
        buttonPanel.add(exitButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

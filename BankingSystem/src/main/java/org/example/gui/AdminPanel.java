package org.example.gui;

import org.example.gui.handlers.AdminPanelHandler;
import org.example.gui.utils.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {
    private final RoundedButton registerAdminButton;
    private final RoundedButton viewUsersButton;
    private final RoundedButton logoutButton;

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

        registerAdminButton = new RoundedButton("Zarejestruj nowego administratora");
        viewUsersButton = new RoundedButton("Przeglądaj użytkowników");
        logoutButton = new RoundedButton("Wyloguj się");

        optionsPanel.add(registerAdminButton);
        optionsPanel.add(viewUsersButton);
        optionsPanel.add(logoutButton);

        add(optionsPanel, BorderLayout.CENTER);

        AdminPanelHandler.attachHandlers(this);

        setVisible(true);
    }

    public RoundedButton getRegisterAdminButton() {
        return registerAdminButton;
    }

    public RoundedButton getViewUsersButton() {
        return viewUsersButton;
    }

    public RoundedButton getLogoutButton() {
        return logoutButton;
    }
}

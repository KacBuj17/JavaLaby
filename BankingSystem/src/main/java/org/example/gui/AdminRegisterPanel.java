package org.example.gui;

import org.example.gui.handlers.AdminRegisterPanelHandler;

import javax.swing.*;
import java.awt.*;

public class AdminRegisterPanel extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public AdminRegisterPanel() {
        setTitle("Rejestracja Administratora");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel firstNameLabel = new JLabel("Imię:");
        firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Nazwisko:");
        lastNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel loginLabel = new JLabel("Login:");
        loginField = new JTextField();
        JLabel passwordLabel = new JLabel("Hasło:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Zarejestruj");

        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(emailLabel);
        add(emailField);
        add(loginLabel);
        add(loginField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);

        AdminRegisterPanelHandler.attachHandlers(this);

        setVisible(true);
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}

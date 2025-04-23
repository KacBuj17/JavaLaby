package org.example;

import javax.swing.*;
import java.awt.*;

class AdminRegisterPanel extends JFrame {
    public AdminRegisterPanel() {
        setTitle("Rejestracja Administratora");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel firstNameLabel = new JLabel("Imię:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Nazwisko:");
        JTextField lastNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();
        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Zarejestruj");
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            if (DBManager.registerAdmin(firstName, lastName, email, login, password)) {
                JOptionPane.showMessageDialog(this, "Administrator zarejestrowany pomyślnie");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas rejestracji");
            }
        });

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

        setVisible(true);
    }
}

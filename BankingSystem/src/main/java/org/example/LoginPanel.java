package org.example;

import javax.swing.*;
import java.awt.*;

class LoginPanel extends JFrame {
    public LoginPanel() {
        setTitle("Logowanie");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Logowanie", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        form.add(new JLabel("Login:"));
        JTextField loginField = new JTextField();
        form.add(loginField);

        form.add(new JLabel("Hasło:"));
        JPasswordField passwordField = new JPasswordField();
        form.add(passwordField);
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        RoundedButton loginBtn = new RoundedButton("Zaloguj");
        RoundedButton backBtn = new RoundedButton("Powrót");

        loginBtn.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            if (DBManager.validateLogin(login, password)) {
                GUI.currentUserLogin = login;
                dispose();
                new UserPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Błędny login lub hasło.");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new WelcomeScreen();
        });

        buttons.add(loginBtn);
        buttons.add(backBtn);
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }
}
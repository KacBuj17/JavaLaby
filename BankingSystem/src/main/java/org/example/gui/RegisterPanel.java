package org.example.gui;

import org.example.gui.handlers.RegisterPanelHandler;
import org.example.gui.utils.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterPanel() {
        setTitle("Rejestracja");
        setSize(420, 450);  // Zwiększyłem rozmiar okna
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Rejestracja", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        form.add(new JLabel("Imię:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Nazwisko:"));
        surnameField = new JTextField();
        form.add(surnameField);

        form.add(new JLabel("Email:"));
        emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Login:"));
        loginField = new JTextField();
        form.add(loginField);

        form.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Potwierdź hasło:"));
        confirmPasswordField = new JPasswordField();
        form.add(confirmPasswordField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        RoundedButton registerBtn = new RoundedButton("Zarejestruj");
        RoundedButton backBtn = new RoundedButton("Powrót");

        registerBtn.addActionListener(e -> RegisterPanelHandler.handleRegisterButton(
                nameField, surnameField, emailField, loginField, passwordField, confirmPasswordField, this));

        backBtn.addActionListener(e -> {
            dispose();
            new WelcomeScreen();
        });

        buttons.add(registerBtn);
        buttons.add(backBtn);
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }
}

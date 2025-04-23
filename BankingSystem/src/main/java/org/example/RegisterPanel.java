package org.example;

import javax.swing.*;
import java.awt.*;

class RegisterPanel extends JFrame {
    public RegisterPanel() {
        setTitle("Rejestracja");
        setSize(420, 450);  // Zwiększyłem rozmiar okna
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // Tytuł
        JLabel title = new JLabel("Rejestracja", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Formularz
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new GridLayout(7, 2, 10, 10));  // Zwiększyłem liczbę wierszy
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        form.add(new JLabel("Imię:"));
        JTextField nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Nazwisko:"));
        JTextField surnameField = new JTextField();
        form.add(surnameField);

        form.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Login:"));
        JTextField loginField = new JTextField();
        form.add(loginField);

        form.add(new JLabel("Hasło:"));
        JPasswordField passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Potwierdź hasło:"));
        JPasswordField confirmPasswordField = new JPasswordField();
        form.add(confirmPasswordField);

        add(form, BorderLayout.CENTER);

        // Przycisk rejestracji i powrotu
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        RoundedButton registerBtn = new RoundedButton("Zarejestruj");
        RoundedButton backBtn = new RoundedButton("Powrót");

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {
                if (DBManager.registerUser(name, surname, email, login, password)) {
                    JOptionPane.showMessageDialog(this, "Rejestracja zakończona sukcesem!");
                    dispose();
                    new LoginPanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd rejestracji.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hasła się nie zgadzają.");
            }
        });

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

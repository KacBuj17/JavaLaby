package org.example.gui;

import org.example.gui.utils.RoundedButton;
import org.example.gui.handlers.AdminLoginPanelHandler;

import javax.swing.*;
import java.awt.*;

public class AdminLoginPanel extends JFrame {
    private final JTextField loginField;
    private final JPasswordField passwordField;
    private final RoundedButton loginBtn;
    private final RoundedButton backBtn;

    public AdminLoginPanel() {
        setTitle("Logowanie jako Administrator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Logowanie Administratora", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        form.add(new JLabel("Login:"));
        loginField = new JTextField();
        form.add(loginField);

        form.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        form.add(passwordField);
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        loginBtn = new RoundedButton("Zaloguj");
        backBtn = new RoundedButton("Powrót");

        buttons.add(loginBtn);
        buttons.add(backBtn);
        add(buttons, BorderLayout.SOUTH);

        AdminLoginPanelHandler.attachHandlers(this);

        setVisible(true);
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public RoundedButton getLoginBtn() {
        return loginBtn;
    }

    public RoundedButton getBackBtn() {
        return backBtn;
    }
}

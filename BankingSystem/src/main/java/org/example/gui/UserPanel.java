package org.example.gui;

import org.example.gui.handlers.UserPanelHandler;
import org.example.gui.utils.RoundedButton;
import org.example.db.DBManager;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JFrame {
    private JLabel balanceLabel;
    private RoundedButton depositButton;
    private RoundedButton withdrawButton;
    private RoundedButton transferButton;
    private RoundedButton transfersButton;
    private RoundedButton infoButton;
    private RoundedButton logoutButton;

    public UserPanel() {
        setTitle("Panel użytkownika");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Witaj w swoim banku, " + GUI.currentUserLogin, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        balanceLabel = new JLabel("Stan konta: " + DBManager.getBalance(GUI.currentUserLogin) + " PLN");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balancePanel.add(balanceLabel);
        add(balancePanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        optionsPanel.setBackground(Color.WHITE);

        depositButton = new RoundedButton("Wpłać pieniądze");
        withdrawButton = new RoundedButton("Wypłać pieniądze");
        transferButton = new RoundedButton("Przelew do innego użytkownika");

        optionsPanel.add(depositButton);
        optionsPanel.add(withdrawButton);
        optionsPanel.add(transferButton);
        add(optionsPanel, BorderLayout.CENTER);

        JPanel transfersPanel = new JPanel();
        transfersPanel.setBackground(Color.WHITE);

        transfersButton = new RoundedButton("Historia transakcji");
        infoButton = new RoundedButton("Informacje o koncie");
        logoutButton = new RoundedButton("Wyloguj się");

        transfersPanel.add(transfersButton);
        transfersPanel.add(infoButton);
        transfersPanel.add(logoutButton);
        add(transfersPanel, BorderLayout.SOUTH);

        UserPanelHandler.attachHandlers(this);

        setVisible(true);
    }

    public RoundedButton getDepositButton() {
        return depositButton;
    }

    public RoundedButton getWithdrawButton() {
        return withdrawButton;
    }

    public RoundedButton getTransferButton() {
        return transferButton;
    }

    public RoundedButton getTransfersButton() {
        return transfersButton;
    }

    public RoundedButton getInfoButton() {
        return infoButton;
    }

    public RoundedButton getLogoutButton() {
        return logoutButton;
    }
}

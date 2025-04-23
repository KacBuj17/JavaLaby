package org.example;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:bank.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA busy_timeout = 3000");
            stmt.execute("PRAGMA journal_mode = WAL");

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS admins (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        first_name TEXT NOT NULL,
                        last_name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        login TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        first_name TEXT NOT NULL,
                        last_name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        login TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL,
                        account_number TEXT UNIQUE NOT NULL,
                        balance REAL DEFAULT 0
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS transfers (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        from_account TEXT,
                        to_account TEXT,
                        amount REAL NOT NULL,
                        subject TEXT,
                        timestamp TEXT
                    )
                    """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public static boolean registerUser(String firstName, String lastName, String email, String login, String password) {
        String sql = "INSERT INTO users(first_name, last_name, email, login, password, account_number) VALUES(?, ?, ?, ?, ?, ?)";
        String accountNumber = generateUniqueAccountNumber();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, login);
            pstmt.setString(5, hashPassword(password));
            pstmt.setString(6, accountNumber);
            pstmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean registerAdmin(String firstName, String lastName, String email, String login, String password) {
        String sql = "INSERT INTO admins(first_name, last_name, email, login, password) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, login);
            pstmt.setString(5, hashPassword(password));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean validateLogin(String login, String password) {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hashPassword(password));
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean validateAdminLogin(String login, String password) {
        String sql = "SELECT 1 FROM admins WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hashPassword(password));
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static String generateUniqueAccountNumber() {
        Random rand = new SecureRandom();
        String number;
        do {
            number = "PL" + (1000000000L + rand.nextLong(9000000000L));
        } while (accountNumberExists(number));
        return number;
    }

    private static boolean accountNumberExists(String number) {
        String sql = "SELECT 1 FROM users WHERE account_number = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            return true;
        }
    }

    public static double getBalance(String login) {
        String sql = "SELECT balance FROM users WHERE login = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getDouble("balance") : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static boolean updateBalance(String login, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE login = ?";
        String insertTransfer = "INSERT INTO transfers(from_account, to_account, amount, subject, timestamp) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, login);
            pstmt.executeUpdate();

            String accountNumber = getAccountNumber(login);
            if (accountNumber == null) {
                conn.rollback();
                return false;
            }

            String subject = amount > 0 ? "Wpłata" : "Wypłata";
            PreparedStatement psTransfer = conn.prepareStatement(insertTransfer);
            psTransfer.setString(1, amount > 0 ? null : accountNumber);
            psTransfer.setString(2, amount > 0 ? accountNumber : null);
            psTransfer.setDouble(3, Math.abs(amount));
            psTransfer.setString(4, subject);
            psTransfer.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            psTransfer.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean makeTransfer(String fromLogin, String toAccount, double amount, String subject) {
        String getSender = "SELECT account_number, balance FROM users WHERE login = ?";
        String getReceiver = "SELECT 1 FROM users WHERE account_number = ?";
        String insertTransfer = "INSERT INTO transfers(from_account, to_account, amount, subject, timestamp) VALUES(?, ?, ?, ?, ?)";
        String updateBalance = "UPDATE users SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);

            PreparedStatement psSender = conn.prepareStatement(getSender);
            psSender.setString(1, fromLogin);
            ResultSet rsSender = psSender.executeQuery();
            if (!rsSender.next() || rsSender.getDouble("balance") < amount) {
                conn.rollback();
                return false;
            }
            String fromAccount = rsSender.getString("account_number");

            PreparedStatement psReceiver = conn.prepareStatement(getReceiver);
            psReceiver.setString(1, toAccount);
            if (!psReceiver.executeQuery().next()) {
                conn.rollback();
                return false;
            }

            PreparedStatement psUpdateSender = conn.prepareStatement(updateBalance);
            psUpdateSender.setDouble(1, -amount);
            psUpdateSender.setString(2, fromAccount);
            psUpdateSender.executeUpdate();

            PreparedStatement psUpdateReceiver = conn.prepareStatement(updateBalance);
            psUpdateReceiver.setDouble(1, amount);
            psUpdateReceiver.setString(2, toAccount);
            psUpdateReceiver.executeUpdate();

            PreparedStatement psTransfer = conn.prepareStatement(insertTransfer);
            psTransfer.setString(1, fromAccount);
            psTransfer.setString(2, toAccount);
            psTransfer.setDouble(3, amount);
            psTransfer.setString(4, subject);
            psTransfer.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            psTransfer.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ResultSet getTransfers(String accountNumber) {
        String sql = "SELECT * FROM transfers WHERE from_account = ? OR to_account = ? ORDER BY timestamp DESC";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountNumber);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getAccountNumber(String login) {
        String sql = "SELECT account_number FROM users WHERE login = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("account_number") : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ResultSet getUserInfo(String login) {
        String sql = "SELECT first_name, last_name, email, login, account_number FROM users WHERE login = ?";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, login);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }

    public static ResultSet getAllUsers() {
        String sql = "SELECT login, first_name, last_name, email, account_number, balance FROM users";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }
}

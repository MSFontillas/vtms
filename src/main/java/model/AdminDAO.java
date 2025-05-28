package main.java.model;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminDAO {
    private static AdminDAO instance;
    private Admin currentAdmin;
    
    private AdminDAO() {
        currentAdmin = null;
    }
    
    public static AdminDAO getInstance() {
        if (instance == null) {
            instance = new AdminDAO();
        }
        return instance;
    }
    
    public boolean login(String username, String password) {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM admin_users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, hashPassword(password));
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        currentAdmin = new Admin(
                            rs.getInt("userID"),
                            rs.getString("username")
                        );
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void logout() {
        currentAdmin = null;
    }
    
    public boolean isLoggedIn() {
        return currentAdmin != null;
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
}
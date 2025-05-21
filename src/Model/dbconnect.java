package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect implements AutoCloseable {
    public Connection conn;

    public dbconnect() {
        try {
            // Update these values according to your MySQL configuration
            String url = "jdbc:mysql://localhost:3306/valoranttournamentdb";
            String username = "root";
            String password = "1234";

            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        try (dbconnect db = new dbconnect()) {
        }
    }
}
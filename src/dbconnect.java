import java.sql.*;

public class dbConnect implements AutoCloseable{

    private Connection conn;
	
	public dbConnect() {
        
	    String url = "jdbc:mysql://127.0.0.1:3306/";
        String username = "root";
        String password = "1234";

		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database!");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("An error happened closing the connection to the DB");
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (dbConnect db = new dbConnect()) {
        }
    }


}
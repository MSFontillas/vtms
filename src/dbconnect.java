import java.sql.*;
public class dbconnect implements AutoCloseable{

	public Connection conn;
	
	public dbconnect() {
        try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://127.0.0.1:3306/";
            String username = "root";
            String password = "1234";
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
		try {
			conn.close();
		} catch (Exception e) {
	        System.out.println("An error happened closing the connection to the DB");
			System.out.println(e.getMessage());			
		}
	}

        @Override
    public void close() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		try (dbconnect db = new dbconnect()) {}
	}
}

// TeamDAO.java - CRUD for Teams
import java.sql.*;
import java.util.*;

public class TeamDAO {

    public int teamID;
    public String teamName, coach, region;

    // CREATE
    public void addTeam() throws SQLException {
        dbconnect db = new dbconnect();
        Scanner sc = new Scanner(System.in);

        String query = "INSERT INTO teams (teamName, coach, region) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
            System.out.println("Enter team name: ");
            String teamName = sc.nextLine();

            System.out.println("Enter coach: ");
            String coach = sc.nextLine();

            System.out.println("Enter region: ");
            String region = sc.nextLine();

            stmt.setString(1, teamName);
            stmt.setString(2, coach);
            stmt.setString(3, region);
            stmt.executeUpdate();

            db.disconnect();
            sc.close();

        } catch (Exception e) {
            System.out.println("Error occurred while adding a Team record");
            System.out.println(e.getMessage());
        }

    }

    // READ
    public List<String> getAllTeams() throws SQLException {
        dbconnect db = new dbconnect();
        
        List<String> teams = new ArrayList<>();
        String query = "SELECT * FROM teams";
        try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                teams.add(rs.getString("teamName") + " | " + rs.getString("coach") + " | " + rs.getString("region"));
            }
        }
        db.disconnect();
        return teams;
    }

    // UPDATE
    public void updateTeam() throws SQLException {
        dbconnect db = new dbconnect();
        Scanner sc = new Scanner(System.in);
        
        String query = "UPDATE teams SET teamName = ?, coach = ?, region = ? WHERE teamID = ?";
        try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
            
            System.out.println("Enter teamID: ");
            int teamID = sc.nextInt();

            // Retrieve the team record
            PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM teams WHERE teamID =?");
            rstmt.setInt(1, teamID);
            ResultSet rs = rstmt.executeQuery();

            System.out.println(rs.getInt("teamID") + " | " + rs.getString("TeamName") + " | " + rs.getString("coach") + " | " + rs.getString("region"));
    
            if (!rs.next()) {
                System.out.println("No team record found with this teamID.");
                db.disconnect();
                sc.close();
                return;
            }

            System.out.println("Enter team name: ");
            String teamName = sc.nextLine();

            System.out.println("Enter coach: "); 
            String coach = sc.nextLine();

            System.out.println("Enter region: ");
            String region = sc.nextLine();
            
            if (!teamName.isEmpty()) {
                stmt.setString(1, teamName);
            } else {
                rstmt.setString(1, rs.getString("teamName")); }
            if (!coach.isEmpty()) {
                stmt.setString(2, coach);
            } else {
                rstmt.setString(2, rs.getString("coach")); }
            if (!region.isEmpty()) {
                stmt.setString(3, region);
            } else {
                rstmt.setString(3, rs.getString("region")); }

            stmt.setInt(4, teamID);
            stmt.executeUpdate();
        }
        db.disconnect();
        sc.close();
    }

    // DELETE
    public void deleteTeam() throws SQLException {
        String query = "DELETE FROM teams WHERE teamID = ?";
        dbconnect db = new dbconnect();
        try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
            stmt.setInt(1, teamID);
            stmt.executeUpdate();
        }
    }
}
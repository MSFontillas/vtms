// TeamDAO.java - CRUD for Teams
import java.sql.*;
import java.util.*;

public class TeamDAO {

    private int teamID;
    private String teamName, coach, region;

    // CREATE
    public void addTeam() throws SQLException {
        try (dbConnect db = new dbConnect(); Scanner sc = new Scanner(System.in)) {
            String query = "INSERT INTO teams (teamName, coach, region) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter team name: ");
                teamName = sc.nextLine();

                System.out.println("Enter coach: ");
                coach = sc.nextLine();

                System.out.println("Enter region: ");
                region = sc.nextLine();

                stmt.setString(1, teamName);
                stmt.setString(2, coach);
                stmt.setString(3, region);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while adding a Team record");
            System.out.println(e.getMessage());
        }
    }

    // READ
    public List<Team> getAllTeams() throws SQLException {
        try (dbConnect db = new dbConnect()) {
            List<Team> teams = new ArrayList<>();
            String query = "SELECT * FROM teams";
            try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    teams.add(new Team(rs.getInt("teamID"), rs.getString("teamName"), rs.getString("coach"), rs.getString("region")));
                }
            }
            return teams;
        }
    }

    // UPDATE
    public void updateTeam() throws SQLException {
        try (dbConnect db = new dbConnect(); Scanner sc = new Scanner(System.in)) {
            String query = "UPDATE teams SET teamName = ?, coach = ?, region = ? WHERE teamID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter teamID: ");
                teamID = sc.nextInt();
                sc.nextLine(); // Consume newline left-over

                // Retrieve the team record
                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM teams WHERE teamID =?")) {
                    rstmt.setInt(1, teamID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("teamID") + " | " + rs.getString("teamName") + " | " + rs.getString("coach") + " | " + rs.getString("region"));
                        } else {
                            System.out.println("No team record found with this teamID.");
                            return;
                        }
                    }
                }

                System.out.println("Enter team name: ");
                teamName = sc.nextLine();

                System.out.println("Enter coach: ");
                coach = sc.nextLine();

                System.out.println("Enter region: ");
                region = sc.nextLine();

                if (!teamName.isEmpty()) {
                    stmt.setString(1, teamName);
                } else {
                    stmt.setString(1, getTeamName(teamID));
                }
                if (!coach.isEmpty()) {
                    stmt.setString(2, coach);
                } else {
                    stmt.setString(2, getCoach(teamID));
                }
                if (!region.isEmpty()) {
                    stmt.setString(3, region);
                } else {
                    stmt.setString(3, getRegion(teamID));
                }

                stmt.setInt(4, teamID);
                stmt.executeUpdate();
            }
        }
    }

    // DELETE
    public void deleteTeam() throws SQLException {
        try (dbConnect db = new dbConnect(); Scanner sc = new Scanner(System.in)) {
            String query = "DELETE FROM teams WHERE teamID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter teamID: ");
                teamID = sc.nextInt();

                // Retrieve the team record
                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM teams WHERE teamID =?")) {
                    rstmt.setInt(1, teamID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("teamID") + " | " + rs.getString("teamName") + " | " + rs.getString("coach") + " | " + rs.getString("region"));
                        } else {
                            System.out.println("No team record found with this teamID.");
                            return;
                        }
                    }
                }

                stmt.setInt(1, teamID);
                stmt.executeUpdate();
            }
        }
    }

    private String getTeamName(int teamID) throws SQLException {
        try (dbConnect db = new dbConnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT teamName FROM teams WHERE teamID =?")) {
            stmt.setInt(1, teamID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("teamName");
                }
            }
        }
        return "";
    }

    private String getCoach(int teamID) throws SQLException {
        try (dbConnect db = new dbConnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT coach FROM teams WHERE teamID =?")) {
            stmt.setInt(1, teamID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("coach");
                }
            }
        }
        return "";
    }

    private String getRegion(int teamID) throws SQLException {
        try (dbConnect db = new dbConnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT region FROM teams WHERE teamID =?")) {
            stmt.setInt(1, teamID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("region");
                }
            }
        }
        return "";
    }

    public static class Team {
        private int teamID;
        private String teamName, coach, region;

        public Team(int teamID, String teamName, String coach, String region) {
            this.teamID = teamID;
            this.teamName = teamName;
            this.coach = coach;
            this.region = region;
        }

        public int getTeamID() {
            return teamID;
        }

        public String getTeamName() {
            return teamName;
        }

        public String getCoach() {
            return coach;
        }

        public String getRegion() {
            return region;
        }
    }
}
```
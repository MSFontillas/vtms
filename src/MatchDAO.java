// MatchDAO.java - CRUD for Matches
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MatchDAO {

    private int matchID, teamA_ID, teamB_ID, winner_ID, mapID;;
    private Date matchDate;

    // CREATE
    public void addMatch() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "INSERT INTO matches (teamA_ID, teamB_ID, winner_ID, mapID, matchDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter Team 1 ID: ");
                teamA_ID = sc.nextInt();
                System.out.println("Enter Team 2 ID: ");
                teamB_ID = sc.nextInt();
                System.out.println("Enter Winner ID: ");
                winner_ID = sc.nextInt();
                sc.nextLine(); // Consume newline

                System.out.println("Enter Map ID: ");
                mapID = sc.nextInt();

                System.out.println("Enter Match Date (YYYY-MM-DD): ");
                matchDate = Date.valueOf(sc.nextLine());

                stmt.setInt(1, teamA_ID);
                stmt.setInt(2, teamB_ID);
                stmt.setInt(3, winner_ID);
                stmt.setInt(4, mapID);
                stmt.setDate(5, matchDate);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while adding a Match record");
            System.out.println(e.getMessage());
        }
    }

    // READ
    public List<Match> getAllMatches() throws SQLException {
        try (dbconnect db = new dbconnect()) {
            List<Match> matches = new ArrayList<>();
            String query = "SELECT * FROM matches";
            try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    matches.add(new Match(rs.getInt("matchID"), rs.getInt("teamA_ID"), rs.getInt("teamB_ID"), 
                                          rs.getInt("winner_ID"), rs.getString("mapID"), rs.getDate("matchDate")));
                }
            }
            return matches;
        }
    }

    // UPDATE
    public void updateMatch() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "UPDATE matches SET teamA_ID = ?, teamB_ID = ?, winner_ID = ?, mapID = ?, matchDate = ? WHERE matchID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter matchID: ");
                matchID = sc.nextInt();
                sc.nextLine(); // Consume newline

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM matches WHERE matchID = ?")) {
                    rstmt.setInt(1, matchID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("matchID") + " | " + rs.getInt("teamA_ID") + " | " +
                                    rs.getInt("teamB_ID") + " | " + rs.getInt("winner_ID") + " | " +
                                    rs.getInt("mapID") + " | " + rs.getDate("matchDate"));
                        } else {
                            System.out.println("No match record found with this matchID.");
                            return;
                        }
                    }
                }

                System.out.println("Enter Team 1 ID: ");
                teamA_ID = sc.nextInt();
                System.out.println("Enter Team 2 ID: ");
                teamB_ID = sc.nextInt();
                System.out.println("Enter Winner ID: ");
                winner_ID = sc.nextInt();
                sc.nextLine(); // Consume newline

                System.out.println("Enter Map Name: ");
                mapID = sc.nextInt();
                System.out.println("Enter Match Date (YYYY-MM-DD): ");
                String dateInput = sc.nextLine();
                matchDate = dateInput.isEmpty() ? getMatchDate(matchID) : Date.valueOf(dateInput);

                stmt.setInt(1, teamA_ID);
                stmt.setInt(2, teamB_ID);
                stmt.setInt(3, winner_ID);
                stmt.setInt(4, mapID);
                stmt.setDate(5, matchDate);
                stmt.setInt(6, matchID);
                stmt.executeUpdate();
            }
        }
    }

    // DELETE
    public void deleteMatch() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "DELETE FROM matches WHERE matchID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter matchID: ");
                matchID = sc.nextInt();

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM matches WHERE matchID = ?")) {
                    rstmt.setInt(1, matchID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("matchID") + " | " + rs.getInt("teamA_ID") + " | " +
                                    rs.getInt("teamB_ID") + " | " + rs.getInt("winner_ID") + " | " +
                                    rs.getString("mapID") + " | " + rs.getDate("matchDate"));
                        } else {
                            System.out.println("No match record found with this matchID.");
                            return;
                        }
                    }
                }

                stmt.setInt(1, matchID);
                stmt.executeUpdate();
            }
        }
    }

    private Date getMatchDate(int matchID) throws SQLException {
        try (dbconnect db = new dbconnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT matchDate FROM matches WHERE matchID = ?")) {
            stmt.setInt(1, matchID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("matchDate");
                }
            }
        }
        return null;
    }

    public static class Match {
        private int matchID, teamA_ID, teamB_ID, winner_ID;
        private String mapID;
        private Date matchDate;

        public Match(int matchID, int teamA_ID, int teamB_ID, int winner_ID, String mapID, Date matchDate) {
            this.matchID = matchID;
            this.teamA_ID = teamA_ID;
            this.teamB_ID = teamB_ID;
            this.winner_ID = winner_ID;
            this.mapID = mapID;
            this.matchDate = matchDate;
        }

        public int getMatchID() { return matchID; }
        public int getteamA_ID() { return teamA_ID; }
        public int getteamB_ID() { return teamB_ID; }
        public int getwinner_ID() { return winner_ID; }
        public String getmapID() { return mapID; }
        public Date getMatchDate() { return matchDate; }
    }
}
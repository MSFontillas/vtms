// MatchStatsDAO.java - CRUD for Match Stats
import java.sql.*;
import java.util.*;

public class MatchStatsDAO {

    private int statID, matchID, playerID, kills, deaths, assists;
    private Boolean mvp;

    // CREATE
    public void addMatchStat() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "INSERT INTO match_stats (matchID, playerID, kills, deaths, assists) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter Match ID: ");
                matchID = sc.nextInt();
                System.out.println("Enter Player ID: ");
                playerID = sc.nextInt();
                sc.nextLine(); // Consume newline

                System.out.println("Enter Kills: ");
                kills = sc.nextInt();
                System.out.println("Enter Deaths: ");
                deaths = sc.nextInt();
                System.out.println("Enter Assists: ");
                assists = sc.nextInt();
                System.out.println("MVP: ");
                mvp = sc.nextBoolean();

                stmt.setInt(1, matchID);
                stmt.setInt(2, playerID);
                stmt.setInt(3, kills);
                stmt.setInt(4, deaths);
                stmt.setInt(5, assists);
                stmt.setBoolean(6, mvp);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while adding a Match Stat record");
            System.out.println(e.getMessage());
        }
    }

    // READ
    public List<MatchStat> getAllMatchStats() throws SQLException {
        try (dbconnect db = new dbconnect()) {
            List<MatchStat> stats = new ArrayList<>();
            String query = "SELECT * FROM match_stats";
            try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    stats.add(new MatchStat(rs.getInt("statID"), rs.getInt("matchID"), rs.getInt("playerID"), 
                                            rs.getInt("kills"), rs.getInt("deaths"), rs.getInt("assists")));
                }
            }
            return stats;
        }
    }

    // UPDATE
    public void updateMatchStat() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "UPDATE match_stats SET matchID = ?, playerID = ?, kills = ?, deaths = ?, assists = ? WHERE statID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter statID: ");
                statID = sc.nextInt();
                sc.nextLine(); // Consume newline

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM match_stats WHERE statID = ?")) {
                    rstmt.setInt(1, statID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("statID") + " | " + rs.getInt("matchID") + " | " +
                                    rs.getInt("playerID") + " | " + rs.getInt("kills") + " | " + 
                                    rs.getInt("deaths") + " | " + rs.getInt("assists"));
                        } else {
                            System.out.println("No match stat record found with this statID.");
                            return;
                        }
                    }
                }

                System.out.println("Enter Match ID: ");
                matchID = sc.nextInt();
                System.out.println("Enter Player ID: ");
                playerID = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.println("Enter Kills: ");
                kills = sc.nextInt();
                System.out.println("Enter Deaths: ");
                deaths = sc.nextInt();
                System.out.println("Enter Assists: ");
                assists = sc.nextInt();

                stmt.setInt(1, matchID);
                stmt.setInt(2, playerID);
                stmt.setInt(4, kills);
                stmt.setInt(5, deaths);
                stmt.setInt(6, assists);
                stmt.setInt(7, statID);
                stmt.executeUpdate();
            }
        }
    }

    // DELETE
    public void deleteMatchStat() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "DELETE FROM match_stats WHERE statID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter statID: ");
                statID = sc.nextInt();

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM match_stats WHERE statID = ?")) {
                    rstmt.setInt(1, statID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("statID") + " | " + rs.getInt("matchID") + " | " +
                                    rs.getInt("playerID") + " | " + rs.getString("agent") + " | " +
                                    rs.getInt("kills") + " | " + rs.getInt("deaths") + " | " + rs.getInt("assists"));
                        } else {
                            System.out.println("No match stat record found with this statID.");
                            return;
                        }
                    }
                }

                stmt.setInt(1, statID);
                stmt.executeUpdate();
            }
        }
    }



    public static class MatchStat {
        private int statID, matchID, playerID, kills, deaths, assists;

        public MatchStat(int statID, int matchID, int playerID, int kills, int deaths, int assists) {
            this.statID = statID;
            this.matchID = matchID;
            this.playerID = playerID;
            this.kills = kills;
            this.deaths = deaths;
            this.assists = assists;
        }

        public int getStatID() { return statID; }
        public int getMatchID() { return matchID; }
        public int getPlayerID() { return playerID; }
        public int getKills() { return kills; }
        public int getDeaths() { return deaths; }
        public int getAssists() { return assists; }
    }
}

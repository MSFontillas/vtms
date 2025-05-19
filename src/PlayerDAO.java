// PlayerDAO.java - CRUD for Players
import java.sql.*;
import java.util.*;

public class PlayerDAO {

    private int playerID, teamID;
    private String playerName, ign, role;

    // CREATE
    public void addPlayer() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "INSERT INTO players (playerName, ign, role, teamID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter player name: ");
                playerName = sc.nextLine();

                System.out.println("Enter IGN: ");
                ign = sc.nextLine();

                System.out.println("Enter role: ");
                role = sc.nextLine();

                System.out.println("Enter teamID: ");
                teamID = sc.nextInt();

                stmt.setString(1, playerName);
                stmt.setString(2, ign);
                stmt.setString(3, role);
                stmt.setInt(4, teamID);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while adding a Player record");
            System.out.println(e.getMessage());
        }
    }

    // READ
    public List<Player> getAllPlayers() throws SQLException {
        try (dbconnect db = new dbconnect()) {
            List<Player> players = new ArrayList<>();
            String query = "SELECT * FROM players";
            try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    players.add(new Player(rs.getInt("playerID"), rs.getString("playerName"), rs.getString("ign"), rs.getString("role"), rs.getInt("teamID")));
                }
            }
            return players;
        }
    }

    // UPDATE
    public void updatePlayer() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "UPDATE players SET playerName = ?, ign = ?, role = ?, teamID = ? WHERE playerID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter playerID: ");
                playerID = sc.nextInt();
                sc.nextLine(); // Consume newline

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM players WHERE playerID = ?")) {
                    rstmt.setInt(1, playerID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("playerID") + " | " + rs.getString("playerName") + " | " + rs.getString("ign") + " | " + rs.getString("role") + " | " + rs.getInt("teamID"));
                        } else {
                            System.out.println("No player record found with this playerID.");
                            return;
                        }
                    }
                }

                System.out.println("Enter player name: ");
                playerName = sc.nextLine();
                stmt.setString(1, playerName.isEmpty() ? getPlayerName(playerID) : playerName);

                System.out.println("Enter IGN: ");
                ign = sc.nextLine();
                stmt.setString(2, ign.isEmpty() ? getIGN(playerID) : ign);

                System.out.println("Enter role: ");
                role = sc.nextLine();
                stmt.setString(3, role.isEmpty() ? getRole(playerID) : role);

                System.out.println("Enter teamID: ");
                teamID = sc.nextInt();
                stmt.setInt(4, teamID == 0 ? getTeamID(playerID) : teamID);

                stmt.setInt(5, playerID);
                stmt.executeUpdate();
            }
        }
    }

    // DELETE
    public void deletePlayer() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "DELETE FROM players WHERE playerID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter playerID: ");
                playerID = sc.nextInt();

                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM players WHERE playerID = ?")) {
                    rstmt.setInt(1, playerID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("playerID") + " | " + rs.getString("playerName") + " | " + rs.getString("ign") + " | " + rs.getString("role") + " | " + rs.getInt("teamID"));
                        } else {
                            System.out.println("No player record found with this playerID.");
                            return;
                        }
                    }
                }

                stmt.setInt(1, playerID);
                stmt.executeUpdate();
            }
        }
    }

    // Helper methods to fetch individual attributes
    private String getPlayerName(int playerID) throws SQLException {
        return fetchSingleValue(playerID, "playerName");
    }

    private String getIGN(int playerID) throws SQLException {
        return fetchSingleValue(playerID, "ign");
    }

    private String getRole(int playerID) throws SQLException {
        return fetchSingleValue(playerID, "role");
    }

    private int getTeamID(int playerID) throws SQLException {
        try (dbconnect db = new dbconnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT teamID FROM players WHERE playerID = ?")) {
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("teamID");
                }
            }
        }
        return 0;
    }

    private String fetchSingleValue(int playerID, String column) throws SQLException {
        try (dbconnect db = new dbconnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT " + column + " FROM players WHERE playerID = ?")) {
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(column);
                }
            }
        }
        return "";
    }

    public static class Player {
        private int playerID, teamID;
        private String playerName, ign, role;

        public Player(int playerID, String playerName, String ign, String role, int teamID) {
            this.playerID = playerID;
            this.playerName = playerName;
            this.ign = ign;
            this.role = role;
            this.teamID = teamID;
        }

        public int getPlayerID() {
            return playerID;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getIGN() {
            return ign;
        }

        public String getRole() {
            return role;
        }

        public int getTeamID() {
            return teamID;
        }
    }
}
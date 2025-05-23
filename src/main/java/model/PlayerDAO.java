// Model/PlayerDAO.java
package main.java.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PlayerDAO {
    public void addPlayer(Player player) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO players (playerName, ign, role, teamID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, player.getPlayerName());
                stmt.setString(2, player.getIgn());
                stmt.setString(3, player.getRole());
                stmt.setInt(4, player.getTeamID());
                stmt.executeUpdate();
            }
        }
    }

    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT p.*, t.teamName FROM players p " +
                      "LEFT JOIN teams t ON p.teamID = t.teamID " +
                      "ORDER BY p.teamID";
        try (Statement stmt = db.conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Player player = new Player(
                    rs.getInt("playerID"),
                    rs.getString("playerName"),
                    rs.getString("ign"),
                    rs.getString("role"),
                    rs.getInt("teamID")
                );
                player.setTeamName(rs.getString("teamName")); // Assuming you add this field to Player class
                players.add(player);
            }
        }
    }
    return players;
}

    public void updatePlayer(Player player) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE players SET playerName = ?, ign = ?, role = ?, teamID = ? WHERE playerID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, player.getPlayerName());
                stmt.setString(2, player.getIgn());
                stmt.setString(3, player.getRole());
                stmt.setInt(4, player.getTeamID());
                stmt.setInt(5, player.getPlayerID());
                stmt.executeUpdate();
            }
        }
    }

    public void deletePlayer(int playerID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM players WHERE playerID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, playerID);
                stmt.executeUpdate();
            }
        }
    }

    public Player getPlayerById(int playerID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM players WHERE playerID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, playerID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Player(
                                rs.getInt("playerID"),
                                rs.getString("playerName"),
                                rs.getString("ign"),
                                rs.getString("role"),
                                rs.getInt("teamID")
                        );
                    }
                }
            }
        }
        return null;
    }

    public Map<String, Object> getPlayerStatistics(int playerID) throws SQLException {
        Map<String, Object> statistics = new HashMap<>();
        
        try (dbconnect db = new dbconnect()) {
            // Call stored procedure
            String query = "{CALL GetPlayerStatistics(?)}";
            try (CallableStatement stmt = db.conn.prepareCall(query)) {
                stmt.setInt(1, playerID);
                boolean hasResults = stmt.execute();
                
                // Process basic info
                if (hasResults) {
                    try (ResultSet rs = stmt.getResultSet()) {
                        if (rs.next()) {
                            statistics.put("playerName", rs.getString("playerName"));
                            statistics.put("ign", rs.getString("ign"));
                            statistics.put("role", rs.getString("Role"));
                            statistics.put("teamName", rs.getString("teamName"));
                            statistics.put("kdaRatio", rs.getDouble("kda_ratio"));
                        }
                    }
                    
                    // Get the next result set (match history)
                    if (stmt.getMoreResults()) {
                        List<Map<String, Object>> matchHistory = new ArrayList<>();
                        try (ResultSet rs = stmt.getResultSet()) {
                            while (rs.next()) {
                                Map<String, Object> match = new HashMap<>();
                                match.put("matchID", rs.getInt("matchID"));
                                match.put("date", rs.getDate("Date"));
                                match.put("kills", rs.getInt("kills"));
                                match.put("deaths", rs.getInt("deaths"));
                                match.put("assists", rs.getInt("assists"));
                                match.put("mvp", rs.getBoolean("mvp"));
                                matchHistory.add(match);
                            }
                        }
                        statistics.put("matchHistory", matchHistory);
                    }
                }
            }
        }
        return statistics;
    }
}
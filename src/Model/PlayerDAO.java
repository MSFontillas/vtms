// Model/PlayerDAO.java
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String query = "SELECT * FROM players";
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    players.add(new Player(
                            rs.getInt("playerID"),
                            rs.getString("playerName"),
                            rs.getString("ign"),
                            rs.getString("role"),
                            rs.getInt("teamID")
                    ));
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
}
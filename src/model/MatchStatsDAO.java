// Model/MatchStatsDAO.java
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchStatsDAO {
    public void addMatchStats(MatchStats stats) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO match_stats (matchID, playerID, kills, deaths, assists, mvp) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, stats.getMatchID());
                stmt.setInt(2, stats.getPlayerID());
                stmt.setInt(3, stats.getKills());
                stmt.setInt(4, stats.getDeaths());
                stmt.setInt(5, stats.getAssists());
                stmt.setBoolean(6, stats.isMvp());
                stmt.executeUpdate();
            }
        }
    }

    public List<MatchStats> getAllMatchStats() throws SQLException {
        List<MatchStats> statsList = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM match_stats";
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    statsList.add(new MatchStats(
                            rs.getInt("statID"),
                            rs.getInt("matchID"),
                            rs.getInt("playerID"),
                            rs.getInt("kills"),
                            rs.getInt("deaths"),
                            rs.getInt("assists"),
                            rs.getBoolean("mvp")
                    ));
                }
            }
        }
        return statsList;
    }

    public void updateMatchStats(MatchStats stats) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE match_stats SET matchID = ?, playerID = ?, kills = ?, deaths = ?, assists = ?, mvp = ? WHERE statID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, stats.getMatchID());
                stmt.setInt(2, stats.getPlayerID());
                stmt.setInt(3, stats.getKills());
                stmt.setInt(4, stats.getDeaths());
                stmt.setInt(5, stats.getAssists());
                stmt.setBoolean(6, stats.isMvp());
                stmt.setInt(7, stats.getStatID());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteMatchStats(int statID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM match_stats WHERE statID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, statID);
                stmt.executeUpdate();
            }
        }
    }

    public MatchStats getMatchStatsById(int statID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM match_stats WHERE statID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, statID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new MatchStats(
                                rs.getInt("statID"),
                                rs.getInt("matchID"),
                                rs.getInt("playerID"),
                                rs.getInt("kills"),
                                rs.getInt("deaths"),
                                rs.getInt("assists"),
                                rs.getBoolean("mvp")
                        );
                    }
                }
            }
        }
        return null;
    }
}
// Model/MatchStatsDAO.java
package main.java.model;

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
            String query = """
            SELECT ms.*, p.playerName, p.ign,
                   ta.teamName as teamAName, tb.teamName as teamBName
            FROM match_stats ms
            JOIN players p ON ms.playerID = p.playerID
            JOIN matches m ON ms.matchID = m.match_ID
            JOIN teams ta ON m.teamA_ID = ta.teamID
            JOIN teams tb ON m.teamB_ID = tb.teamID
            ORDER BY ms.statID
            """;
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    MatchStats stats = new MatchStats(
                        rs.getInt("statID"),
                        rs.getInt("matchID"),
                        rs.getInt("playerID"),
                        rs.getInt("kills"),
                        rs.getInt("deaths"),
                        rs.getInt("assists"),
                        rs.getBoolean("mvp")
                    );
                    String displayName = rs.getString("playerName") + " (" + rs.getString("ign") + ")";
                    stats.setPlayerName(displayName);
                    stats.setTeamAName(rs.getString("teamAName"));
                    stats.setTeamBName(rs.getString("teamBName"));
                    statsList.add(stats);
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

    public List<MatchStats> getMatchStatsByMatchId(int matchId) throws SQLException {
        List<MatchStats> statsList = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = """
                SELECT ms.*, p.playerName, p.ign,
                   ta.teamName as teamAName, tb.teamName as teamBName
                FROM match_stats ms
                JOIN players p ON ms.playerID = p.playerID
                JOIN matches m ON ms.matchID = m.match_ID
                JOIN teams ta ON m.teamA_ID = ta.teamID
                JOIN teams tb ON m.teamB_ID = tb.teamID
                WHERE ms.matchID = ?
                """;

            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, matchId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        MatchStats stats = new MatchStats(
                                rs.getInt("statID"),
                                rs.getInt("matchID"),
                                rs.getInt("playerID"),
                                rs.getInt("kills"),
                                rs.getInt("deaths"),
                                rs.getInt("assists"),
                                rs.getBoolean("mvp")
                        );
                        // Set the player name (combining real name and IGN)
                        stats.setPlayerName(rs.getString("playerName") + " (" + rs.getString("ign") + ")");
                        stats.setTeamAName(rs.getString("teamAName"));
                        stats.setTeamBName(rs.getString("teamBName"));
                        statsList.add(stats);
                    }
                }
            }
        }
        return statsList;
    }

    public List<MatchStats> searchMatchStats(String matchIdStr, String playerName, 
                                       String kills, String deaths, String assists, 
                                       Boolean mvp) throws SQLException {
    List<MatchStats> statsList = new ArrayList<>();
    try (dbconnect db = new dbconnect()) {
        StringBuilder queryBuilder = new StringBuilder("""
            SELECT ms.*, p.playerName, p.ign,
                   ta.teamName as teamAName, tb.teamName as teamBName
            FROM match_stats ms
            JOIN players p ON ms.playerID = p.playerID
            JOIN matches m ON ms.matchID = m.match_ID
            JOIN teams ta ON m.teamA_ID = ta.teamID
            JOIN teams tb ON m.teamB_ID = tb.teamID
            WHERE 1=1
        """);
        
        List<Object> params = new ArrayList<>();
        
        // Add match ID condition if provided
        if (!matchIdStr.isEmpty()) {
            queryBuilder.append(" AND ms.matchID = ?");
            params.add(Integer.parseInt(matchIdStr));
        }
        
        // Add player name condition if provided
        if (!playerName.isEmpty()) {
            queryBuilder.append(" AND (LOWER(p.playerName) LIKE LOWER(?) OR LOWER(p.ign) LIKE LOWER(?) OR ");
            queryBuilder.append("LOWER(CONCAT(p.playerName, ' (', p.ign, ')')) LIKE LOWER(?))");
            params.add("%" + playerName + "%");
            params.add("%" + playerName + "%");
            params.add("%" + playerName + "%");
        }
        
        // Rest of the conditions remain the same
        if (!kills.isEmpty()) {
            queryBuilder.append(" AND ms.kills = ?");
            params.add(Integer.parseInt(kills));
        }
        
        if (!deaths.isEmpty()) {
            queryBuilder.append(" AND ms.deaths = ?");
            params.add(Integer.parseInt(deaths));
        }
        
        if (!assists.isEmpty()) {
            queryBuilder.append(" AND ms.assists = ?");
            params.add(Integer.parseInt(assists));
        }
        
        if (mvp != null) {
            queryBuilder.append(" AND ms.mvp = ?");
            params.add(mvp);
        }
        
        queryBuilder.append(" ORDER BY ms.mvp DESC, ms.kills DESC");
        
        try (PreparedStatement stmt = db.conn.prepareStatement(queryBuilder.toString())) {
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MatchStats stats = new MatchStats(
                        rs.getInt("statID"),
                        rs.getInt("matchID"),
                        rs.getInt("playerID"),
                        rs.getInt("kills"),
                        rs.getInt("deaths"),
                        rs.getInt("assists"),
                        rs.getBoolean("mvp")
                    );
                    String displayName = rs.getString("playerName") + " (" + rs.getString("ign") + ")";
                    stats.setPlayerName(displayName);
                    stats.setTeamAName(rs.getString("teamAName"));
                    stats.setTeamBName(rs.getString("teamBName"));
                    statsList.add(stats);
                }
            }
        }
    }
    return statsList;
}
}
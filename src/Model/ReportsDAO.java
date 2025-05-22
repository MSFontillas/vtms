package Model;

import java.sql.*;
import java.util.*;
import java.util.Map;

public class ReportsDAO {
    
    public Map<String, Object> getTeamPerformanceSummary(int teamId) throws SQLException {
        Map<String, Object> summary = new HashMap<>();
        try (dbconnect db = new dbconnect()) {
            String query = """
                SELECT
                    t.teamName,
                    COUNT(DISTINCT m.matchID) as total_matches,
                    COUNT(CASE WHEN m.winner_ID = t.teamID THEN 1 END) as matches_won,
                    ROUND(AVG(ms.kills), 2) as avg_team_kills
                FROM teams t
                LEFT JOIN matches m ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID
                LEFT JOIN match_Stats ms ON m.matchID = ms.matchID
                LEFT JOIN players p ON ms.playerID = p.playerID AND p.teamID = t.teamID
                WHERE t.teamID = ?
                GROUP BY t.teamID, t.teamName
            """;
            
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, teamId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        summary.put("teamName", rs.getString("teamName"));
                        summary.put("totalMatches", rs.getInt("total_matches"));
                        summary.put("matchesWon", rs.getInt("matches_won"));
                        summary.put("avgTeamKills", rs.getDouble("avg_team_kills"));
                    }
                }
            }
        }
        return summary;
    }

    public List<Map<String, Object>> getPlayerAnalytics() throws SQLException {
        List<Map<String, Object>> analytics = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = """
                SELECT
                    p.playerName,
                    p.ign,
                    p.Role,
                    t.teamName,
                    ROUND(AVG(ms.kills), 2) as avg_kills,
                    ROUND(AVG(ms.deaths), 2) as avg_deaths,
                    ROUND(AVG(ms.assists), 2) as avg_assists,
                    ROUND((SUM(ms.kills) + SUM(ms.assists)) / NULLIF(SUM(ms.deaths), 0), 2) as kda_ratio,
                    COUNT(DISTINCT CASE WHEN ms.mvp = 1 THEN ms.matchID END) as mvp_count
                FROM players p
                JOIN teams t ON p.teamID = t.teamID
                LEFT JOIN match_Stats ms ON p.playerID = ms.playerID
                GROUP BY p.playerID, p.playerName, p.ign, p.Role, t.teamName
                ORDER BY kda_ratio DESC
            """;
            
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Map<String, Object> player = new HashMap<>();
                    player.put("playerName", rs.getString("playerName"));
                    player.put("ign", rs.getString("ign"));
                    player.put("role", rs.getString("Role"));
                    player.put("teamName", rs.getString("teamName"));
                    player.put("avgKills", rs.getDouble("avg_kills"));
                    player.put("avgDeaths", rs.getDouble("avg_deaths"));
                    player.put("avgAssists", rs.getDouble("avg_assists"));
                    player.put("kdaRatio", rs.getDouble("kda_ratio"));
                    player.put("mvpCount", rs.getInt("mvp_count"));
                    analytics.add(player);
                }
            }
        }
        return analytics;
    }

    public List<Map<String, Object>> getMapStatistics() throws SQLException {
        List<Map<String, Object>> mapStats = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = """
                SELECT
                    m.mapName,
                    COUNT(mt.matchID) as times_played,
                    ROUND(AVG(ms.kills), 2) as avg_kills_per_match
                FROM maps m
                LEFT JOIN matches mt ON m.mapID = mt.mapID
                LEFT JOIN match_Stats ms ON mt.matchID = ms.matchID
                GROUP BY m.mapID, m.mapName
            """;
            
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mapName", rs.getString("mapName"));
                    map.put("timesPlayed", rs.getInt("times_played"));
                    map.put("avgKillsPerMatch", rs.getDouble("avg_kills_per_match"));
                    mapStats.add(map);
                }
            }
        }
        return mapStats;
    }
}
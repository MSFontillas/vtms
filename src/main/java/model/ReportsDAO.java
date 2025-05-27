package main.java.model;

import java.sql.*;
import java.util.*;
import java.util.Map;

public class ReportsDAO {

    public List<Map<String, Object>> getTeamPerformances() throws SQLException {
        List<Map<String, Object>> performances = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = """
                SELECT
                    t.teamName,
                    COUNT(DISTINCT m.match_ID) as total_matches,
                    COUNT(DISTINCT CASE WHEN m.winner_ID = t.teamID THEN m.match_ID END) as matches_won,
                    ROUND(AVG(ms.kills), 2) as avg_team_kills
                FROM teams t
                LEFT JOIN matches m ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID
                LEFT JOIN players p ON p.teamID = t.teamID
                LEFT JOIN match_Stats ms ON m.match_ID = ms.matchID AND ms.playerID = p.playerID
                GROUP BY t.teamID, t.teamName
                """;

            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Map<String, Object> team = new HashMap<>();
                    team.put("teamName", rs.getString("teamName"));
                    team.put("totalMatches", rs.getInt("total_matches"));
                    team.put("matchesWon", rs.getInt("matches_won"));
                    team.put("avgTeamKills", rs.getDouble("avg_team_kills"));
                    performances.add(team);
                }
            }
        }
        return performances;
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
                    COUNT(DISTINCT mt.match_ID) as times_played,
                    ROUND(AVG(match_kills), 2) as avg_kills_per_match
                FROM maps m
                LEFT JOIN matches mt ON m.mapID = mt.map_ID
                LEFT JOIN (
                    SELECT matchID, SUM(kills) as match_kills
                    FROM match_Stats
                    GROUP BY matchID
                ) match_totals ON mt.match_ID = match_totals.matchID
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
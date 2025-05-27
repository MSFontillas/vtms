package main.java.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    public void addMatch(Match match) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO matches (teamA_ID, teamB_ID, match_date, match_time, winner_ID, map_ID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                setStatement(match, stmt);
                stmt.executeUpdate();
            }
        }
    }

    public void updateMatch(Match match) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE matches SET teamA_ID = ?, teamB_ID = ?, match_date = ?, match_time = ?, winner_ID = ?, map_ID = ? WHERE match_ID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                setStatement(match, stmt);
                stmt.setInt(7, match.getMatchID());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteMatch(int matchID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM matches WHERE match_ID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, matchID);
                stmt.executeUpdate();
            }
        }
    }

    public List<Match> searchMatches(String teamA, String teamB, LocalDate date, String winner, String map) throws SQLException {
        List<Match> matches = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            StringBuilder queryBuilder = new StringBuilder(
                "SELECT m.*, ta.teamName as teamAName, tb.teamName as teamBName, " +
                "w.teamName as winnerName, mp.mapName " +
                "FROM matches m " +
                "JOIN teams ta ON m.teamA_ID = ta.teamID " +
                "JOIN teams tb ON m.teamB_ID = tb.teamID " +
                "JOIN teams w ON m.winner_ID = w.teamID " +
                "JOIN maps mp ON m.map_ID = mp.mapID " +
                "WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            if (!teamA.isEmpty()) {
                queryBuilder.append(" AND ta.teamName LIKE ?");
                params.add("%" + teamA + "%");
            }
            if (!teamB.isEmpty()) {
                queryBuilder.append(" AND tb.teamName LIKE ?");
                params.add("%" + teamB + "%");
            }
            if (date != null) {
                queryBuilder.append(" AND m.match_date = ?");
                params.add(Date.valueOf(date));
            }
            if (!winner.isEmpty()) {
                queryBuilder.append(" AND w.teamName LIKE ?");
                params.add("%" + winner + "%");
            }
            if (!map.isEmpty()) {
                queryBuilder.append(" AND mp.mapName LIKE ?");
                params.add("%" + map + "%");
            }

            try (PreparedStatement stmt = db.conn.prepareStatement(queryBuilder.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Match match = new Match(
                            rs.getInt("match_ID"),
                            rs.getInt("teamA_ID"),
                            rs.getInt("teamB_ID"),
                            rs.getDate("match_date").toLocalDate(),
                            rs.getTime("match_time").toLocalTime(),
                            rs.getInt("winner_ID"),
                            rs.getInt("map_ID")
                        );
                        match.setTeamAName(rs.getString("teamAName"));
                        match.setTeamBName(rs.getString("teamBName"));
                        match.setWinnerName(rs.getString("winnerName"));
                        match.setMapName(rs.getString("mapName"));
                        matches.add(match);
                    }
                }
            }
        }
        return matches;
    }

    public List<Match> searchMatchesbyTeam(String teamA, String teamB) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT m.*, t1.teamName as teamAName, t2.teamName as teamBName, " +
                            "w.teamName as winnerName, mp.mapName " +
                            "FROM matches m " +
                            "JOIN teams t1 ON m.teamAID = t1.teamID " +
                            "JOIN teams t2 ON m.teamBID = t2.teamID " +
                            "JOIN teams w ON m.winnerID = w.teamID " +
                            "JOIN maps mp ON m.mapID = mp.mapID " +
                            "WHERE 1=1"
            );
            List<Object> params = new ArrayList<>();

            if (teamA != null && !teamA.trim().isEmpty()) {
                queryBuilder.append(" AND t1.teamName LIKE ?");
                params.add("%" + teamA + "%");
            }
            if (teamB != null && !teamB.trim().isEmpty()) {
                queryBuilder.append(" AND t2.teamName LIKE ?");
                params.add("%" + teamB + "%");
            }

            try (PreparedStatement stmt = db.conn.prepareStatement(queryBuilder.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }

                List<Match> matches = new ArrayList<>();
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Match match = new Match(
                                rs.getInt("matchID"),
                                rs.getInt("teamAID"),
                                rs.getInt("teamBID"),
                                rs.getDate("matchDate").toLocalDate(),
                                rs.getTime("matchTime").toLocalTime(),
                                rs.getInt("winnerID"),
                                rs.getInt("mapID")
                        );
                        match.setTeamAName(rs.getString("teamAName"));
                        match.setTeamBName(rs.getString("teamBName"));
                        match.setWinnerName(rs.getString("winnerName"));
                        match.setMapName(rs.getString("mapName"));
                        matches.add(match);
                    }
                }
                return matches;
            }
        }
    }

    private void setStatement(Match match, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, match.getTeamAID());
        stmt.setInt(2, match.getTeamBID());
        stmt.setDate(3, Date.valueOf(match.getMatchDate()));
        stmt.setTime(4, Time.valueOf(match.getMatchTime()));
        stmt.setInt(5, match.getWinnerID());
        stmt.setInt(6, match.getMapID());
    }
}
// Model/MatchDAO.java
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    public void addMatch(Match match) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO matches (teamA_ID, teamB_ID, winner_ID, mapID, matchDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, match.getTeamA_ID());
                stmt.setInt(2, match.getTeamB_ID());
                stmt.setInt(3, match.getWinner_ID());
                stmt.setInt(4, match.getMapID());
                stmt.setDate(5, match.getMatchDate());
                stmt.executeUpdate();
            }
        }
    }

    public List<Match> getAllMatches() throws SQLException {
        List<Match> matches = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM matches";
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    matches.add(new Match(
                            rs.getInt("matchID"),
                            rs.getInt("teamA_ID"),
                            rs.getInt("teamB_ID"),
                            rs.getInt("winner_ID"),
                            rs.getInt("mapID"),
                            rs.getDate("matchDate")
                    ));
                }
            }
        }
        return matches;
    }

    public void updateMatch(Match match) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE matches SET teamA_ID = ?, teamB_ID = ?, winner_ID = ?, mapID = ?, matchDate = ? WHERE matchID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, match.getTeamA_ID());
                stmt.setInt(2, match.getTeamB_ID());
                stmt.setInt(3, match.getWinner_ID());
                stmt.setInt(4, match.getMapID());
                stmt.setDate(5, match.getMatchDate());
                stmt.setInt(6, match.getMatchID());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteMatch(int matchID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM matches WHERE matchID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, matchID);
                stmt.executeUpdate();
            }
        }
    }

    public Match getMatchById(int matchID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM matches WHERE matchID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, matchID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Match(
                                rs.getInt("matchID"),
                                rs.getInt("teamA_ID"),
                                rs.getInt("teamB_ID"),
                                rs.getInt("winner_ID"),
                                rs.getInt("mapID"),
                                rs.getDate("matchDate")
                        );
                    }
                }
            }
        }
        return null;
    }
}
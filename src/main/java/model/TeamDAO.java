package main.java.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    public void addTeam(Team team) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO teams (teamName, coach, region) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, team.getTeamName());
                stmt.setString(2, team.getCoach());
                stmt.setString(3, team.getRegion());
                stmt.executeUpdate();
            }
        }
    }

    public List<Team> getAllTeams() throws SQLException {
        List<Team> teams = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM teams";
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Team team = new Team(
                        rs.getInt("teamID"),
                        rs.getString("teamName"),
                        rs.getString("coach"),
                        rs.getString("region")
                    );
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    public void updateTeam(Team team) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE teams SET teamName = ?, coach = ?, region = ? WHERE teamID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, team.getTeamName());
                stmt.setString(2, team.getCoach());
                stmt.setString(3, team.getRegion());
                stmt.setInt(4, team.getTeamID());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteTeam(int teamID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM teams WHERE teamID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, teamID);
                stmt.executeUpdate();
            }
        }
    }

    public Team getTeamById(int teamID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM teams WHERE teamID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, teamID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Team(
                            rs.getInt("teamID"),
                            rs.getString("teamName"),
                            rs.getString("coach"),
                            rs.getString("region")
                        );
                    }
                }
            }
        }
        return null;
    }

    public int getTeamIdByName(String teamName) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT teamID FROM teams WHERE teamName = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, teamName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("teamID");
                    }
                }
            }
        }
        throw new SQLException("Team not found: " + teamName);
    }

}
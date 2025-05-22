// Model/MapDAO.java
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapDAO {
    public void addMap(Map map) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "INSERT INTO maps (mapName) VALUES (?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, map.getMapName());
                stmt.executeUpdate();
            }
        }
    }

    public List<Map> getAllMaps() throws SQLException {
        List<Map> maps = new ArrayList<>();
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM maps";
            try (Statement stmt = db.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    maps.add(new Map(
                            rs.getInt("mapID"),
                            rs.getString("mapName")
                    ));
                }
            }
        }
        return maps;
    }

    public void updateMap(Map map) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "UPDATE maps SET mapName = ? WHERE mapID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setString(1, map.getMapName());
                stmt.setInt(2, map.getMapID());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteMap(int mapID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "DELETE FROM maps WHERE mapID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, mapID);
                stmt.executeUpdate();
            }
        }
    }

    public Map getMapById(int mapID) throws SQLException {
        try (dbconnect db = new dbconnect()) {
            String query = "SELECT * FROM maps WHERE mapID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                stmt.setInt(1, mapID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Map(
                                rs.getInt("mapID"),
                                rs.getString("mapName")
                        );
                    }
                }
            }
        }
        return null;
    }
}
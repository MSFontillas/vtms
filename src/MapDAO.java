// MapDAO.java - CRUD for Maps
import java.sql.*;
import java.util.*;

public class MapDAO {

    private int mapID;
    private String mapName;

    // CREATE
    public void addMap() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "INSERT INTO maps (mapName) VALUES (?)";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter map name: ");
                mapName = sc.nextLine();
                stmt.setString(1, mapName);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while adding a Map record");
            System.out.println(e.getMessage());
        }
    }

    // READ
    public List<Map> getAllMaps() throws SQLException {
        try (dbconnect db = new dbconnect()) {
            List<Map> maps = new ArrayList<>();
            String query = "SELECT * FROM maps";
            try (Statement stmt = db.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    maps.add(new Map(rs.getInt("mapID"), rs.getString("mapName")));
                }
            }
            return maps;
        }
    }

    // UPDATE
    public void updateMap() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "UPDATE maps SET mapName = ? WHERE mapID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter mapID: ");
                mapID = sc.nextInt();
                sc.nextLine(); // Consume newline

                // Retrieve the map record
                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM maps WHERE mapID = ?")) {
                    rstmt.setInt(1, mapID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("mapID") + " | " + rs.getString("mapName"));
                        } else {
                            System.out.println("No map record found with this mapID.");
                            return;
                        }
                    }
                }

                System.out.println("Enter new map name (leave blank to keep current): ");
                mapName = sc.nextLine();
                stmt.setString(1, mapName.isEmpty() ? getMapName(mapID) : mapName);

                stmt.setInt(2, mapID);
                stmt.executeUpdate();
            }
        }
    }

    // DELETE
    public void deleteMap() throws SQLException {
        try (dbconnect db = new dbconnect(); Scanner sc = new Scanner(System.in)) {
            String query = "DELETE FROM maps WHERE mapID = ?";
            try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
                System.out.println("Enter mapID: ");
                mapID = sc.nextInt();

                // Retrieve the map record
                try (PreparedStatement rstmt = db.conn.prepareStatement("SELECT * FROM maps WHERE mapID = ?")) {
                    rstmt.setInt(1, mapID);
                    try (ResultSet rs = rstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(rs.getInt("mapID") + " | " + rs.getString("mapName"));
                        } else {
                            System.out.println("No map record found with this mapID.");
                            return;
                        }
                    }
                }

                stmt.setInt(1, mapID);
                stmt.executeUpdate();
            }
        }
    }

    // Helper method to fetch the map name if left empty during update
    private String getMapName(int mapID) throws SQLException {
        try (dbconnect db = new dbconnect(); PreparedStatement stmt = db.conn.prepareStatement("SELECT mapName FROM maps WHERE mapID = ?")) {
            stmt.setInt(1, mapID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mapName");
                }
            }
        }
        return "";
    }

    public static class Map {
        private int mapID;
        private String mapName;

        public Map(int mapID, String mapName) {
            this.mapID = mapID;
            this.mapName = mapName;
        }

        public int getMapID() {
            return mapID;
        }

        public String getMapName() {
            return mapName;
        }
    }
}
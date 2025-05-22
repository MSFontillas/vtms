// Model/Map.java
package model;

public class Map {
    private int mapID;
    private String mapName;

    public Map(int mapID, String mapName) {
        this.mapID = mapID;
        this.mapName = mapName;
    }

    // Default constructor
    public Map() {}

    // Getters and Setters
    public int getMapID() { return mapID; }
    public void setMapID(int mapID) { this.mapID = mapID; }

    public String getMapName() { return mapName; }
    public void setMapName(String mapName) { this.mapName = mapName; }
}
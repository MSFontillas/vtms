package main.java.model;

import java.util.Map;

public class MapStatistics {
    private final String mapName;
    private final int timesPlayed;
    private final double avgKillsPerMatch;

    public MapStatistics(Map<String, Object> data) {
        this.mapName = (String) data.get("mapName");
        this.timesPlayed = (int) data.get("timesPlayed");
        this.avgKillsPerMatch = (double) data.get("avgKillsPerMatch");
    }

    // Getters
    public String getMapName() { return mapName; }
    public int getTimesPlayed() { return timesPlayed; }
    public double getAvgKillsPerMatch() { return avgKillsPerMatch; }
}

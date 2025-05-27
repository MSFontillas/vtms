package main.java.model;

import java.util.Map;

public class PlayerAnalytics {
    private final String playerName;
    private final String ign;
    private final String role;
    private final String teamName;
    private final double avgKills;
    private final double avgDeaths;
    private final double avgAssists;
    private final double kdaRatio;
    private final int mvpCount;

    public PlayerAnalytics(Map<String, Object> data) {
        this.playerName = (String) data.get("playerName");
        this.ign = (String) data.get("ign");
        this.role = (String) data.get("role");
        this.teamName = (String) data.get("teamName");
        this.avgKills = (double) data.get("avgKills");
        this.avgDeaths = (double) data.get("avgDeaths");
        this.avgAssists = (double) data.get("avgAssists");
        this.kdaRatio = (double) data.get("kdaRatio");
        this.mvpCount = (int) data.get("mvpCount");
    }

    // Getters
    public String getPlayerName() { return playerName; }
    public String getIgn() { return ign; }
    public String getRole() { return role; }
    public String getTeamName() { return teamName; }
    public double getAvgKills() { return avgKills; }
    public double getAvgDeaths() { return avgDeaths; }
    public double getAvgAssists() { return avgAssists; }
    public double getKdaRatio() { return kdaRatio; }
    public int getMvpCount() { return mvpCount; }
}

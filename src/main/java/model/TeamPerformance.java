package main.java.model;

import java.util.Map;

public class TeamPerformance {
    private final String teamName;
    private final int totalMatches;
    private final int matchesWon;
    private final double avgTeamKills;

    public TeamPerformance(Map<String, Object> data) {
        this.teamName = (String) data.get("teamName");
        this.totalMatches = (int) data.get("totalMatches");
        this.matchesWon = (int) data.get("matchesWon");
        this.avgTeamKills = (double) data.get("avgTeamKills");
    }

    // Getters
    public String getTeamName() { return teamName; }
    public int getTotalMatches() { return totalMatches; }
    public int getMatchesWon() { return matchesWon; }
    public double getAvgTeamKills() { return avgTeamKills; }
}


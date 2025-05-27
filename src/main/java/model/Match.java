package main.java.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Match {
    private int matchID;
    private int teamAID;
    private int teamBID;
    private String teamAName;
    private String teamBName;
    private LocalDate matchDate;
    private LocalTime matchTime;
    private int winnerID;
    private String winnerName;
    private int mapID;
    private String mapName;

    public Match(int matchID, int teamAID, int teamBID, LocalDate matchDate, LocalTime matchTime, int winnerID, int mapID) {
        this.matchID = matchID;
        this.teamAID = teamAID;
        this.teamBID = teamBID;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.winnerID = winnerID;
        this.mapID = mapID;
    }

    // Default constructor
    public Match() {}

    // Getters and Setters
    public int getMatchID() { return matchID; }
    public void setMatchID(int matchID) { this.matchID = matchID; }

    public int getTeamAID() { return teamAID; }
    public void setTeamAID(int teamAID) { this.teamAID = teamAID; }

    public int getTeamBID() { return teamBID; }
    public void setTeamBID(int teamBID) { this.teamBID = teamBID; }

    public String getTeamAName() { return teamAName; }
    public void setTeamAName(String teamAName) { this.teamAName = teamAName; }

    public String getTeamBName() { return teamBName; }
    public void setTeamBName(String teamBName) { this.teamBName = teamBName; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public LocalTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalTime matchTime) { this.matchTime = matchTime; }

    public int getWinnerID() { return winnerID; }
    public void setWinnerID(int winnerID) { this.winnerID = winnerID; }

    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public int getMapID() { return mapID; }
    public void setMapID(int mapID) { this.mapID = mapID; }

    public String getMapName() { return mapName; }
    public void setMapName(String mapName) { this.mapName = mapName; }
}
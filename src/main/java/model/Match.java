package main.java.model;

import java.sql.Date;
import java.sql.Time;

public class Match {
    private int matchID;
    private int teamA_ID;
    private int teamB_ID;
    private int winner_ID;
    private int mapID;
    private Date matchDate;
    private Time matchTime;
    private String teamAName;
    private String teamBName;
    private String winnerName;
    private String mapName;

    public Match() {}

    // Constructor for creating a new match
    public Match(int teamA_ID, int teamB_ID, int winner_ID, int mapID, Date matchDate, Time matchTime) {
        this.teamA_ID = teamA_ID;
        this.teamB_ID = teamB_ID;
        this.winner_ID = winner_ID;
        this.mapID = mapID;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
    }

    // Constructor for an existing match
    public Match(int matchID, int teamA_ID, int teamB_ID, int winner_ID, int mapID, Date matchDate, Time matchTime) {
        this.matchID = matchID;
        this.teamA_ID = teamA_ID;
        this.teamB_ID = teamB_ID;
        this.winner_ID = winner_ID;
        this.mapID = mapID;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
    }

    // Getters and setters
    public int getMatchID() { return matchID; }
    public void setMatchID(int matchID) { this.matchID = matchID; }

    public int getTeamA_ID() { return teamA_ID; }
    public void setTeamA_ID(int teamA_ID) { this.teamA_ID = teamA_ID; }

    public int getTeamB_ID() { return teamB_ID; }
    public void setTeamB_ID(int teamB_ID) { this.teamB_ID = teamB_ID; }

    public int getWinner_ID() { return winner_ID; }
    public void setWinner_ID(int winner_ID) { this.winner_ID = winner_ID; }

    public int getMapID() { return mapID; }
    public void setMapID(int mapID) { this.mapID = mapID; }

    public Date getMatchDate() { return matchDate; }
    public void setMatchDate(Date matchDate) { this.matchDate = matchDate; }

    public Time getMatchTime() { return matchTime; }
    public void setMatchTime(Time matchTime) { this.matchTime = matchTime; }

    public String getTeamAName() { return teamAName; }
    public void setTeamAName(String teamAName) { this.teamAName = teamAName; }

    public String getTeamBName() { return teamBName; }
    public void setTeamBName(String teamBName) { this.teamBName = teamBName; }

    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public String getMapName() { return mapName; }
    public void setMapName(String mapName) { this.mapName = mapName; }
}
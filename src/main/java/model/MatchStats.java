// Model/MatchStats.java
package main.java.model;

public class MatchStats {
    private int statID;
    private int matchID;
    private int playerID;
    private int kills;
    private int deaths;
    private int assists;
    private boolean mvp;

    public MatchStats(int statID, int matchID, int playerID, int kills, int deaths, int assists, boolean mvp) {
        this.statID = statID;
        this.matchID = matchID;
        this.playerID = playerID;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.mvp = mvp;
    }

    // Default constructor
    public MatchStats() {}

    // Getters and Setters
    public int getStatID() { return statID; }
    public void setStatID(int statID) { this.statID = statID; }

    public int getMatchID() { return matchID; }
    public void setMatchID(int matchID) { this.matchID = matchID; }

    public int getPlayerID() { return playerID; }
    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public int getKills() { return kills; }
    public void setKills(int kills) { this.kills = kills; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }

    public boolean isMvp() { return mvp; }
    public void setMvp(boolean mvp) { this.mvp = mvp; }
}
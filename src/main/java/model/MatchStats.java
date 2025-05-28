package main.java.model;

public class MatchStats {
    private int statID;
    private int matchID;
    private int playerID;
    private int kills;
    private int deaths;
    private int assists;
    private boolean mvp;
    private String playerName;
    private String teamName;
    private String teamAName;
    private String teamBName;

    // Update constructor
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

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getTeamAName() { return teamAName; }
    public void setTeamAName(String teamAName) { this.teamAName = teamAName; }

    public String getTeamBName() { return teamBName; }
    public void setTeamBName(String teamBName) { this.teamBName = teamBName; }

    public void validateInput() throws IllegalArgumentException {
        if (matchID <= 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        if (playerID <= 0) {
            throw new IllegalArgumentException("Invalid player ID");
        }
        if (kills < 0) {
            throw new IllegalArgumentException("Kills cannot be negative");
        }
        if (deaths < 0) {
            throw new IllegalArgumentException("Deaths cannot be negative");
        }
        if (assists < 0) {
            throw new IllegalArgumentException("Assists cannot be negative");
        }
    }

}
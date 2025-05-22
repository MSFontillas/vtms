package model;

// Model/Player.java
public class Player {
    private int playerID;
    private String playerName;
    private String ign;
    private String role;
    private int teamID;

    public Player(int playerID, String playerName, String ign, String role, int teamID) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.ign = ign;
        this.role = role;
        this.teamID = teamID;
    }

    // Default constructor
    public Player() {}

    // Getters and Setters
    public int getPlayerID() { return playerID; }
    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getIgn() { return ign; }
    public void setIgn(String ign) { this.ign = ign; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getTeamID() { return teamID; }
    public void setTeamID(int teamID) { this.teamID = teamID; }
}
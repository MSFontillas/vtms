// Model/Team.java
package Model;

public class Team {
    private int teamID;
    private String teamName;
    private String coach;
    private String region;

    // Constructor
    public Team(int teamID, String teamName, String coach, String region) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.coach = coach;
        this.region = region;
    }

    // Default constructor
    public Team() {}

    // Getters and Setters
    public int getTeamID() { return teamID; }
    public void setTeamID(int teamID) { this.teamID = teamID; }
    
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    
    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
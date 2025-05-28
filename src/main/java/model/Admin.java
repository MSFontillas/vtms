package main.java.model;

public class Admin {
    private final int userID;
    private final String username;
    
    public Admin(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }
    
    public int getUserID() { return userID; }
    public String getUsername() { return username; }
}
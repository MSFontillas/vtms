package main.java.view;
import main.java.model.Team;

import java.util.List;
import java.util.Scanner;

public class TeamView {
    private final Scanner scanner;

    public TeamView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--- Manage Teams ---");
        System.out.println("1. Add Team");
        System.out.println("2. View All Teams");
        System.out.println("3. Update Team");
        System.out.println("4. Delete Team");
        System.out.println("5. Back");
        System.out.print("Choose an option: ");
    }

    public Team getTeamInput() {
        Team team = new Team();

        scanner.nextLine();

        System.out.println("Enter team name: ");
        team.setTeamName(scanner.nextLine());
        
        System.out.println("Enter coach: ");
        team.setCoach(scanner.nextLine());
        
        System.out.println("Enter region: ");
        team.setRegion(scanner.nextLine());
        
        return team;
    }

    public int getTeamId() {
        System.out.println("Enter teamID: ");
        return scanner.nextInt();
    }

    public void displayTeams(List<Team> teams) {
        System.out.println("\n--- Teams List ---");
        System.out.printf("| %-5s | %-20s | %-20s | %-20s |\n", "ID", "Team Name", "Coach", "Region");
        System.out.println("-".repeat(75));
        
        for (Team team : teams) {
            System.out.printf("| %-5d | %-20s | %-20s | %-20s |\n",
                team.getTeamID(),
                team.getTeamName(),
                team.getCoach(),
                team.getRegion());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public Team getUpdateTeamInput(Team existingTeam) {
        Team team = new Team();
        scanner.nextLine();

        System.out.println("Enter team name (press Enter to keep current: " + existingTeam.getTeamName() + "): ");
        String teamName = scanner.nextLine();
        team.setTeamName(teamName.isEmpty() ? existingTeam.getTeamName() : teamName);
        
        System.out.println("Enter coach (press Enter to keep current: " + existingTeam.getCoach() + "): ");
        String coach = scanner.nextLine();
        team.setCoach(coach.isEmpty() ? existingTeam.getCoach() : coach);
        
        System.out.println("Enter region (press Enter to keep current: " + existingTeam.getRegion() + "): ");
        String region = scanner.nextLine();
        team.setRegion(region.isEmpty() ? existingTeam.getRegion() : region);
        
        return team;
    }
}
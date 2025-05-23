// View/MatchView.java
package main.java.view;

import main.java.model.Match;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class MatchView {
    private final Scanner scanner;

    public MatchView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--- Manage Matches ---");
        System.out.println("1. Add Match");
        System.out.println("2. View All Matches");
        System.out.println("3. Update Match");
        System.out.println("4. Delete Match");
        System.out.println("5. Back");
        System.out.print("Choose an option: ");
    }

    public Match getMatchInput() {

        scanner.nextLine();

        System.out.println("Enter Team A ID: ");
        int teamAId = scanner.nextInt();

        System.out.println("Enter Team B ID: ");
        int teamBId = scanner.nextInt();

        System.out.println("Enter Winner ID: ");
        int winnerId = scanner.nextInt();

        System.out.println("Enter Map ID: ");
        int mapId = scanner.nextInt();

        System.out.println("Enter Match Date (YYYY-MM-DD): ");
        String dateStr = scanner.next();
        Date matchDate = Date.valueOf(dateStr);

        System.out.println("Enter Match Time (HH:MM:SS): ");
        String timeStr = scanner.next();
        Time matchTime = Time.valueOf(timeStr);

        return new Match(teamAId, teamBId, winnerId, mapId, matchDate, matchTime);
    }

    public Match getUpdateMatchInput(Match existingMatch) {
        Match match = new Match();
        scanner.nextLine();

        System.out.println("Enter Team A ID (press Enter to keep current: " + existingMatch.getTeamA_ID() + "): ");
        String teamAInput = scanner.nextLine();
        match.setTeamA_ID(teamAInput.isEmpty() ? existingMatch.getTeamA_ID() : Integer.parseInt(teamAInput));

        System.out.println("Enter Team B ID (press Enter to keep current: " + existingMatch.getTeamB_ID() + "): ");
        String teamBInput = scanner.nextLine();
        match.setTeamB_ID(teamBInput.isEmpty() ? existingMatch.getTeamB_ID() : Integer.parseInt(teamBInput));

        System.out.println("Enter Winner ID (press Enter to keep current: " + existingMatch.getWinner_ID() + "): ");
        String winnerInput = scanner.nextLine();
        match.setWinner_ID(winnerInput.isEmpty() ? existingMatch.getWinner_ID() : Integer.parseInt(winnerInput));

        System.out.println("Enter Map ID (press Enter to keep current: " + existingMatch.getMapID() + "): ");
        String mapInput = scanner.nextLine();
        match.setMapID(mapInput.isEmpty() ? existingMatch.getMapID() : Integer.parseInt(mapInput));

        System.out.println("Enter match date (YYYY-MM-DD) (press Enter to keep current: " + existingMatch.getMatchDate() + "): ");
        String dateInput = scanner.nextLine();
        match.setMatchDate(dateInput.isEmpty() ? existingMatch.getMatchDate() : Date.valueOf(dateInput));

        System.out.println("Enter match time (HH:MM:SS) (press Enter to keep current: " + existingMatch.getMatchTime() + "): ");
        String timeInput = scanner.nextLine();
        match.setMatchTime(timeInput.isEmpty() ? existingMatch.getMatchTime() : Time.valueOf(timeInput));

        return match;
    }

    public int getMatchId() {
        System.out.println("Enter matchID: ");
        return scanner.nextInt();
    }

    public void displayMatches(List<Match> matches) {
        System.out.println("\n--- Matches List ---");
        System.out.printf("| %-5s | %-8s | %-8s | %-9s | %-7s | %-12s |\n",
                "ID", "Team A", "Team B", "Winner", "Map", "Date");
        System.out.println("-".repeat(65));

        for (Match match : matches) {
            System.out.printf("| %-5d | %-8d | %-8d | %-9d | %-7d | %-12s |\n",
                    match.getMatchID(),
                    match.getTeamA_ID(),
                    match.getTeamB_ID(),
                    match.getWinner_ID(),
                    match.getMapID(),
                    match.getMatchDate());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
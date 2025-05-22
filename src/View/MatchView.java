// View/MatchView.java
package View;

import Model.Match;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class MatchView {
    private Scanner scanner;

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
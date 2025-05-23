// View/MatchStatsView.java
package main.java.view;

import main.java.model.MatchStats;

import java.util.List;
import java.util.Scanner;

public class MatchStatsView {
    private final Scanner scanner;

    public MatchStatsView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--- Manage Match Stats ---");
        System.out.println("1. Add Match Stat");
        System.out.println("2. View All Match Stats");
        System.out.println("3. Update Match Stat");
        System.out.println("4. Delete Match Stat");
        System.out.println("5. Back");
        System.out.print("Choose an option: ");
    }

    public MatchStats getMatchStatsInput() {
        MatchStats stats = new MatchStats();

        scanner.nextLine();

        System.out.println("Enter Match ID: ");
        stats.setMatchID(scanner.nextInt());

        System.out.println("Enter Player ID: ");
        stats.setPlayerID(scanner.nextInt());

        System.out.println("Enter Kills: ");
        stats.setKills(scanner.nextInt());

        System.out.println("Enter Deaths: ");
        stats.setDeaths(scanner.nextInt());

        System.out.println("Enter Assists: ");
        stats.setAssists(scanner.nextInt());

        System.out.println("MVP (true/false): ");
        stats.setMvp(scanner.nextBoolean());

        return stats;
    }

    public MatchStats getUpdateMatchStatsInput(MatchStats existingStats) {
        MatchStats stats = new MatchStats();
        scanner.nextLine();

        System.out.println("Enter kills (press Enter to keep current: " + existingStats.getKills() + "): ");
        String killsInput = scanner.nextLine();
        stats.setKills(killsInput.isEmpty() ? existingStats.getKills() : Integer.parseInt(killsInput));

        System.out.println("Enter deaths (press Enter to keep current: " + existingStats.getDeaths() + "): ");
        String deathsInput = scanner.nextLine();
        stats.setDeaths(deathsInput.isEmpty() ? existingStats.getDeaths() : Integer.parseInt(deathsInput));

        System.out.println("Enter assists (press Enter to keep current: " + existingStats.getAssists() + "): ");
        String assistsInput = scanner.nextLine();
        stats.setAssists(assistsInput.isEmpty() ? existingStats.getAssists() : Integer.parseInt(assistsInput));

        System.out.println("Was MVP? (y/n) (press Enter to keep current: " + (existingStats.isMvp() ? "y" : "n") + "): ");
        String mvpInput = scanner.nextLine();
        stats.setMvp(mvpInput.isEmpty() ? existingStats.isMvp() : mvpInput.equalsIgnoreCase("y"));

        return stats;
    }

    public int getStatId() {
        System.out.println("Enter statID: ");
        return scanner.nextInt();
    }

    public void displayMatchStats(List<MatchStats> statsList) {
        System.out.println("\n--- Match Stats List ---");
        System.out.printf("| %-5s | %-8s | %-9s | %-6s | %-7s | %-8s | %-5s |\n",
                "ID", "Match", "Player", "Kills", "Deaths", "Assists", "MVP");
        System.out.println("-".repeat(70));

        for (MatchStats stats : statsList) {
            System.out.printf("| %-5d | %-8d | %-9d | %-6d | %-7d | %-8d | %-5d |\n",
                    stats.getStatID(),
                    stats.getMatchID(),
                    stats.getPlayerID(),
                    stats.getKills(),
                    stats.getDeaths(),
                    stats.getAssists(),
                    stats.isMvp() ? 1 : 0);
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
// View/ReportsView.java
package view;

import java.util.List;
import java.util.Map;

public class ReportsView {
    public void displayMenu() {
        System.out.println("\n--- Tournament Reports ---");
        System.out.println("1. Team Performance Summary");
        System.out.println("2. Player Analytics");
        System.out.println("3. Map Statistics");
        System.out.println("4. Back");
        System.out.print("Choose an option: ");
    }

    public void displayTeamPerformance(Map<String, Object> summary) {
        System.out.println("\n=== Team Performance Summary ===");
        System.out.println("Team Name: " + summary.get("teamName"));
        System.out.println("Total Matches: " + summary.get("totalMatches"));
        System.out.println("Matches Won: " + summary.get("matchesWon"));
        System.out.println("Average Team Kills: " + summary.get("avgTeamKills"));
        System.out.println("Win Rate: " + 
            (((Integer)summary.get("matchesWon") * 100.0) / (Integer)summary.get("totalMatches")) + "%");
    }

    public void displayPlayerAnalytics(List<Map<String, Object>> analytics) {
        System.out.println("\n=== Player Analytics ===");
        System.out.printf("%-20s | %-15s | %-10s | %-15s | %-8s | %-8s | %-8s | %-8s | %-5s\n",
            "Player Name", "IGN", "Role", "Team", "Avg Kills", "Avg Deaths", "Avg Assist", "KDA", "MVPs");
        System.out.println("-".repeat(120));

        for (Map<String, Object> player : analytics) {
            System.out.printf("%-20s | %-15s | %-10s | %-15s | %-8.4s | %-8.4s | %-8.4s | %-8.4s | %-5s\n",
                player.get("playerName"),
                player.get("ign"),
                player.get("role"),
                player.get("teamName"),
                player.get("avgKills"),
                player.get("avgDeaths"),
                player.get("avgAssists"),
                player.get("kdaRatio"),
                player.get("mvpCount"));
        }
    }

    public void displayMapStatistics(List<Map<String, Object>> mapStats) {
        System.out.println("\n=== Map Statistics ===");
        System.out.printf("%-15s | %-12s | %-20s\n",
            "Map Name", "Times Played", "Avg Kills Per Match");
        System.out.println("-".repeat(50));

        for (Map<String, Object> map : mapStats) {
            System.out.printf("%-15s | %-12s | %-20.4s\n",
                map.get("mapName"),
                map.get("timesPlayed"),
                map.get("avgKillsPerMatch"));
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
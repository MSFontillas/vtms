package view;
import model.Player;

// View/PlayerView.java
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class PlayerView {
    private Scanner scanner;

    public PlayerView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--- Manage Players ---");
        System.out.println("1. Add Player");
        System.out.println("2. View All Players");
        System.out.println("3. Update Player");
        System.out.println("4. Delete Player");
        System.out.println("5. View Player Statistics");
        System.out.println("6. Back");
        System.out.print("Choose an option: ");
    }

    public Player getPlayerInput() {
        Player player = new Player();

        System.out.println("Enter player name: ");
        player.setPlayerName(scanner.nextLine());

        System.out.println("Enter IGN: ");
        player.setIgn(scanner.nextLine());

        System.out.println("Enter role: ");
        player.setRole(scanner.nextLine());

        System.out.println("Enter teamID: ");
        player.setTeamID(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        return player;
    }

    public int getPlayerId() {
        System.out.println("Enter playerID: ");
        return scanner.nextInt();
    }

    public void displayPlayers(List<Player> players) {
        System.out.println("\n--- Players List ---");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s | %-7s |\n",
                "ID", "Player Name", "IGN", "Role", "Team ID");
        System.out.println("-".repeat(70));

        for (Player player : players) {
            System.out.printf("| %-5d | %-20s | %-15s | %-10s | %-7d |\n",
                    player.getPlayerID(),
                    player.getPlayerName(),
                    player.getIgn(),
                    player.getRole(),
                    player.getTeamID());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayPlayerStatistics(Map<String, Object> statistics) {
        System.out.println("\n=== Player Statistics ===");
        System.out.println("Player Name: " + statistics.get("playerName"));
        System.out.println("IGN: " + statistics.get("ign"));
        System.out.println("Role: " + statistics.get("role"));
        System.out.println("Team: " + statistics.get("teamName"));
        System.out.println("KDA Ratio: " + statistics.get("kdaRatio"));
        
        System.out.println("\n--- Match History ---");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> matchHistory = (List<Map<String, Object>>) statistics.get("matchHistory");
        if (matchHistory != null && !matchHistory.isEmpty()) {
            System.out.printf("%-8s | %-12s | %-6s | %-7s | %-8s | %-4s\n",
                "Match ID", "Date", "Kills", "Deaths", "Assists", "MVP");
            System.out.println("-".repeat(55));
            
            for (Map<String, Object> match : matchHistory) {
                System.out.printf("%-8s | %-12s | %-6s | %-7s | %-8s | %-4s\n",
                    match.get("matchID"),
                    match.get("date"),
                    match.get("kills"),
                    match.get("deaths"),
                    match.get("assists"),
                    (boolean) match.get("mvp") ? "Yes" : "No");
            }
        } else {
            System.out.println("No match history found.");
        }
    }
}
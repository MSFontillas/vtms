package View;
import Model.Player;

// View/PlayerView.java
import java.util.List;
import java.util.Scanner;

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
        System.out.println("5. Back");
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
}
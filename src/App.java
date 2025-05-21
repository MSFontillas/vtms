import Controller.*;
import View.*;
import Model.*;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Initialize DAOs
        TeamDAO teamDAO = new TeamDAO();
        PlayerDAO playerDAO = new PlayerDAO();
        MapDAO mapDAO = new MapDAO();
        MatchDAO matchDAO = new MatchDAO();
        MatchStatsDAO matchStatsDAO = new MatchStatsDAO();

        // Initialize Views
        TeamView teamView = new TeamView();
        PlayerView playerView = new PlayerView();
        MapView mapView = new MapView();
        MatchView matchView = new MatchView();
        MatchStatsView matchStatsView = new MatchStatsView();

        // Initialize Controllers
        TeamController teamController = new TeamController(teamDAO, teamView);
        PlayerController playerController = new PlayerController(playerDAO, playerView);
        MapController mapController = new MapController(mapDAO, mapView);
        MatchController matchController = new MatchController(matchDAO, matchView);
        MatchStatsController matchStatsController = new MatchStatsController(matchStatsDAO, matchStatsView);

        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Valorant Tournament Management System ---");
            System.out.println("1. Manage Teams");
            System.out.println("2. Manage Players");
            System.out.println("3. Manage Maps");
            System.out.println("4. Manage Matches");
            System.out.println("5. Manage Match Stats");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    teamView.displayMenu();
                    teamController.processUserChoice(sc.nextInt());
                    break;
                case 2:
                    playerView.displayMenu();
                    playerController.processUserChoice(sc.nextInt());
                    break;
                case 3:
                    mapView.displayMenu();
                    mapController.processUserChoice(sc.nextInt());
                    break;
                case 4:
                    matchView.displayMenu();
                    matchController.processUserChoice(sc.nextInt());
                    break;
                case 5:
                    matchStatsView.displayMenu();
                    matchStatsController.processUserChoice(sc.nextInt());
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

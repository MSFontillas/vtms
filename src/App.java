import main.java.controller.*;
import main.java.view.*;
import main.java.model.*;

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

        // After other DAO initializations:
        ReportsDAO reportsDAO = new ReportsDAO();
        ReportsView reportsView = new ReportsView();
        ReportsController reportsController = new ReportsController(reportsDAO, reportsView);

        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Valorant Tournament Management System ---");
            System.out.println("1. Manage Teams");
            System.out.println("2. Manage Players");
            System.out.println("3. Manage Maps");
            System.out.println("4. Manage Matches");
            System.out.println("5. Manage Match Stats");
            System.out.println("6. View Reports");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    teamController.processUserChoice(sc);
                    break;
                case 2:
                    playerController.processUserChoice(sc);
                    break;
                case 3:
                    mapController.processUserChoice(sc);
                    break;
                case 4:
                    matchController.processUserChoice(sc);
                    break;
                case 5:
                    matchStatsController.processUserChoice(sc);
                    break;
                case 6:
                    reportsController.processUserChoice(sc);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
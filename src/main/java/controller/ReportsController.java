package main.java.controller;

import main.java.model.ReportsDAO;
import main.java.view.ReportsView;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportsController {
    private final ReportsDAO reportsDAO;
    private final ReportsView reportsView;
    private final Scanner scanner;

    public ReportsController(ReportsDAO reportsDAO, ReportsView reportsView) {
        this.reportsDAO = reportsDAO;
        this.reportsView = reportsView;
        this.scanner = new Scanner(System.in);
    }

    public void processUserChoice(Scanner sc) {
        try {
            while (true) {
                reportsView.displayMenu();
                switch (sc.nextInt()) {
                    case 1:
                        System.out.print("Enter team ID: ");
                        int teamId = scanner.nextInt();
                        reportsView.displayTeamPerformance(reportsDAO.getTeamPerformanceSummary(teamId));
                        break;
                    case 2:
                        reportsView.displayPlayerAnalytics(reportsDAO.getPlayerAnalytics());
                        break;
                    case 3:
                        reportsView.displayMapStatistics(reportsDAO.getMapStatistics());
                        break;
                    case 4:
                        return;
                    default:
                        reportsView.displayMessage("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            reportsView.displayMessage("Error: " + e.getMessage());
        }
    }
}
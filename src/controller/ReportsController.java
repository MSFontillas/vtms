package controller;

import model.ReportsDAO;
import view.ReportsView;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportsController {
    private ReportsDAO reportsDAO;
    private ReportsView reportsView;
    private Scanner scanner;

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
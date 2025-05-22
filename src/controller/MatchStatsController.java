// Controller/MatchStatsController.java
package controller;

import model.MatchStatsDAO;
import model.MatchStats;
import view.MatchStatsView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MatchStatsController {
    private MatchStatsDAO matchStatsDAO;
    private MatchStatsView matchStatsView;

    public MatchStatsController(MatchStatsDAO matchStatsDAO, MatchStatsView matchStatsView) {
        this.matchStatsDAO = matchStatsDAO;
        this.matchStatsView = matchStatsView;
    }

    public void processUserChoice(Scanner sc) {
        try {
            while (true) {
                matchStatsView.displayMenu();
                switch (sc.nextInt()) {
                    case 1:
                        addMatchStats();
                        break;
                    case 2:
                        showAllMatchStats();
                        break;
                    case 3:
                        updateMatchStats();
                        break;
                    case 4:
                        deleteMatchStats();
                        break;
                    case 5:
                        return;
                    default:
                        matchStatsView.displayMessage("Invalid option.");
                }
            }
        } catch (SQLException e) {
            matchStatsView.displayMessage("Operation failed: " + e.getMessage());
        }
    }

    private void addMatchStats() throws SQLException {
        MatchStats stats = matchStatsView.getMatchStatsInput();
        matchStatsDAO.addMatchStats(stats);
        matchStatsView.displayMessage("Match stats added successfully!");
    }

    private void showAllMatchStats() throws SQLException {
        List<MatchStats> statsList = matchStatsDAO.getAllMatchStats();
        matchStatsView.displayMatchStats(statsList);
    }

    private void updateMatchStats() throws SQLException {
        int statId = matchStatsView.getStatId();
        MatchStats existingStats = matchStatsDAO.getMatchStatsById(statId);

        if (existingStats == null) {
            matchStatsView.displayMessage("Match stats not found!");
            return;
        }

        MatchStats updatedStats = matchStatsView.getMatchStatsInput();
        updatedStats.setStatID(statId);
        matchStatsDAO.updateMatchStats(updatedStats);
        matchStatsView.displayMessage("Match stats updated successfully!");
    }

    private void deleteMatchStats() throws SQLException {
        int statId = matchStatsView.getStatId();
        MatchStats existingStats = matchStatsDAO.getMatchStatsById(statId);

        if (existingStats == null) {
            matchStatsView.displayMessage("Match stats not found!");
            return;
        }

        matchStatsDAO.deleteMatchStats(statId);
        matchStatsView.displayMessage("Match stats deleted successfully!");
    }
}
// Controller/MatchController.java
package Controller;

import Model.MatchDAO;
import Model.Match;
import View.MatchView;

import java.sql.SQLException;
import java.util.List;

public class MatchController {
    private MatchDAO matchDAO;
    private MatchView matchView;

    public MatchController(MatchDAO matchDAO, MatchView matchView) {
        this.matchDAO = matchDAO;
        this.matchView = matchView;
    }

    public void processUserChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    addMatch();
                    break;
                case 2:
                    showAllMatches();
                    break;
                case 3:
                    updateMatch();
                    break;
                case 4:
                    deleteMatch();
                    break;
                case 5:
                    return;
                default:
                    matchView.displayMessage("Invalid option.");
            }
        } catch (SQLException e) {
            matchView.displayMessage("Operation failed: " + e.getMessage());
        }
    }

    private void addMatch() throws SQLException {
        Match match = matchView.getMatchInput();
        matchDAO.addMatch(match);
        matchView.displayMessage("Match added successfully!");
    }

    private void showAllMatches() throws SQLException {
        List<Match> matches = matchDAO.getAllMatches();
        matchView.displayMatches(matches);
    }

    private void updateMatch() throws SQLException {
        int matchId = matchView.getMatchId();
        Match existingMatch = matchDAO.getMatchById(matchId);

        if (existingMatch == null) {
            matchView.displayMessage("Match not found!");
            return;
        }

        Match updatedMatch = matchView.getMatchInput();
        updatedMatch.setMatchID(matchId);
        matchDAO.updateMatch(updatedMatch);
        matchView.displayMessage("Match updated successfully!");
    }

    private void deleteMatch() throws SQLException {
        int matchId = matchView.getMatchId();
        Match existingMatch = matchDAO.getMatchById(matchId);

        if (existingMatch == null) {
            matchView.displayMessage("Match not found!");
            return;
        }

        matchDAO.deleteMatch(matchId);
        matchView.displayMessage("Match deleted successfully!");
    }
}
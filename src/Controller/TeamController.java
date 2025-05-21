package Controller;

// Controller/TeamController.java
import Model.TeamDAO;
import Model.Team;
import View.TeamView;

import java.sql.SQLException;
import java.util.List;

public class TeamController {
    private TeamDAO teamDAO;
    private TeamView teamView;

    public TeamController(TeamDAO teamDAO, TeamView teamView) {
        this.teamDAO = teamDAO;
        this.teamView = teamView;
    }

    public void processUserChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    addTeam();
                    break;
                case 2:
                    showAllTeams();
                    break;
                case 3:
                    updateTeam();
                    break;
                case 4:
                    deleteTeam();
                    break;
                case 5:
                    return;
                default:
                    teamView.displayMessage("Invalid option.");
            }
        } catch (SQLException e) {
            teamView.displayMessage("Operation failed: " + e.getMessage());
        }
    }

    private void addTeam() throws SQLException {
        Team team = teamView.getTeamInput();
        teamDAO.addTeam(team);
        teamView.displayMessage("Team added successfully!");
    }

    private void showAllTeams() throws SQLException {
        List<Team> teams = teamDAO.getAllTeams();
        teamView.displayTeams(teams);
    }

    private void updateTeam() throws SQLException {
        int teamId = teamView.getTeamId();
        Team existingTeam = teamDAO.getTeamById(teamId);
        
        if (existingTeam == null) {
            teamView.displayMessage("Team not found!");
            return;
        }

        Team updatedTeam = teamView.getTeamInput();
        updatedTeam.setTeamID(teamId);
        teamDAO.updateTeam(updatedTeam);
        teamView.displayMessage("Team updated successfully!");
    }

    private void deleteTeam() throws SQLException {
        int teamId = teamView.getTeamId();
        Team existingTeam = teamDAO.getTeamById(teamId);
        
        if (existingTeam == null) {
            teamView.displayMessage("Team not found!");
            return;
        }

        teamDAO.deleteTeam(teamId);
        teamView.displayMessage("Team deleted successfully!");
    }
}
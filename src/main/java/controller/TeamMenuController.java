package main.java.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import main.java.model.Team;
import main.java.model.TeamDAO;
import main.java.util.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamMenuController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField regionField;
    @FXML
    private TextField coachField;
    @FXML
    private SplitMenuButton smb;

    private TeamViewController teamViewController;
    private TeamDAO teamDAO;
    private AlertUtil alertUtil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teamDAO = new TeamDAO();
        alertUtil = new AlertUtil();

        try {
            List<Team> teams = teamDAO.getAllTeams();

            // Get suggestions for each field
            List<String> teamNames = teams.stream()
                    .map(Team::getTeamName)
                    .collect(Collectors.toList());

            List<String> regions = teams.stream()
                    .map(Team::getRegion)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> coaches = teams.stream()
                    .map(Team::getCoach)
                    .distinct()
                    .collect(Collectors.toList());

            // Setup autocomplete
            AutoCompleteUtil.setupAutoComplete(nameField, teamNames);
            AutoCompleteUtil.setupAutoComplete(regionField, regions);
            AutoCompleteUtil.setupAutoComplete(coachField, coaches);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Disable the search button until TeamViewController is set
        smb.setDisable(true);

        // Configure the main button action (Search)
        smb.setOnAction(e -> handleSearch());

        // Configure menu items
        for (MenuItem item : smb.getItems()) {
            switch (item.getText()) {
                case "Add" -> item.setOnAction(e -> handleAdd());
                case "Update" -> item.setOnAction(e -> handleUpdate());
                case "Delete" -> item.setOnAction(e -> handleDelete());
            }
        }
    }

    public void setTeamViewController(TeamViewController controller) {
        this.teamViewController = controller;
        smb.setDisable(false);

        // Set up a table selection listener
        teamViewController.getTeamTable().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Fill text fields with selected team data
                    nameField.setText(newSelection.getTeamName());
                    regionField.setText(newSelection.getRegion());
                    coachField.setText(newSelection.getCoach());
                }
            }
        );
    }

    private void handleSearch() {
        if (teamViewController == null) {
            alertUtil.showError("Error", "Team view not properly initialized");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String region = regionField.getText().trim();
            String coach = coachField.getText().trim();

            List<Team> searchResults = teamDAO.searchTeams(name, region, coach);
            teamViewController.getTeamTable().setItems(FXCollections.observableArrayList(searchResults));

            if (searchResults.isEmpty()) {
                alertUtil.showInformation("Search Results", "No teams found matching the search criteria.");
            }
        } catch (SQLException ex) {
            alertUtil.showError("Search Error", "Failed to search teams: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleAdd() {
        try {
            validateFields();

            Team newTeam = new Team(
                0,
                nameField.getText().trim(),
                coachField.getText().trim(),
                regionField.getText().trim()
            );

            teamDAO.addTeam(newTeam);
            refreshTable();
            clearFields();
            alertUtil.showInformation("Success", "Team added successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Adding Team", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleUpdate() {
        Team selectedTeam = teamViewController.getTeamTable().getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            alertUtil.showWarning("Update Error", "Please select a team to update.");
            return;
        }

        try {
            validateFields();

            selectedTeam.setTeamName(nameField.getText().trim());
            selectedTeam.setRegion(regionField.getText().trim());
            selectedTeam.setCoach(coachField.getText().trim());

            teamDAO.updateTeam(selectedTeam);
            refreshTable();
            alertUtil.showInformation("Success", "Team updated successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Updating Team", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleDelete() {
        Team selectedTeam = teamViewController.getTeamTable().getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            alertUtil.showWarning("Delete Error", "Please select a team to delete.");
            return;
        }

        if (alertUtil.showConfirmation("Delete Team",
                "Are you sure you want to delete team: " + selectedTeam.getTeamName() + "?")) {
            try {
                teamDAO.deleteTeam(selectedTeam.getTeamID());
                refreshTable();
                clearFields();
                alertUtil.showInformation("Success", "Team deleted successfully!");
            } catch (SQLException ex) {
                alertUtil.showError("Error Deleting Team", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        regionField.clear();
        coachField.clear();
    }

    private void refreshTable() {
        if (teamViewController != null) {
            teamViewController.loadTeamData();
        }
    }

    private void validateFields() throws ValidationException {
        String teamName = nameField.getText().trim();
        String region = regionField.getText().trim();
        String coach = coachField.getText().trim();

        // Required fields validation
        if (teamName.isEmpty()) {
            throw new ValidationException("Team name is required");
        }
        if (region.isEmpty()) {
            throw new ValidationException("Region is required");
        }
        if (coach.isEmpty()) {
            throw new ValidationException("Coach is required");
        }

        // Length validations
        if (teamName.length() < 2 || teamName.length() > 50) {
            throw new ValidationException("Team name must be between 2 and 50 characters");
        }
        if (region.length() < 2 || region.length() > 20) {
            throw new ValidationException("Region must be between 2 and 20 characters");
        }
        if (coach.length() < 2 || coach.length() > 50) {
            throw new ValidationException("Coach name must be between 2 and 50 characters");
        }
    }
}
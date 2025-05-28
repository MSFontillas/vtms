package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import main.java.model.MatchStats;
import main.java.model.MatchStatsDAO;
import main.java.util.AlertUtil;
import main.java.util.AutoCompleteUtil;
import main.java.util.ValidationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MatchStatsMenuController implements Initializable {
    @FXML
    public TextField matchIDField;
    @FXML
    public TextField playerField;
    @FXML
    public SplitMenuButton smb;
    @FXML
    public CheckBox mvpBox;
    @FXML
    public TextField killsField;
    @FXML
    public TextField deathsField;
    @FXML
    public TextField assistsField;

    private MatchStatsViewController matchStatsViewController;
    private MatchStatsDAO matchStatsDAO;
    private AlertUtil alertUtil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matchStatsDAO = new MatchStatsDAO();
        alertUtil = new AlertUtil();

        try {
            // Get player names and match IDs for autocomplete
            List<String> playerNames = matchStatsDAO.getAllMatchStats()
                    .stream()
                    .map(MatchStats::getPlayerName)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> matchIds = matchStatsDAO.getAllMatchStats()
                    .stream()
                    .map(stats -> String.valueOf(stats.getMatchID()))
                    .distinct()
                    .collect(Collectors.toList());

            // Setup autocomplete
            AutoCompleteUtil.setupAutoComplete(playerField, playerNames);
            AutoCompleteUtil.setupAutoComplete(matchIDField, matchIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Disable the search button until MatchStatsViewController is set
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

    public void setMatchStatsViewController(MatchStatsViewController controller) {
        this.matchStatsViewController = controller;
        smb.setDisable(false);

        // Set up a table selection listener
        matchStatsViewController.getMatchStatsTable().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Fill text fields with selected match stats data
                    matchIDField.setText(String.valueOf(newSelection.getMatchID()));
                    playerField.setText(newSelection.getPlayerName());
                    killsField.setText(String.valueOf(newSelection.getKills()));
                    deathsField.setText(String.valueOf(newSelection.getDeaths()));
                    assistsField.setText(String.valueOf(newSelection.getAssists()));
                    mvpBox.setSelected(newSelection.isMvp());
                }
            }
        );
    }


    private void handleSearch() {
        if (matchStatsViewController == null) {
            alertUtil.showError("Error", "Match stats view not properly initialized");
            return;
        }

        try {
            String matchIdStr = matchIDField.getText().trim();
            String playerName = playerField.getText().trim();
            String kills = killsField.getText().trim();
            String deaths = deathsField.getText().trim();
            String assists = assistsField.getText().trim();
            Boolean mvp = mvpBox.isSelected() ? true : null;

            // Validate numeric fields if provided
            if (!matchIdStr.isEmpty()) {
                try {
                    Integer.parseInt(matchIdStr);
                } catch (NumberFormatException ex) {
                    alertUtil.showError("Invalid Input", "Match ID must be a valid number");
                    return;
                }
            }

            if (!kills.isEmpty()) {
                try {
                    Integer.parseInt(kills);
                } catch (NumberFormatException ex) {
                    alertUtil.showError("Invalid Input", "Kills must be a valid number");
                    return;
                }
            }

            if (!deaths.isEmpty()) {
                try {
                    Integer.parseInt(deaths);
                } catch (NumberFormatException ex) {
                    alertUtil.showError("Invalid Input", "Deaths must be a valid number");
                    return;
                }
            }

            if (!assists.isEmpty()) {
                try {
                    Integer.parseInt(assists);
                } catch (NumberFormatException ex) {
                    alertUtil.showError("Invalid Input", "Assists must be a valid number");
                    return;
                }
            }

            // If all fields are empty and MVP is not checked, load all match stats
            if (matchIdStr.isEmpty() && playerName.isEmpty() && kills.isEmpty() &&
                    deaths.isEmpty() && assists.isEmpty() && mvp == null) {
                matchStatsViewController.getMatchStatsTable().setItems(
                        FXCollections.observableArrayList(matchStatsDAO.getAllMatchStats())
                );
                return;
            }

            List<MatchStats> searchResults = matchStatsDAO.searchMatchStats(
                    matchIdStr, playerName, kills, deaths, assists, mvp
            );

            matchStatsViewController.getMatchStatsTable().setItems(
                    FXCollections.observableArrayList(searchResults)
            );

            if (searchResults.isEmpty()) {
                alertUtil.showInformation("Search Results",
                        "No match stats found matching the search criteria.");
            }
        } catch (SQLException ex) {
            alertUtil.showError("Search Error", "Failed to search match stats: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleAdd() {
        try {
            validateFields();

            MatchStats newStats = new MatchStats(
                0, // statID will be generated by database
                Integer.parseInt(matchIDField.getText().trim()),
                0, // playerID will be set by DAO based on player name
                Integer.parseInt(killsField.getText().trim()),
                Integer.parseInt(deathsField.getText().trim()),
                Integer.parseInt(assistsField.getText().trim()),
                mvpBox.isSelected()
            );

            matchStatsDAO.addMatchStats(newStats);
            refreshTable();
            clearFields();
            alertUtil.showInformation("Success", "Match stats added successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Adding Match Stats", ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            alertUtil.showError("Invalid Input", "Please enter valid numbers for Match ID, Kills, Deaths, and Assists");
        }
    }

    private void handleUpdate() {
        MatchStats selectedStats = matchStatsViewController.getMatchStatsTable().getSelectionModel().getSelectedItem();
        if (selectedStats == null) {
            alertUtil.showWarning("Update Error", "Please select match stats to update.");
            return;
        }

        try {
            validateFields();

            selectedStats.setMatchID(Integer.parseInt(matchIDField.getText().trim()));
            selectedStats.setKills(Integer.parseInt(killsField.getText().trim()));
            selectedStats.setDeaths(Integer.parseInt(deathsField.getText().trim()));
            selectedStats.setAssists(Integer.parseInt(assistsField.getText().trim()));
            selectedStats.setMvp(mvpBox.isSelected());

            matchStatsDAO.updateMatchStats(selectedStats);
            refreshTable();
            alertUtil.showInformation("Success", "Match stats updated successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Updating Match Stats", ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            alertUtil.showError("Invalid Input", "Please enter valid numbers for Match ID, Kills, Deaths, and Assists");
        }
    }

    private void handleDelete() {
        MatchStats selectedStats = matchStatsViewController.getMatchStatsTable().getSelectionModel().getSelectedItem();
        if (selectedStats == null) {
            alertUtil.showWarning("Delete Error", "Please select match stats to delete.");
            return;
        }

        if (alertUtil.showConfirmation("Delete Match Stats",
                "Are you sure you want to delete these match stats?")) {
            try {
                matchStatsDAO.deleteMatchStats(selectedStats.getStatID());
                refreshTable();
                clearFields();
                alertUtil.showInformation("Success", "Match stats deleted successfully!");
            } catch (SQLException ex) {
                alertUtil.showError("Error Deleting Match Stats", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        matchIDField.clear();
        playerField.clear();
        killsField.clear();
        deathsField.clear();
        assistsField.clear();
        mvpBox.setSelected(false);
    }

    private void refreshTable() {
        if (matchStatsViewController != null) {
            matchStatsViewController.loadMatchStatsData();
        }
    }

private void validateFields() throws ValidationException {
    String matchIdStr = matchIDField.getText().trim();
    String playerName = playerField.getText().trim();
    String killsStr = killsField.getText().trim();
    String deathsStr = deathsField.getText().trim();
    String assistsStr = assistsField.getText().trim();

    // Required fields validation
    if (matchIdStr.isEmpty()) {
        throw new ValidationException("Match ID is required");
    }
    if (playerName.isEmpty()) {
        throw new ValidationException("Player name is required");
    }
    if (killsStr.isEmpty()) {
        throw new ValidationException("Kills is required");
    }
    if (deathsStr.isEmpty()) {
        throw new ValidationException("Deaths is required");
    }
    if (assistsStr.isEmpty()) {
        throw new ValidationException("Assists is required");
    }

    // Numeric validation with reasonable ranges
    try {
        int matchId = Integer.parseInt(matchIdStr);
        int kills = Integer.parseInt(killsStr);
        int deaths = Integer.parseInt(deathsStr);
        int assists = Integer.parseInt(assistsStr);

        if (matchId <= 0) {
            throw new ValidationException("Match ID must be a positive number");
        }
        if (kills < 0) {
            throw new ValidationException("Kills cannot be negative");
        }
        if (deaths < 0) {
            throw new ValidationException("Deaths cannot be negative");
        }
        if (assists < 0) {
            throw new ValidationException("Assists cannot be negative");
        }
        
        // Additional game-specific validation
        if (kills > 100) {
            throw new ValidationException("Kills value seems unreasonably high");
        }
        if (deaths > 100) {
            throw new ValidationException("Deaths value seems unreasonably high");
        }
        if (assists > 100) {
            throw new ValidationException("Assists value seems unreasonably high");
        }
    } catch (NumberFormatException e) {
        throw new ValidationException("Match ID, Kills, Deaths, and Assists must be valid numbers");
    }
}
}
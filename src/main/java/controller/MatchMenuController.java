package main.java.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.model.*;
import main.java.util.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MatchMenuController implements Initializable {
    @FXML
    public TextField teamBField;
    @FXML
    public TextField teamAField;
    @FXML
    public SplitMenuButton smb;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Spinner<Integer> hourPicker;
    @FXML
    public Spinner<Integer> minutePicker;
    @FXML
    public TextField winnerField;
    @FXML
    public TextField mapField;
    
    private MatchViewController matchViewController;
    private MatchDAO matchDAO;
    private TeamDAO teamDAO;
    private MapDAO mapDAO;
    private AlertUtil alertUtil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matchDAO = new MatchDAO();
        teamDAO = new TeamDAO();
        mapDAO = new MapDAO();
        alertUtil = new AlertUtil();

        try {
            // Get all team names for autocomplete
            List<String> teamNames = teamDAO.getAllTeams()
                    .stream()
                    .map(Team::getTeamName)
                    .collect(Collectors.toList());

            // Get all map names
            List<String> mapNames = mapDAO.getAllMaps()
                    .stream()
                    .map(Map::getMapName)
                    .collect(Collectors.toList());

            // Setup autocomplete
            AutoCompleteUtil.setupAutoComplete(teamAField, teamNames);
            AutoCompleteUtil.setupAutoComplete(teamBField, teamNames);
            AutoCompleteUtil.setupAutoComplete(winnerField, teamNames);
            AutoCompleteUtil.setupAutoComplete(mapField, mapNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Configure time spinners
        SpinnerValueFactory<Integer> hourFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> minuteFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        hourPicker.setValueFactory(hourFactory);
        minutePicker.setValueFactory(minuteFactory);
        
        // Disable the search button until MatchViewController is set
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
    
    public void setMatchViewController(MatchViewController controller) {
        this.matchViewController = controller;
        smb.setDisable(false);
        
        // Set up a table selection listener
        matchViewController.getMatchTable().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Fill fields with selected match data
                    teamAField.setText(newSelection.getTeamAName());
                    teamBField.setText(newSelection.getTeamBName());
                    datePicker.setValue(newSelection.getMatchDate());
                    hourPicker.getValueFactory().setValue(newSelection.getMatchTime().getHour());
                    minutePicker.getValueFactory().setValue(newSelection.getMatchTime().getMinute());
                    winnerField.setText(newSelection.getWinnerName());
                    mapField.setText(newSelection.getMapName());
                }
            }
        );
    }
    
    private void handleSearch() {
        if (matchViewController == null) {
            alertUtil.showError("Error", "Match view not properly initialized");
            return;
        }
        
        try {
            String teamA = teamAField.getText().trim();
            String teamB = teamBField.getText().trim();
            LocalDate date = datePicker.getValue();
            String winner = winnerField.getText().trim();
            String map = mapField.getText().trim();
            
            List<Match> searchResults = matchDAO.searchMatches(teamA, teamB, date, winner, map);
            matchViewController.getMatchTable().setItems(FXCollections.observableArrayList(searchResults));
            
            if (searchResults.isEmpty()) {
                alertUtil.showInformation("Search Results", "No matches found matching the search criteria.");
            }
        } catch (SQLException ex) {
            alertUtil.showError("Search Error", "Failed to search matches: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleAdd() {
        try {
            validateFields();
            
            LocalTime time = LocalTime.of(
                hourPicker.getValue(),
                minutePicker.getValue()
            );
            
            Match newMatch = new Match(
                0,
                getTeamIdFromName(teamAField.getText().trim()),
                getTeamIdFromName(teamBField.getText().trim()),
                datePicker.getValue(),
                time,
                getTeamIdFromName(winnerField.getText().trim()),
                getMapIdFromName(mapField.getText().trim())
            );
            
            matchDAO.addMatch(newMatch);
            refreshTable();
            clearFields();
            alertUtil.showInformation("Success", "Match added successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Adding Match", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleUpdate() {
        Match selectedMatch = matchViewController.getMatchTable().getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            alertUtil.showWarning("Update Error", "Please select a match to update.");
            return;
        }
        
        try {
            validateFields();
            
            LocalTime time = LocalTime.of(
                hourPicker.getValue(),
                minutePicker.getValue()
            );
            
            selectedMatch.setTeamAID(getTeamIdFromName(teamAField.getText().trim()));
            selectedMatch.setTeamBID(getTeamIdFromName(teamBField.getText().trim()));
            selectedMatch.setMatchDate(datePicker.getValue());
            selectedMatch.setMatchTime(time);
            selectedMatch.setWinnerID(getTeamIdFromName(winnerField.getText().trim()));
            selectedMatch.setMapID(getMapIdFromName(mapField.getText().trim()));
            
            matchDAO.updateMatch(selectedMatch);
            refreshTable();
            alertUtil.showInformation("Success", "Match updated successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Updating Match", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleDelete() {
        Match selectedMatch = matchViewController.getMatchTable().getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            alertUtil.showWarning("Delete Error", "Please select a match to delete.");
            return;
        }
        
        if (alertUtil.showConfirmation("Delete Match", 
                "Are you sure you want to delete this match?")) {
            try {
                matchDAO.deleteMatch(selectedMatch.getMatchID());
                refreshTable();
                clearFields();
                alertUtil.showInformation("Success", "Match deleted successfully!");
            } catch (SQLException ex) {
                alertUtil.showError("Error Deleting Match", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void clearFields() {
        teamAField.clear();
        teamBField.clear();
        datePicker.setValue(null);
        hourPicker.getValueFactory().setValue(12);
        minutePicker.getValueFactory().setValue(0);
        winnerField.clear();
        mapField.clear();
    }
    
    private void refreshTable() {
        if (matchViewController != null) {
            matchViewController.loadMatchData();
        }
    }
    
    private int getTeamIdFromName(String teamName) throws SQLException {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new SQLException("Team name cannot be empty");
        }
        return teamDAO.getTeamIdByName(teamName.trim());
    }
    
    private int getMapIdFromName(String mapName) throws SQLException {
        if (mapName == null || mapName.trim().isEmpty()) {
            throw new SQLException("Map name cannot be empty");
        }
        return mapDAO.getMapIdByName(mapName.trim());
    }
    
    private void validateFields() throws ValidationException {
        String teamA = teamAField.getText().trim();
        String teamB = teamBField.getText().trim();
        String winner = winnerField.getText().trim();
        String map = mapField.getText().trim();

        // Basic required field validation
        if (teamA.isEmpty()) {
            throw new ValidationException("Team A is required");
        }
        if (teamB.isEmpty()) {
            throw new ValidationException("Team B is required");
        }

        // Team comparison validation
        if (teamA.equalsIgnoreCase(teamB)) {
            throw new ValidationException("Team A and Team B cannot be the same team");
        }

        // Winner validation
        if (!winner.equalsIgnoreCase(teamA) && !winner.equalsIgnoreCase(teamB)) {
            if(!winner.isEmpty()) {
                throw new ValidationException("Winner must be either Team A or Team B");
            }
        }

        // Time validation
        int hour = hourPicker.getValue();
        int minute = minutePicker.getValue();
        if (hour < 0 || hour > 23) {
            throw new ValidationException("Invalid hour value");
        }
        if (minute < 0 || minute > 59) {
            throw new ValidationException("Invalid minute value");
        }
    }
}
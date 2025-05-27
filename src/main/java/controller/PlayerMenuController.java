package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;

import main.java.model.Player;
import main.java.model.PlayerDAO;
import main.java.model.TeamDAO;
import main.java.util.AlertUtil;
import main.java.util.ValidationException;

public class PlayerMenuController implements Initializable {
    @FXML
    private SplitMenuButton smb;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField ignField;
    
    @FXML
    private TextField roleField;
    
    @FXML
    private TextField teamField;
    
    private PlayerViewController playerViewController;
    private PlayerDAO playerDAO;
    private TeamDAO teamDAO;
    private AlertUtil alertUtil;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerDAO = new PlayerDAO();
        teamDAO = new TeamDAO();
        alertUtil = new AlertUtil();
        
        // Disable the search button until PlayerViewController is set
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
    
    public void setPlayerViewController(PlayerViewController controller) {
        this.playerViewController = controller;
        
        // Enable the search button now that we have the controller
        smb.setDisable(false);
        
        // Set up a table selection listener
        playerViewController.getPlayerTable().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Fill text fields with selected player data
                    nameField.setText(newSelection.getPlayerName());
                    ignField.setText(newSelection.getIgn());
                    roleField.setText(newSelection.getRole());
                    teamField.setText(newSelection.getTeamName());
                }
            }
        );
    }
    
    private void handleSearch() {
        if (playerViewController == null) {
            alertUtil.showError("Error", "Player view not properly initialized");
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            String ign = ignField.getText().trim();
            String role = roleField.getText().trim();
            String team = teamField.getText().trim();
            
            List<Player> searchResults = playerDAO.searchPlayers(name, ign, role, team);
            playerViewController.getPlayerTable().setItems(FXCollections.observableArrayList(searchResults));
            
            if (searchResults.isEmpty()) {
                alertUtil.showInformation("Search Results", "No players found matching the search criteria.");
            }
        } catch (SQLException ex) {
            alertUtil.showError("Search Error", "Failed to search players: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private int getTeamIdFromName(String teamName) throws SQLException {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new SQLException("Team name cannot be empty");
        }
        return teamDAO.getTeamIdByName(teamName.trim());
    }

    private void handleAdd() {
        try {
            validateFields();
        
            Player newPlayer = new Player(
                0,
                nameField.getText().trim(),
                ignField.getText().trim(),
                roleField.getText().trim(),
                getTeamIdFromName(teamField.getText().trim())
            );
        
            playerDAO.addPlayer(newPlayer);
            refreshTable();
            clearFields();
            alertUtil.showInformation("Success", "Player added successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Adding Player", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleUpdate() {
        Player selectedPlayer = playerViewController.getPlayerTable().getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            alertUtil.showWarning("Update Error", "Please select a player to update.");
            return;
        }
        
        try {
            validateFields();
        
            selectedPlayer.setPlayerName(nameField.getText().trim());
            selectedPlayer.setIgn(ignField.getText().trim());
            selectedPlayer.setRole(roleField.getText().trim());
            selectedPlayer.setTeamID(getTeamIdFromName(teamField.getText().trim()));
        
            playerDAO.updatePlayer(selectedPlayer);
            refreshTable();
            alertUtil.showInformation("Success", "Player updated successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Updating Player", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleDelete() {
        Player selectedPlayer = playerViewController.getPlayerTable().getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            alertUtil.showWarning("Delete Error", "Please select a player to delete.");
            return;
        }
        
        if (alertUtil.showConfirmation("Delete Player", 
                "Are you sure you want to delete player: " + selectedPlayer.getPlayerName() + "?")) {
            try {
                playerDAO.deletePlayer(selectedPlayer.getPlayerID());
                refreshTable();
                clearFields();
                alertUtil.showInformation("Success", "Player deleted successfully!");
            } catch (SQLException ex) {
                alertUtil.showError("Error Deleting Player", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void clearFields() {
        nameField.clear();
        ignField.clear();
        roleField.clear();
        teamField.clear();
    }
    
    private void refreshTable() {
        if (playerViewController != null) {
            playerViewController.loadPlayerData();
        }
    }
    
    private void validateFields() throws ValidationException {
        if (nameField.getText().trim().isEmpty()) {
            throw new ValidationException("Player name is required");
        }
        if (ignField.getText().trim().isEmpty()) {
            throw new ValidationException("IGN is required");
        }
        if (roleField.getText().trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }
        if (teamField.getText().trim().isEmpty()) {
            throw new ValidationException("Team is required");
        }
    }
}
package main.java.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import main.java.model.Map;
import main.java.model.MapDAO;
import main.java.util.AlertUtil;
import main.java.util.AutoCompleteUtil;
import main.java.util.ValidationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MapMenuController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private SplitMenuButton smb;
    
    private MapViewController mapViewController;
    private MapDAO mapDAO;
    private AlertUtil alertUtil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapDAO = new MapDAO();
        alertUtil = new AlertUtil();

        try {
            // Get all map names for autocomplete
            List<String> mapNames = mapDAO.getAllMaps()
                    .stream()
                    .map(Map::getMapName)
                    .collect(Collectors.toList());

            // Setup autocomplete
            AutoCompleteUtil.setupAutoComplete(nameField, mapNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Disable the search button until MapViewController is set
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
    
    public void setMapViewController(MapViewController controller) {
        this.mapViewController = controller;
        smb.setDisable(false);
        
        // Set up a table selection listener
        mapViewController.getMapTable().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Fill text field with selected map data
                    nameField.setText(newSelection.getMapName());
                }
            }
        );
    }
    
    private void handleSearch() {
        if (mapViewController == null) {
            alertUtil.showError("Error", "Map view not properly initialized");
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            
            List<Map> searchResults = mapDAO.searchMaps(name);
            mapViewController.getMapTable().setItems(FXCollections.observableArrayList(searchResults));
            
            if (searchResults.isEmpty()) {
                alertUtil.showInformation("Search Results", "No maps found matching the search criteria.");
            }
        } catch (SQLException ex) {
            alertUtil.showError("Search Error", "Failed to search maps: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleAdd() {
        try {
            validateFields();
            
            Map newMap = new Map(
                0,
                nameField.getText().trim()
            );
            
            mapDAO.addMap(newMap);
            refreshTable();
            clearFields();
            alertUtil.showInformation("Success", "Map added successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Adding Map", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleUpdate() {
        Map selectedMap = mapViewController.getMapTable().getSelectionModel().getSelectedItem();
        if (selectedMap == null) {
            alertUtil.showWarning("Update Error", "Please select a map to update.");
            return;
        }
        
        try {
            validateFields();
            
            selectedMap.setMapName(nameField.getText().trim());
            
            mapDAO.updateMap(selectedMap);
            refreshTable();
            alertUtil.showInformation("Success", "Map updated successfully!");
        } catch (SQLException | ValidationException ex) {
            alertUtil.showError("Error Updating Map", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleDelete() {
        Map selectedMap = mapViewController.getMapTable().getSelectionModel().getSelectedItem();
        if (selectedMap == null) {
            alertUtil.showWarning("Delete Error", "Please select a map to delete.");
            return;
        }
        
        if (alertUtil.showConfirmation("Delete Map", 
                "Are you sure you want to delete map: " + selectedMap.getMapName() + "?")) {
            try {
                mapDAO.deleteMap(selectedMap.getMapID());
                refreshTable();
                clearFields();
                alertUtil.showInformation("Success", "Map deleted successfully!");
            } catch (SQLException ex) {
                alertUtil.showError("Error Deleting Map", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void clearFields() {
        nameField.clear();
    }
    
    private void refreshTable() {
        if (mapViewController != null) {
            mapViewController.loadMapData();
        }
    }
    
    private void validateFields() throws ValidationException {
        String mapName = nameField.getText().trim();
        
        if (mapName.isEmpty()) {
            throw new ValidationException("Map name is required");
        }
        
        // Additional validation for map name length
        if (mapName.length() < 2 || mapName.length() > 50) {
            throw new ValidationException("Map name must be between 2 and 50 characters");
        }
        
        // Check for special characters
        if (!mapName.matches("^[a-zA-Z0-9\\s-_]+$")) {
            throw new ValidationException("Map name can only contain letters, numbers, spaces, hyphens and underscores");
        }
    }
}
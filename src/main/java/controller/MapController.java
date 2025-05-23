package main.java.controller;// Controller/MapController.java

import main.java.model.Map;
import main.java.model.MapDAO;
import main.java.view.MapView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MapController {
    private final MapDAO mapDAO;
    private final MapView mapView;

    public MapController(MapDAO mapDAO, MapView mapView) {
        this.mapDAO = mapDAO;
        this.mapView = mapView;
    }

    public void processUserChoice(Scanner sc) {
        try {
            while (true) {
                mapView.displayMenu();
                switch (sc.nextInt()) {
                    case 1:
                        addMap();
                        break;
                    case 2:
                        showAllMaps();
                        break;
                    case 3:
                        updateMap();
                        break;
                    case 4:
                        deleteMap();
                        break;
                    case 5:
                        return;
                    default:
                        mapView.displayMessage("Invalid option.");
                }
            }
        } catch (SQLException e) {
            mapView.displayMessage("Operation failed: " + e.getMessage());
        }
    }

    private void addMap() throws SQLException {
        Map map = mapView.getMapInput();
        mapDAO.addMap(map);
        mapView.displayMessage("Map added successfully!");
    }

    private void showAllMaps() throws SQLException {
        List<Map> maps = mapDAO.getAllMaps();
        mapView.displayMaps(maps);
    }

    private void updateMap() throws SQLException {
        int mapId = mapView.getMapId();
        Map existingMap = mapDAO.getMapById(mapId);
        
        if (existingMap == null) {
            mapView.displayMessage("Map not found!");
            return;
        }

        Map updatedMap = mapView.getUpdateMapInput(existingMap);
        updatedMap.setMapID(mapId);
        mapDAO.updateMap(updatedMap);
        mapView.displayMessage("Map updated successfully!");
    }

    private void deleteMap() throws SQLException {
        int mapId = mapView.getMapId();
        Map existingMap = mapDAO.getMapById(mapId);

        if (existingMap == null) {
            mapView.displayMessage("Map not found!");
            return;
        }

        mapDAO.deleteMap(mapId);
        mapView.displayMessage("Map deleted successfully!");
    }
}
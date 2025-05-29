package main.java.controller;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.Main;
import main.java.model.AdminDAO;

import java.io.IOException;

public class MainViewController {
    @FXML
    private MapMenuController mapMenuController;
    @FXML
    private MapViewController mapViewController;
    @FXML
    private PlayerMenuController playerMenuController;
    @FXML
    private PlayerViewController playerViewController;
    @FXML
    private TeamMenuController teamMenuController;
    @FXML
    private TeamViewController teamViewController;
    @FXML
    private MatchMenuController matchMenuController;
    @FXML
    private MatchViewController matchViewController;
    @FXML
    private MatchStatsMenuController matchStatsMenuController;
    @FXML
    private MatchStatsViewController matchStatsViewController;
    @FXML
    private ReportsViewController reportsViewController;
    @FXML
    private PlayerAnalyticsFilterController playerAnalyticsFilterController;
    @FXML
    private AnchorPane ap, lowerap;
    @FXML
    private BorderPane bp;
    @FXML
    BorderPane innerbp;

    @FXML
    void home(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        innerbp.setCenter(ap);
        innerbp.setBottom(lowerap);
    }

    @FXML
    void maps(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("MapView");
        loadMenu("MapMenu");
        if (mapMenuController != null) {
            mapMenuController.setMapViewController(mapViewController);
        } else {
            throw new IllegalStateException("MapMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void matches(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("MatchView");
        loadMenu("MatchMenu");
        if (matchMenuController != null) {
            matchMenuController.setMatchViewController(matchViewController);
        } else {
            throw new IllegalStateException("MatchMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void players(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("PlayerView");
        loadMenu("PlayerMenu");
        if (playerMenuController != null) {
            playerMenuController.setPlayerViewController(playerViewController);
        } else {
            throw new IllegalStateException("PlayerMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void teams(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("TeamView");
        loadMenu("TeamMenu");
        if (teamMenuController != null) {
            teamMenuController.setTeamViewController(teamViewController);
        } else {
            throw new IllegalStateException("TeamMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void match_stats(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("MatchStatsView");
        loadMenu("MatchStatsMenu");
        if (matchStatsMenuController != null) {
            matchStatsMenuController.setMatchStatsViewController(matchStatsViewController);
        } else {
            throw new IllegalStateException("MatchStatsMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void reports(MouseEvent event) {
        setActiveButton((Button) event.getSource());

        loadPage("ReportsView");
        innerbp.setBottom(null);
        bp.setBottom(null);
        if (reportsViewController != null) {
            reportsViewController.loadAllData();
        } else {
            throw new IllegalStateException("ReportsViewController was not properly initialized");
        }
    }

    @FXML
    void logout(MouseEvent event) {
        AdminDAO.getInstance().logout();
        // Close the current window
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

        // Restart application
        try {
            new Main().start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/" + page + ".fxml"));
            Parent root = loader.load();
            innerbp.setCenter(root);
            bp.setCenter(innerbp);
            
            switch (page) {
                case "PlayerView" -> playerViewController = loader.getController();
                case "TeamView" -> teamViewController = loader.getController();
                case "MapView" -> mapViewController = loader.getController();
                case "MatchView" -> matchViewController = loader.getController();
                case "MatchStatsView" -> matchStatsViewController = loader.getController();
                case "ReportsView" -> reportsViewController = loader.getController();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMenu(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/" + page + ".fxml"));
            Parent root = loader.load();
            innerbp.setBottom(root);
            bp.setCenter(innerbp);
            
            switch (page) {
                case "PlayerMenu" -> playerMenuController = loader.getController();
                case "TeamMenu" -> teamMenuController = loader.getController();
                case "MapMenu" -> mapMenuController = loader.getController();
                case "MatchMenu" -> matchMenuController = loader.getController();
                case "MatchStatsMenu" -> matchStatsMenuController = loader.getController();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setActiveButton(Button clickedButton) {
        // Remove the active class from all buttons
        bp.lookupAll(".menu-button").forEach(node -> {
            node.getStyleClass().remove("active");
        });
        // Add an active class to clicked button
        clickedButton.getStyleClass().add("active");
    }
}
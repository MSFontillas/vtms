package main.java.controller;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainViewController {

    @FXML
    private PlayerMenuController playerMenuController;

    @FXML
    private PlayerViewController playerViewController;

    @FXML
    private AnchorPane ap, lowerap;

    @FXML
    private BorderPane bp, innerbp;

    @FXML
    void home() {
        innerbp.setCenter(ap);
        innerbp.setBottom(lowerap);
    }

    @FXML
    void maps() {
        loadPage("MapView");
        loadMenu("MapMenu");
    }

    @FXML
    void matches() {
        loadPage("MatchView");
        loadMenu("MatchMenu");
    }

    @FXML
    void players() {
        loadPage("PlayerView");
        loadMenu("PlayerMenu");
        if (playerMenuController != null) {
            playerMenuController.setPlayerViewController(playerViewController);
        } else {
            throw new IllegalStateException("PlayerMenuController was not properly initialized. Check FXML file for correct fx:id");
        }
    }

    @FXML
    void teams() {
        loadPage("TeamView");
        loadMenu("TeamMenu");
    }

    @FXML
    void match_stats() {
        loadPage("MatchStatsView");
        loadMenu("MatchStatsMenu");
    }

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/" + page + ".fxml"));
            Parent root = loader.load();
            innerbp.setCenter(root);
            bp.setCenter(innerbp);
            
            // Store controller reference if it's the PlayerView
            if (page.equals("PlayerView")) {
                playerViewController = loader.getController();
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
            
            // Store controller reference if it's the PlayerMenu
            if (page.equals("PlayerMenu")) {
                playerMenuController = loader.getController();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
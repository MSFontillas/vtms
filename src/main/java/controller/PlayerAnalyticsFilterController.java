package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import main.java.model.PlayerAnalytics;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PlayerAnalyticsFilterController implements Initializable {
    @FXML private TextField playerField;
    @FXML private TextField teamField;
    @FXML private TextField roleField;
    @FXML private SplitMenuButton filterButton;
    
    private ReportsViewController reportsViewController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterButton.setOnAction(e -> handleFilter());
    }

    public void setReportsViewController(ReportsViewController controller) {
        this.reportsViewController = controller;
    }

    private void handleFilter() {
        if (reportsViewController != null) {
            String player = playerField.getText().toLowerCase().trim();
            String team = teamField.getText().toLowerCase().trim();
            String role = roleField.getText().toLowerCase().trim();

            Predicate<PlayerAnalytics> filter = p -> 
                (player.isEmpty() || p.getPlayerName().toLowerCase().contains(player) || 
                 p.getIgn().toLowerCase().contains(player)) &&
                (team.isEmpty() || p.getTeamName().toLowerCase().contains(team)) &&
                (role.isEmpty() || p.getRole().toLowerCase().contains(role));

            reportsViewController.filterPlayerAnalytics(filter);
        }
    }

    public void clearFields() {
        playerField.clear();
        teamField.clear();
        roleField.clear();
    }
}
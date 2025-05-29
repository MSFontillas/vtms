package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.model.PlayerAnalytics;
import main.java.model.PlayerDAO;
import main.java.model.TeamDAO;
import main.java.util.AutoCompleteUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PlayerAnalyticsFilterController implements Initializable {
    @FXML private TextField playerField;
    @FXML private TextField teamField;
    @FXML private TextField roleField;
    @FXML private Button filterButton;
    
    private ReportsViewController reportsViewController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Prepare suggestions
        List<String> playerSuggestions = new ArrayList<>();
        List<String> teamSuggestions = new ArrayList<>();
        List<String> roleSuggestions = List.of("Duelist", "Initiator", "Controller", "Sentinel", "Flex");

        try {
            PlayerDAO playerDAO = new PlayerDAO();
            playerDAO.getAllPlayers().forEach(p -> {
                playerSuggestions.add(p.getPlayerName());
                playerSuggestions.add(p.getIgn());
            });

            TeamDAO teamDAO = new TeamDAO();
            teamDAO.getAllTeams().forEach(t -> teamSuggestions.add(t.getTeamName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AutoCompleteUtil.setupAutoComplete(playerField, playerSuggestions);
        AutoCompleteUtil.setupAutoComplete(teamField, teamSuggestions);
        AutoCompleteUtil.setupAutoComplete(roleField, roleSuggestions);
    }

    public void setReportsViewController(ReportsViewController controller) {
        this.reportsViewController = controller;
    }

    @FXML
    public void handleFilter() {
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
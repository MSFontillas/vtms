package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.layout.AnchorPane;
import main.java.model.MapStatistics;
import main.java.model.PlayerAnalytics;
import main.java.model.ReportsDAO;
import main.java.model.TeamPerformance;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportsViewController implements Initializable {
    @FXML 
    private PlayerAnalyticsFilterController playerAnalyticsFilterControllerController;

    @FXML
    private AnchorPane playerAnalyticsFilterController;

    private List<PlayerAnalytics> allPlayerAnalytics;  // Store all analytics
    
    @FXML private TableView<PlayerAnalytics> playerAnalyticsTable;
    @FXML private TableColumn<PlayerAnalytics, String> playerName;
    @FXML private TableColumn<PlayerAnalytics, String> ign;
    @FXML private TableColumn<PlayerAnalytics, String> role;
    @FXML private TableColumn<PlayerAnalytics, String> teamName;
    @FXML private TableColumn<PlayerAnalytics, Double> avgKills;
    @FXML private TableColumn<PlayerAnalytics, Double> avgDeaths;
    @FXML private TableColumn<PlayerAnalytics, Double> avgAssists;
    @FXML private TableColumn<PlayerAnalytics, Double> kdaRatio;
    @FXML private TableColumn<PlayerAnalytics, Integer> mvpCount;

    @FXML private TableView<MapStatistics> mapStatsTable;
    @FXML private TableColumn<MapStatistics, String> mapName;
    @FXML private TableColumn<MapStatistics, Integer> timesPlayed;
    @FXML private TableColumn<MapStatistics, Double> avgKillsPerMatch;

    @FXML private TableView<TeamPerformance> teamPerformanceTable;
    @FXML private TableColumn<TeamPerformance, String> teamNamePerf;
    @FXML private TableColumn<TeamPerformance, Integer> totalMatches;
    @FXML private TableColumn<TeamPerformance, Integer> matchesWon;
    @FXML private TableColumn<TeamPerformance, Double> avgTeamKills;

    private ReportsDAO reportsDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportsDAO = new ReportsDAO();
        allPlayerAnalytics = new ArrayList<>();
        
        initializePlayerAnalyticsTable();
        initializeMapStatsTable();
        initializeTeamPerformanceTable();
        
        // Set this controller instance in the filter controller
        if (playerAnalyticsFilterControllerController != null) {
            playerAnalyticsFilterControllerController.setReportsViewController(this);
        }
        
        loadAllData();
    }

    private void initializePlayerAnalyticsTable() {
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        ign.setCellValueFactory(new PropertyValueFactory<>("ign"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        avgKills.setCellValueFactory(new PropertyValueFactory<>("avgKills"));
        avgDeaths.setCellValueFactory(new PropertyValueFactory<>("avgDeaths"));
        avgAssists.setCellValueFactory(new PropertyValueFactory<>("avgAssists"));
        kdaRatio.setCellValueFactory(new PropertyValueFactory<>("kdaRatio"));
        mvpCount.setCellValueFactory(new PropertyValueFactory<>("mvpCount"));
    }

    private void initializeMapStatsTable() {
        mapName.setCellValueFactory(new PropertyValueFactory<>("mapName"));
        timesPlayed.setCellValueFactory(new PropertyValueFactory<>("timesPlayed"));
        avgKillsPerMatch.setCellValueFactory(new PropertyValueFactory<>("avgKillsPerMatch"));
    }

    private void initializeTeamPerformanceTable() {
        teamNamePerf.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        totalMatches.setCellValueFactory(new PropertyValueFactory<>("totalMatches"));
        matchesWon.setCellValueFactory(new PropertyValueFactory<>("matchesWon"));
        avgTeamKills.setCellValueFactory(new PropertyValueFactory<>("avgTeamKills"));
    }

    public void loadAllData() {
        try {
            loadPlayerAnalytics();
            loadMapStatistics();
            loadTeamPerformance();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }

    private void loadPlayerAnalytics() throws SQLException {
        List<Map<String, Object>> data = reportsDAO.getPlayerAnalytics();
        allPlayerAnalytics.clear();
        
        for (Map<String, Object> row : data) {
            allPlayerAnalytics.add(new PlayerAnalytics(row));
        }
        
        playerAnalyticsTable.setItems(FXCollections.observableArrayList(allPlayerAnalytics));
    }

    private void loadMapStatistics() throws SQLException {
        List<Map<String, Object>> data = reportsDAO.getMapStatistics();
        List<MapStatistics> stats = new ArrayList<>();
        
        for (Map<String, Object> row : data) {
            stats.add(new MapStatistics(row));
        }
        
        mapStatsTable.setItems(FXCollections.observableArrayList(stats));
    }

    private void loadTeamPerformance() throws SQLException {
        List<Map<String, Object>> data = reportsDAO.getTeamPerformances();
        List<TeamPerformance> performances = new ArrayList<>();
        
        for (Map<String, Object> row : data) {
            performances.add(new TeamPerformance(row));
        }
        
        teamPerformanceTable.setItems(FXCollections.observableArrayList(performances));
    }

    public void filterPlayerAnalytics(Predicate<PlayerAnalytics> filter) {
        List<PlayerAnalytics> filteredList = allPlayerAnalytics.stream()
            .filter(filter)
            .collect(Collectors.toList());
        playerAnalyticsTable.setItems(FXCollections.observableArrayList(filteredList));
    }
}
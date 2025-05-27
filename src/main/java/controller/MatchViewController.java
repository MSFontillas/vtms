package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.model.Match;
import main.java.model.MatchDAO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class MatchViewController implements Initializable {
    @FXML
    private TableView<Match> matchTable;
    
    @FXML
    private TableColumn<Match, Integer> matchID;
    
    @FXML
    private TableColumn<Match, String> teamA;
    
    @FXML
    private TableColumn<Match, String> teamB;
    
    @FXML
    private TableColumn<Match, String> winner;
    
    @FXML
    private TableColumn<Match, String> map;
    
    @FXML
    private TableColumn<Match, LocalDate> date;
    
    @FXML
    private TableColumn<Match, LocalTime> time;
    
    private MatchDAO matchDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matchDAO = new MatchDAO();
        
        // Initialize column cell value factories
        matchID.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        teamA.setCellValueFactory(new PropertyValueFactory<>("teamAName"));
        teamB.setCellValueFactory(new PropertyValueFactory<>("teamBName"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        map.setCellValueFactory(new PropertyValueFactory<>("mapName"));
        date.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
        time.setCellValueFactory(new PropertyValueFactory<>("matchTime"));
        
        // Load initial data
        loadMatchData();
    }
    
    public void loadMatchData() {
        try {
            matchTable.getItems().clear();
            matchTable.getItems().addAll(matchDAO.searchMatches("", "", null, "", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Match> searchMatches(String teamA, String teamB) throws SQLException {
        return matchDAO.searchMatchesbyTeam(teamA, teamB);
    }
    
    public TableView<Match> getMatchTable() {
        return matchTable;
    }
}
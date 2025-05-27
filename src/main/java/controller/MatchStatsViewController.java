package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import main.java.model.MatchStats;
import main.java.model.MatchStatsDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MatchStatsViewController implements Initializable {
    @FXML
    private TableView<MatchStats> matchStatsTable;

    @FXML
    private TableColumn<MatchStats, Integer> statID;

    @FXML
    private TableColumn<MatchStats, Integer> matchID;

    @FXML
    private TableColumn<MatchStats, String> playerName;

    @FXML
    private TableColumn<MatchStats, String> teamName;

    @FXML
    private TableColumn<MatchStats, Integer> kills;

    @FXML
    private TableColumn<MatchStats, Integer> deaths;

    @FXML
    private TableColumn<MatchStats, Integer> assists;

    @FXML
    private TableColumn<MatchStats, Boolean> mvp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statID.setCellValueFactory(new PropertyValueFactory<>("statID"));
        matchID.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        kills.setCellValueFactory(new PropertyValueFactory<>("kills"));
        deaths.setCellValueFactory(new PropertyValueFactory<>("deaths"));
        assists.setCellValueFactory(new PropertyValueFactory<>("assists"));
        mvp.setCellValueFactory(new PropertyValueFactory<>("mvp"));

        loadMatchStats();
    }

    private void loadMatchStats() {
        try {
            MatchStatsDAO matchDAO = new MatchStatsDAO();
            matchStatsTable.setItems(FXCollections.observableArrayList(matchDAO.getAllMatchStats()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
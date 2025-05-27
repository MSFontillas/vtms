package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import main.java.model.Match;
import main.java.model.MatchDAO;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
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
    private TableColumn<Match, Date> date;

    @FXML
    private TableColumn<Match, Time> time;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matchID.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        teamA.setCellValueFactory(new PropertyValueFactory<>("teamAName"));
        teamB.setCellValueFactory(new PropertyValueFactory<>("teamBName"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        map.setCellValueFactory(new PropertyValueFactory<>("mapName"));
        date.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
        time.setCellValueFactory(new PropertyValueFactory<>("matchTime"));

        loadMatchData();
    }

    private void loadMatchData() {
        try {
            MatchDAO matchDAO = new MatchDAO();
            matchTable.setItems(FXCollections.observableArrayList(matchDAO.getAllMatches()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
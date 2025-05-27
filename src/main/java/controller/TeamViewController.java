package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import main.java.model.Team;
import main.java.model.TeamDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeamViewController implements Initializable {
    @FXML
    private TableView<Team> teamTable;

    @FXML
    private TableColumn<Team, Integer> teamID;

    @FXML
    private TableColumn<Team, String> teamName;

    @FXML
    private TableColumn<Team, String> coach;

    @FXML
    private TableColumn<Team, String> region;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teamID.setCellValueFactory(new PropertyValueFactory<>("teamID"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        coach.setCellValueFactory(new PropertyValueFactory<>("coach"));
        region.setCellValueFactory(new PropertyValueFactory<>("region"));

        loadTeamData();
    }

    private void loadTeamData() {
        try {
            TeamDAO teamDAO = new TeamDAO();
            teamTable.setItems(FXCollections.observableArrayList(teamDAO.getAllTeams()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
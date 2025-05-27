package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
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
        // Set up column cell value factories
        teamID.setCellValueFactory(new PropertyValueFactory<>("teamID"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        coach.setCellValueFactory(new PropertyValueFactory<>("coach"));
        region.setCellValueFactory(new PropertyValueFactory<>("region"));

        // Configure selection mode
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Load initial data
        loadTeamData();
    }

    // Getter for the table view (needed by TeamMenuController)
    public TableView<Team> getTeamTable() {
        return teamTable;
    }

    // Make loadTeamData public so it can be called from TeamMenuController
    public void loadTeamData() {
        try {
            TeamDAO teamDAO = new TeamDAO();
            teamTable.setItems(FXCollections.observableArrayList(teamDAO.getAllTeams()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
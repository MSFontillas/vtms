package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import main.java.model.Player;
import main.java.model.PlayerDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlayerViewController implements Initializable {

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> ign;

    @FXML
    private TableColumn<Player, Integer> playerID;

    @FXML
    private TableColumn<Player, String> playerName;

    @FXML
    private TableColumn<Player, String> role;

    @FXML
    private TableColumn<Player, String> teamID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up column cell value factories
        playerID.setCellValueFactory(new PropertyValueFactory<>("playerID"));
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        ign.setCellValueFactory(new PropertyValueFactory<>("ign"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        teamID.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        // Configure selection mode
        playerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        loadPlayerData();
    }

    public TableView<Player> getPlayerTable() {
        return playerTable;
    }

    // Make loadPlayerData public so it can be called from PlayerMenuController
    public void loadPlayerData() {
        try {
            PlayerDAO playerDAO = new PlayerDAO();
            playerTable.setItems(FXCollections.observableArrayList(playerDAO.getAllPlayers()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
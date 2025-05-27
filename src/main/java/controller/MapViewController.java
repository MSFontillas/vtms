package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import main.java.model.Map;
import main.java.model.MapDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MapViewController implements Initializable {
    @FXML
    private TableView<Map> mapTable;

    @FXML
    private TableColumn<Map, Integer> mapID;

    @FXML
    private TableColumn<Map, String> mapName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapID.setCellValueFactory(new PropertyValueFactory<>("mapID"));
        mapName.setCellValueFactory(new PropertyValueFactory<>("mapName"));

        loadMapData();
    }

    private void loadMapData() {
        try {
            MapDAO mapDAO = new MapDAO();
            mapTable.setItems(FXCollections.observableArrayList(mapDAO.getAllMaps()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.model.AdminDAO;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private boolean authenticated = false;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (AdminDAO.getInstance().login(username, password)) {
            authenticated = true;
            ((Stage) loginButton.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
            passwordField.clear();
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
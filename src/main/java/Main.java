package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import main.java.controller.LoginController;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Show login dialog first
        if (!showLoginDialog()) {
            // If login failed, exit the application
            System.exit(0);
        }

        // If login successful, proceed with main application
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main/resources/view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        primaryStage.setTitle("Valorant Tournament Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private boolean showLoginDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Admin Login");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        
        LoginController controller = loader.getController();
        loginStage.showAndWait();
        
        return controller.isAuthenticated();
    }

    public static void main(String[] args) {
        launch();
    }
}
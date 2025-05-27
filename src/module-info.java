module your.module.name {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    exports main.java;
    exports main.java.controller;
    exports main.java.model;
    exports main.java.view;
    
    opens main.java.controller to javafx.fxml;
    opens main.java.model to javafx.base;
    opens main.java.view to javafx.fxml;
}
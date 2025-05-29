module main.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    exports main.java;
    exports main.java.controller;
    exports main.java.model;
    exports main.java.util;

    opens main.java to javafx.fxml;
    opens main.java.model to javafx.base;
    opens main.java.controller to javafx.base, javafx.fxml;
    opens main.java.util to javafx.base, javafx.fxml;
}
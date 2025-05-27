package main.java.util;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.geometry.Side;

import java.util.List;

public class AutoCompleteUtil {
    public static void setupAutoComplete(TextField textField, List<String> suggestions) {
        ContextMenu contextMenu = new ContextMenu();
        
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                contextMenu.hide();
                return;
            }
            
            contextMenu.getItems().clear();
            String text = newValue.toLowerCase();
            
            suggestions.stream()
                .filter(s -> s.toLowerCase().contains(text))
                .limit(10) // Limit to 10 suggestions for better performance
                .forEach(s -> {
                    MenuItem item = new MenuItem(s);
                    item.setOnAction(e -> {
                        textField.setText(s);
                        contextMenu.hide();
                    });
                    contextMenu.getItems().add(item);
                });
            
            if (!contextMenu.getItems().isEmpty()) {
                if (!contextMenu.isShowing()) {
                    contextMenu.show(textField, Side.BOTTOM, 0, 0);
                }
            } else {
                contextMenu.hide();
            }
        });
        
        // Hide context menu when focus is lost
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                contextMenu.hide();
            }
        });
        
        // Add escape key handler
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                contextMenu.hide();
                textField.clear();
            }
        });
    }
}
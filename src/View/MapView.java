package View;// View/MapView.java

import Model.Map;

import java.util.List;
import java.util.Scanner;

public class MapView {
    private Scanner scanner;

    public MapView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--- Manage Maps ---");
        System.out.println("1. Add Map");
        System.out.println("2. View All Maps");
        System.out.println("3. Update Map");
        System.out.println("4. Delete Map");
        System.out.println("5. Back");
        System.out.print("Choose an option: ");
    }

    public Map getMapInput() {
        Map map = new Map();
        System.out.println("Enter map name: ");
        map.setMapName(scanner.nextLine());
        return map;
    }

    public int getMapId() {
        System.out.println("Enter mapID: ");
        return scanner.nextInt();
    }

    public void displayMaps(List<Map> maps) {
        System.out.println("\n--- Maps List ---");
        System.out.printf("| %-5s | %-20s |\n", "ID", "Map Name");
        System.out.println("-".repeat(30));

        for (Map map : maps) {
            System.out.printf("| %-5d | %-20s |\n",
                    map.getMapID(),
                    map.getMapName());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
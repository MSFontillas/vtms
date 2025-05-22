// Controller/PlayerController.java
package controller;

import model.Player;
import model.PlayerDAO;
import view.PlayerView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlayerController {
    private PlayerDAO playerDAO;
    private PlayerView playerView;

    public PlayerController(PlayerDAO playerDAO, PlayerView playerView) {
        this.playerDAO = playerDAO;
        this.playerView = playerView;
    }

    public void processUserChoice(Scanner sc) {
        try {
            while (true) {
                playerView.displayMenu();
                switch (sc.nextInt()) {
                    case 1:
                        addPlayer();
                        break;
                    case 2:
                        showAllPlayers();
                        break;
                    case 3:
                        updatePlayer();
                        break;
                    case 4:
                        deletePlayer();
                        break;
                    case 5:
                        showPlayerStatistics();
                        break;
                    case 6:
                        return;
                    default:
                        playerView.displayMessage("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            playerView.displayMessage("Error: " + e.getMessage());
        }
    }

    private void addPlayer() throws SQLException {
        Player player = playerView.getPlayerInput();
        playerDAO.addPlayer(player);
        playerView.displayMessage("Player added successfully!");
    }

    private void showAllPlayers() throws SQLException {
        List<Player> players = playerDAO.getAllPlayers();
        playerView.displayPlayers(players);
    }

    private void updatePlayer() throws SQLException {
        int playerId = playerView.getPlayerId();
        Player existingPlayer = playerDAO.getPlayerById(playerId);

        if (existingPlayer == null) {
            playerView.displayMessage("Player not found!");
            return;
        }

        Player updatedPlayer = playerView.getPlayerInput();
        updatedPlayer.setPlayerID(playerId);
        playerDAO.updatePlayer(updatedPlayer);
        playerView.displayMessage("Player updated successfully!");
    }

    private void deletePlayer() throws SQLException {
        int playerId = playerView.getPlayerId();
        Player existingPlayer = playerDAO.getPlayerById(playerId);

        if (existingPlayer == null) {
            playerView.displayMessage("Player not found!");
            return;
        }

        playerDAO.deletePlayer(playerId);
        playerView.displayMessage("Player deleted successfully!");
    }

    private void showPlayerStatistics() throws SQLException {
        int playerId = playerView.getPlayerId();
        Player existingPlayer = playerDAO.getPlayerById(playerId);

        if (existingPlayer == null) {
            playerView.displayMessage("Player not found!");
            return;
        }

        Map<String, Object> statistics = playerDAO.getPlayerStatistics(playerId);
        playerView.displayPlayerStatistics(statistics);
    }
}
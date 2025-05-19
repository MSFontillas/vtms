// Valorant Tournament Management System - Main Application

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TeamDAO teamDAO = new TeamDAO();
        PlayerDAO playerDAO = new PlayerDAO();
        MapDAO mapDAO = new MapDAO();
        MatchDAO matchDAO = new MatchDAO();
        MatchStatsDAO matchStatsDAO = new MatchStatsDAO();
        
        while (true) {
            System.out.println("\n--- Valorant Tournament Management System ---");
            System.out.println("1. Manage Teams");
            System.out.println("2. Manage Players");
            System.out.println("3. Manage Maps");
            System.out.println("4. Manage Matches");
            System.out.println("5. Manage Match Stats");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manageTeams(teamDAO);
                    break;
                case 2:
                    managePlayers(playerDAO);
                    break;
                case 3:
                    manageMaps(mapDAO);
                    break;
                case 4:
                    manageMatches(matchDAO);
                    break;
                case 5:
                    manageMatchStats(matchStatsDAO);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageTeams(TeamDAO teamDAO) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Manage Teams ---");
            System.out.println("1. Add Team");
            System.out.println("2. View All Teams");
            System.out.println("3. Update Team");
            System.out.println("4. Delete Team");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        teamDAO.addTeam();
                        break;
                    case 2:
                        teamDAO.getAllTeams().forEach(System.out::println);
                        break;
                    case 3:
                        teamDAO.updateTeam();
                        break;
                    case 4:
                        teamDAO.deleteTeam();
                        break;
                    case 5:
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void managePlayers(PlayerDAO playerDAO) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Manage Players ---");
            System.out.println("1. Add Player");
            System.out.println("2. View All Players");
            System.out.println("3. Update Player");
            System.out.println("4. Delete Player");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        playerDAO.addPlayer();
                        break;
                    case 2:
                        playerDAO.getAllPlayers().forEach(System.out::println);
                        break;
                    case 3:
                        playerDAO.updatePlayer();
                        break;
                    case 4:
                        playerDAO.deletePlayer();
                        break;
                    case 5:
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void manageMaps(MapDAO mapDAO) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Manage Maps ---");
            System.out.println("1. Add Map");
            System.out.println("2. View All Maps");
            System.out.println("3. Update Map");
            System.out.println("4. Delete Map");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        mapDAO.addMap();
                        break;
                    case 2:
                        mapDAO.getAllMaps().forEach(System.out::println);
                        break;
                    case 3:
                        mapDAO.updateMap();
                        break;
                    case 4:
                        mapDAO.deleteMap();
                        break;
                    case 5:
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void manageMatches(MatchDAO matchDAO) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Manage Matches ---");
            System.out.println("1. Add Match");
            System.out.println("2. View All Matches");
            System.out.println("3. Update Match");
            System.out.println("4. Delete Match");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        matchDAO.addMatch();
                        break;
                    case 2:
                        matchDAO.getAllMatches().forEach(System.out::println);
                        break;
                    case 3:
                        matchDAO.updateMatch();
                        break;
                    case 4:
                        matchDAO.deleteMatch();
                        break;
                    case 5:
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void manageMatchStats(MatchStatsDAO matchStatsDAO) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Manage Match Stats ---");
            System.out.println("1. Add Match Stat");
            System.out.println("2. View All Match Stats");
            System.out.println("3. Update Match Stat");
            System.out.println("4. Delete Match Stat");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        matchStatsDAO.addMatchStat();
                        break;
                    case 2:
                        matchStatsDAO.getAllMatchStats().forEach(System.out::println);
                        break;
                    case 3:
                        matchStatsDAO.updateMatchStat();
                        break;
                    case 4:
                        matchStatsDAO.deleteMatchStat();
                        break;
                    case 5:
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }
}
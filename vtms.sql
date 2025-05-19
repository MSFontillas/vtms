-- Valorant Tournament Management System - SQL Table Creation Scripts

-- Database Creation
CREATE DATABASE IF NOT EXISTS valorantTournamentDB;
USE valorantTournamentDB;

-- Dropping existing tables if they exist
DROP TABLE IF EXISTS match_Stats;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS maps;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS teams;

-- 1. Teams Table
CREATE TABLE teams (
    teamID INT AUTO_INCREMENT PRIMARY KEY,
    teamName VARCHAR(100) NOT NULL,
    coach VARCHAR(100),
    region VARCHAR(50)
);

-- 2. Players Table
CREATE TABLE players (
    playerID INT AUTO_INCREMENT PRIMARY KEY,
    playerName VARCHAR(100) NOT NULL,
    ign VARCHAR(50) NOT NULL,
    Role VARCHAR(50),
    teamID INT,
    FOREIGN KEY (teamID) REFERENCES teams(teamID) ON DELETE SET NULL
);

-- 3. Maps Table
CREATE TABLE maps (
    mapID INT AUTO_INCREMENT PRIMARY KEY,
    mapName VARCHAR(100) NOT NULL
);

-- 4. Matches Table
CREATE TABLE matches (
    matchID INT AUTO_INCREMENT PRIMARY KEY,
    Date DATE NOT NULL,
    Time TIME NOT NULL,
    teamA_ID INT NOT NULL,
    teamB_ID INT NOT NULL,
    winner_ID INT,
    mapID INT,
    FOREIGN KEY (teamA_ID) REFERENCES teams(teamID) ON DELETE CASCADE,
    FOREIGN KEY (teamB_ID) REFERENCES teams(teamID) ON DELETE CASCADE,
    FOREIGN KEY (winner_ID) REFERENCES teams(teamID) ON DELETE SET NULL,
    FOREIGN KEY (mapID) REFERENCES maps(mapID) ON DELETE SET NULL
);

-- 5. Match_Stats Table
CREATE TABLE match_Stats (
    statID INT AUTO_INCREMENT PRIMARY KEY,
    matchID INT NOT NULL,
    playerID INT NOT NULL,
    kills INT DEFAULT 0,
    deaths INT DEFAULT 0,
    assists INT DEFAULT 0,
    mvp BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (matchID) REFERENCES matches(matchID) ON DELETE CASCADE,
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE
);

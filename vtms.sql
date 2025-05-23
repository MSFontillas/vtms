-- Database Creation
DROP DATABASE IF EXISTS valorantTournamentDB;
CREATE DATABASE IF NOT EXISTS valorantTournamentDB;
USE valorantTournamentDB;

-- Drop Views
DROP VIEW IF EXISTS team_performance_summary;
DROP VIEW IF EXISTS player_analytics;
DROP VIEW IF EXISTS map_statistics;

-- Drop Stored Procedures and Functions
DROP PROCEDURE IF EXISTS GetMatchStatistics;
DROP PROCEDURE IF EXISTS GetPlayerStatistics;
DROP FUNCTION IF EXISTS CalculatePlayerKDA;

-- Drop Triggers
DROP TRIGGER IF EXISTS after_match_update;

-- Drop Tables (in correct order due to foreign key constraints)
DROP TABLE IF EXISTS match_history_log;
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


-- Views for Reports
CREATE VIEW team_performance_summary AS
SELECT 
    t.teamName,
    COUNT(DISTINCT m.matchID) as total_matches,
    COUNT(CASE WHEN m.winner_ID = t.teamID THEN 1 END) as matches_won,
    ROUND(AVG(ms.kills), 2) as avg_team_kills
FROM teams t
LEFT JOIN matches m ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID
LEFT JOIN match_Stats ms ON m.matchID = ms.matchID
LEFT JOIN players p ON ms.playerID = p.playerID AND p.teamID = t.teamID
GROUP BY t.teamID, t.teamName;

CREATE VIEW player_analytics AS
SELECT 
    p.playerName,
    p.ign,
    p.Role,
    t.teamName,
    ROUND(AVG(ms.kills), 2) as avg_kills,
    ROUND(AVG(ms.deaths), 2) as avg_deaths,
    ROUND(AVG(ms.assists), 2) as avg_assists,
    ROUND((SUM(ms.kills) + SUM(ms.assists)) / NULLIF(SUM(ms.deaths), 0), 2) as kda_ratio,
    COUNT(DISTINCT CASE WHEN ms.mvp = 1 THEN ms.matchID END) as mvp_count
FROM players p
JOIN teams t ON p.teamID = t.teamID
LEFT JOIN match_Stats ms ON p.playerID = ms.playerID
GROUP BY p.playerID, p.playerName, p.ign, p.Role, t.teamName;

CREATE VIEW map_statistics AS
SELECT 
    m.mapName,
    COUNT(mt.matchID) as times_played,
    ROUND(AVG(ms.kills), 2) as avg_kills_per_match
FROM maps m
LEFT JOIN matches mt ON m.mapID = mt.mapID
LEFT JOIN match_Stats ms ON mt.matchID = ms.matchID
GROUP BY m.mapID, m.mapName;


-- LOGGING TABLE AND TRIGGER
CREATE TABLE match_history_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    matchID INT,
    action_type VARCHAR(50),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT
);

DELIMITER //
CREATE TRIGGER after_match_update
AFTER UPDATE ON matches
FOR EACH ROW
BEGIN
    INSERT INTO match_history_log (matchID, action_type, details)
    VALUES (
        NEW.matchID,
        'MATCH_RESULT_UPDATE',
        CONCAT('Match winner updated. Winner Team ID: ', NEW.winner_ID)
    );
END //
DELIMITER ;


-- ALTER TABLE
ALTER TABLE players ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE players ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE teams ADD COLUMN region_rank INT;


-- PROCEDURE
DELIMITER //

CREATE PROCEDURE GetMatchStatistics()
BEGIN
    -- INNER JOIN example
    SELECT 
        m.matchID,
        ta.teamName as team_a,
        tb.teamName as team_b,
        w.teamName as winner,
        mp.mapName
    FROM matches m
    INNER JOIN teams ta ON m.teamA_ID = ta.teamID
    INNER JOIN teams tb ON m.teamB_ID = tb.teamID
    INNER JOIN teams w ON m.winner_ID = w.teamID
    INNER JOIN maps mp ON m.mapID = mp.mapID;

    -- RIGHT JOIN example
    SELECT 
        t.teamName,
        COUNT(m.matchID) as matches_played
    FROM matches m
    RIGHT JOIN teams t ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID
    GROUP BY t.teamID, t.teamName;

    -- FULL OUTER JOIN simulation (MySQL doesn't support FULL OUTER directly)
    SELECT t.teamName, m.matchID
    FROM teams t
    LEFT JOIN matches m ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID
    UNION
    SELECT t.teamName, m.matchID
    FROM teams t
    RIGHT JOIN matches m ON t.teamID = m.teamA_ID OR t.teamID = m.teamB_ID;
END //

DELIMITER ;


-- STORED FUNCTION
DELIMITER //

CREATE FUNCTION CalculatePlayerKDA(p_playerID INT) 
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE kda DECIMAL(10,2);
    
    SELECT 
        ROUND((SUM(kills) + SUM(assists)) / NULLIF(SUM(deaths), 0), 2)
    INTO kda
    FROM match_Stats
    WHERE playerID = p_playerID;
    
    RETURN IFNULL(kda, 0.00);
END //

DELIMITER ;


-- STORED PROCEDURE
DELIMITER //

CREATE PROCEDURE GetPlayerStatistics(IN p_playerID INT)
BEGIN
    -- Player basic info with INNER JOIN
    SELECT 
        p.playerName,
        p.ign,
        p.Role,
        t.teamName,
        CalculatePlayerKDA(p.playerID) as kda_ratio
    FROM players p
    INNER JOIN teams t ON p.teamID = t.teamID
    WHERE p.playerID = p_playerID;
    
    -- Player match history with LEFT JOIN
    SELECT 
        m.matchID,
        m.Date,
        ms.kills,
        ms.deaths,
        ms.assists,
        ms.mvp
    FROM players p
    LEFT JOIN match_Stats ms ON p.playerID = ms.playerID
    LEFT JOIN matches m ON ms.matchID = m.matchID
    WHERE p.playerID = p_playerID;
END //

DELIMITER ;
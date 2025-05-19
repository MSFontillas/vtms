-- Valorant Tournament Management System - Sample Data Insert Script

USE ValorantTournamentDB;

INSERT INTO Teams (TeamID, TeamName, Coach, Region) VALUES
(1, 'Sentinels', 'Kaplan', 'North America'),
(2, 'Fnatic', 'Mini', 'EMEA'),
(3, 'DRX', 'termi', 'Korea'),
(4, 'LOUD', 'fRoD', 'Brazil'),
(5, 'Paper Rex', 'Alecks', 'Asia Pacific'),
(6, 'Team Liquid', 'Sliggy', 'EMEA'),
(7, 'Optic Gaming', 'Cheta', 'North America'),
(8, 'EDward Gaming', 'AfteR', 'China'),
(9, 'ZETA DIVISION', 'crow', 'Japan'),
(10, 'KRÜ Esports', 'Klaus', 'Latin America'),
(11, 'FURIA', 'Carlão', 'Brazil'),
(12, '100 Thieves', 'Mikes', 'North America'),
(13, 'T1', 'Autumn', 'Korea'),
(14, 'Team Heretics', 'loWel', 'EMEA'),
(15, 'Rex Regum Qeon', 'Eeyore', 'Asia Pacific');

INSERT INTO Players (PlayerID, PlayerName, IGN, Role, TeamID) VALUES
-- Sentinels
(1, 'Tyson Ngo', 'TenZ', 'Duelist', 1),
(2, 'Shahzeb Khan', 'ShahZaM', 'Initiator', 1),
(3, 'Michael Gulino', 'dapr', 'Controller', 1),
(4, 'Hunter Mims', 'SicK', 'Flex', 1),
(5, 'Jared Gitlin', 'zombs', 'Controller', 1),

-- Fnatic
(6, 'Jake Howlett', 'Boaster', 'Initiator', 2),
(7, 'Nikita Sirmitev', 'Derke', 'Duelist', 2),
(8, 'James Orfila', 'Mistic', 'Controller', 2),
(9, 'Dimitri De Natale', 'Dimasick', 'Flex', 2),
(10, 'Marcel Neumann', 'MAGNUM', 'Sentinel', 2),

-- DRX
(11, 'Kim Gu-taek', 'stax', 'Initiator', 3),
(12, 'Goo Sang-min', 'Rb', 'Duelist', 3),
(13, 'Yu Byung-chul', 'Zest', 'Flex', 3),
(14, 'Kim Myeong-kwan', 'Mako', 'Controller', 3),
(15, 'Jung Ji-won', 'BuZz', 'Sentinel', 3),

-- ... (continuing for all 15 teams with 5 players each)
-- Sample for 3 more teams:

-- LOUD
(16, 'Erick Santos', 'aspas', 'Duelist', 4),
(17, 'Matias Delipetro', 'Saadhak', 'Initiator', 4),
(18, 'Bryan Luna', 'cauanzin', 'Flex', 4),
(19, 'Gabriel Lima', 'qck', 'Controller', 4),
(20, 'Arthur Santos', 'tuyz', 'Sentinel', 4),

-- Paper Rex
(21, 'Aaron Leonhart', 'mindfreak', 'Initiator', 5),
(22, 'Jason Susanto', 'f0rsakeN', 'Duelist', 5),
(23, 'Khalish Rusyaidee', 'd4v41', 'Flex', 5),
(24, 'Wang Jing Jie', 'Jinggg', 'Controller', 5),
(25, 'Benedict Tan', 'Benkai', 'Sentinel', 5),

-- Team Liquid
(26, 'Adil Benrlitom', 'ScreaM', 'Duelist', 6),
(27, 'Elias Olkkonen', 'Jamppi', 'Flex', 6),
(28, 'Travis Mendoza', 'L1NK', 'Initiator', 6),
(29, 'Domagoj Fancev', 'Doma', 'Controller', 6),
(30, 'Enzo Mestari', 'Enzo', 'Sentinel', 6);
-- ... (would continue similarly for remaining teams)

INSERT INTO Maps (MapID, MapName) VALUES
(1, 'Ascent'),
(2, 'Bind'),
(3, 'Haven'),
(4, 'Split'),
(5, 'Icebox'),
(6, 'Breeze'),
(7, 'Fracture'),
(8, 'Pearl'),
(9, 'Lotus'),
(10, 'Sunset');

INSERT INTO Matches (MatchID, Date, Time, TeamA_ID, TeamB_ID, Winner_ID, MapID) VALUES
-- Group Stage
(1, '2023-03-01', '15:00:00', 1, 2, 1, 1),
(2, '2023-03-01', '18:00:00', 3, 4, 3, 2),
(3, '2023-03-02', '15:00:00', 5, 6, 6, 3),
(4, '2023-03-02', '18:00:00', 7, 8, 8, 4),
(5, '2023-03-03', '15:00:00', 9, 10, 9, 5),
(6, '2023-03-03', '18:00:00', 11, 12, 11, 6),
(7, '2023-03-04', '15:00:00', 13, 14, 14, 7),
(8, '2023-03-04', '18:00:00', 15, 1, 1, 8),
(9, '2023-03-05', '15:00:00', 2, 3, 3, 9),
(10, '2023-03-05', '18:00:00', 4, 5, 4, 10),

-- Quarterfinals
(11, '2023-03-08', '15:00:00', 1, 3, 1, 1),
(12, '2023-03-08', '18:00:00', 6, 8, 6, 2),
(13, '2023-03-09', '15:00:00', 9, 11, 9, 3),
(14, '2023-03-09', '18:00:00', 14, 1, 1, 4),

-- Semifinals
(15, '2023-03-11', '15:00:00', 1, 6, 1, 5),
(16, '2023-03-11', '18:00:00', 9, 1, 1, 6),

-- Finals
(17, '2023-03-13', '20:00:00', 1, 1, 1, 7),

-- Additional matches to reach 30
(18, '2023-03-14', '15:00:00', 2, 5, 2, 8),
(19, '2023-03-14', '18:00:00', 3, 7, 3, 9),
-- ... (would continue with similar matchups)
(30, '2023-03-20', '18:00:00', 10, 12, 12, 10);

INSERT INTO Match_Stats (StatID, MatchID, PlayerID, Kills, Deaths, Assists, MVP) VALUES
-- Match 1 (Sentinels vs Fnatic)
(1, 1, 1, 25, 12, 8, 1),  -- TenZ MVP
(2, 1, 2, 18, 14, 12, 0),
(3, 1, 3, 15, 16, 20, 0),
(4, 1, 4, 20, 15, 10, 0),
(5, 1, 5, 12, 18, 15, 0),
(6, 1, 6, 22, 20, 5, 0),
(7, 1, 7, 19, 18, 3, 0),
(8, 1, 8, 14, 19, 10, 0),
(9, 1, 9, 13, 17, 8, 0),
(10, 1, 10, 10, 16, 12, 0),

-- Match 2 (DRX vs LOUD)
(11, 2, 11, 18, 14, 10, 0),
(12, 2, 12, 24, 12, 6, 1),  -- Rb MVP
(13, 2, 13, 16, 15, 9, 0),
(14, 2, 14, 14, 16, 15, 0),
(15, 2, 15, 12, 17, 12, 0),
(16, 2, 16, 20, 16, 5, 0),
(17, 2, 17, 18, 15, 8, 0),
(18, 2, 18, 15, 18, 7, 0),
(19, 2, 19, 13, 17, 10, 0),
(20, 2, 20, 10, 16, 12, 0);

-- ... (would continue similarly for all 30 matches with 10 players each = 300 rows)
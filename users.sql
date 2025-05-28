USE ValorantTournamentDB;

DROP TABLE IF EXISTS  admin_users;

CREATE TABLE IF NOT EXISTS admin_users (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL
);

-- Insert default admin (username: admin, password: admin)
INSERT INTO admin_users (username, password)
VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
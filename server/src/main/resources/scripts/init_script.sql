CREATE TABLE Users
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO users (username)
VALUES ('testUser1');
INSERT INTO users (username)
VALUES ('testUser2');

CREATE TABLE UserSessions
(
    userId    INTEGER NOT NULL,
    sessionId VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (userId) REFERENCES users (id)
);

CREATE TABLE Rounds
(
    id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    ownerId INTEGER NOT NULL,
    status  VARCHAR(50) NOT NULL CHECK (status IN ('NOT_STARTED', 'ONGOING', 'FINISHED')),
    FOREIGN KEY (ownerId) REFERENCES users (id)
);
use booking_system_db;

CREATE TABLE app_user (
    id BINARY(16) NOT NULL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

INSERT INTO app_user(id, email, name, password)
VALUES(UUID_TO_BIN(UUID()), 'email@1', 'user1', 'password1'),
      (UUID_TO_BIN(UUID()), 'email@2', 'user2', 'password2'),
      (UUID_TO_BIN(UUID()), 'email@2', 'user3', 'password2');
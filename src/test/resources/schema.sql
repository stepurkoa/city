CREATE TABLE city (
      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255),
      photo VARCHAR(255),
      version INT
);

INSERT INTO city (id, name, photo, version) VALUES (1, 'London', 'photoLondon', 0);
INSERT INTO city (id, name, photo, version) VALUES (2, 'Tallin', 'photoTallin', 0);
INSERT INTO city (id, name, photo, version) VALUES (3, 'Nitra', 'photoNitra', 0);
INSERT INTO city (id, name, photo, version) VALUES (4, 'Odesa', 'photoOdesa', 0);


CREATE TABLE usr (
      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(255),
      password VARCHAR(255),
      role VARCHAR(255)
);

INSERT INTO usr (username, password, role) VALUES ('admin', '$2a$12$Jjy1KiMt9ythFkdV8WMIrOkSN8VmD9TISYxpAWuJia.cdPJLFjwqC', 'ALLOW_EDIT');
INSERT INTO usr (username, password, role) VALUES ('user', '$2a$12$lP.CPzvkHmaZ9BBJElbTCetmFKtszLVyTtfKeGIgcjnxFjq3Vsgpi', 'ALLOW_READ');
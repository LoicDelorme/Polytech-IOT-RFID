USE TP_RFID;

/*
  DROP TABLE IF EXISTS logs;
  DROP TABLE IF EXISTS doors;
  DROP TABLE IF EXISTS users;
*/

CREATE TABLE users (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  lastname VARCHAR(100) NOT NULL,
  firstname VARCHAR(100) NOT NULL,
  rfid_tag VARCHAR(100),
  is_valid BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE doors (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  label VARCHAR(100) NOT NULL
);

CREATE TABLE logs (
  user_id INT,
  door_id INT,
  date_time DATETIME,
  PRIMARY KEY (user_id, door_id, date_time),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (door_id) REFERENCES doors (id)
);
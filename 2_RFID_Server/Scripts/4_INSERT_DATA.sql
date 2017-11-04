USE TP_RFID;

INSERT INTO users (lastname, firstname, rfid_tag, is_valid) VALUES ('DELORME', 'Loïc', '1234-5678-9', true), ('KASPRZYK', 'Nicolas', NULL, false);

INSERT INTO doors (label) VALUES ('Polytech');
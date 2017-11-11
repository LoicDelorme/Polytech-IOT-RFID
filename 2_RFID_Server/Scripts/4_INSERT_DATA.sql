USE TP_RFID;

INSERT INTO users (lastname, firstname, rfid_tag, is_valid) VALUES ('DELORME', 'Loïc', '150210156172', true), ('KASPRZYK', 'Nicolas', NULL, false);

INSERT INTO doors (label) VALUES ('Polytech - Door 1'), ('Polytech - Door 2');
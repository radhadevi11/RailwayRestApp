CREATE TABLE station (
  station_id int(11) NOT NULL AUTO_INCREMENT,
  station_name varchar(20) NOT NULL,
  station_code varchar(10) NOT NULL,
  PRIMARY KEY (station_id)
);

CREATE TABLE train (
  train_id int(11) NOT NULL AUTO_INCREMENT,
  train_number varchar(20) NOT NULL,
  train_name varchar(20) NOT NULL,
  source_station_id int(11) NOT NULL,
  destination_station_id int(11) NOT NULL,
  PRIMARY KEY (train_id),
  KEY source_station_id (source_station_id),
  KEY destination_station_id (destination_station_id),
  CONSTRAINT train_station_source_station_id_fk
  FOREIGN KEY (source_station_id) REFERENCES station (station_id),
  CONSTRAINT train_station_source_destination_station_id_fk
  FOREIGN KEY (destination_station_id) REFERENCES station (station_id)
);

CREATE TABLE train_stop (
  train_stop_id int(11) NOT NULL AUTO_INCREMENT,
  train_id int(11) NOT NULL,
  station_id int(11) NOT NULL,
  sequence int(11) DEFAULT NULL,
  arrival_time time DEFAULT NULL,
  departure_time time DEFAULT NULL,
  PRIMARY KEY (train_stop_id),
  KEY train_id (train_id),
  KEY station_id (station_id),
  CONSTRAINT train_stop_train_train_id_fk
  FOREIGN KEY (train_id) REFERENCES train (train_id),
  CONSTRAINT train_stop_train_station_id_fk FOREIGN KEY (station_id) REFERENCES station (station_id)
);

INSERT INTO station
(station_name,
station_code)
VALUES
('CHENNAI', 'MAS'),
('ERODE', 'ED');
('COIMBATORE', 'CBE');

INSERT INTO train
(train_number,
train_name,
source_station_id,
destination_station_id)
VALUES
('1234', 'train1' 1, 2),
('1235', 'train1' 1, 3);

INSERT INTO train_stop
(train_id,
station_id,
sequence,
arrival_time,
departure_time)
VALUES
(1, 1, 1, '09:35:00', '09:35:00'),
(1, 2, 2. '09:35:00', '09:35:00'),
(2, 1, 1, '09:35:00', '09:35:00');
(2, 3, 2, '09:35:00', '09:35:00');






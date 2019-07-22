USE airticketofficedb;

CREATE TABLE city (
    id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    city_name VARCHAR(100) NOT NULL
);

CREATE TABLE aircraft (
    id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    aircraft_name VARCHAR(30) NOT NULL, 
    business_seat_number INTEGER NOT NULL,
    economy_seat_number INTEGER NOT NULL
);

CREATE TABLE passenger (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,  
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATETIME NOT NULL
);

CREATE TABLE route (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  departure_city_id INTEGER NOT NULL,
  departure_date DATETIME NOT NULL,
  arrival_city_id INTEGER NOT NULL,
  arrival_date DATETIME NOT NULL,
  CONSTRAINT `FK_route_arrival_city` FOREIGN KEY (`arrival_city_id`) REFERENCES `city` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_route_departure_city` FOREIGN KEY (`departure_city_id`) REFERENCES `city` (`id`) ON DELETE CASCADE
);

CREATE TABLE flight (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    aircraft_id INTEGER NOT NULL,
    route_id INTEGER NOT NULL,
    business_seat_occupied_number INTEGER NOT NULL,
    economy_seat_occupied_number INTEGER NOT NULL,
    CONSTRAINT `FK_flight_aircraft` FOREIGN KEY (`aircraft_id`) REFERENCES `aircraft` (`id`) ON DELETE CASCADE,
	CONSTRAINT `FK_flight_route` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE CASCADE
    );

CREATE TABLE ticket (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    flight_id INTEGER NOT NULL,
    passenger_id INTEGER NOT NULL,
    seat_type VARCHAR(8) NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT `FK_ticket_flight` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`) ON DELETE CASCADE,
	CONSTRAINT `FK_ticket_passenger` FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`id`) ON DELETE CASCADE
);

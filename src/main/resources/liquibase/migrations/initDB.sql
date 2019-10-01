--liquibase formatted sql
--changeset tigratius:1

USE ticketoffice;

CREATE TABLE cities (
    id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(100) NOT NULL
);

CREATE TABLE aircrafts (
    id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(30) NOT NULL
);

CREATE TABLE aircraftseatamounts (
    id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    aircraft_id INTEGER DEFAULT NULL,
    seat_type VARCHAR(8) DEFAULT NULL,
    amount INTEGER DEFAULT NULL,
    CONSTRAINT `FK_aircraftseatamounts_aircrafts` FOREIGN KEY (`aircraft_id`) REFERENCES `aircrafts` (`id`) ON DELETE CASCADE
);

CREATE TABLE passengers (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,  
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATETIME NOT NULL
);

CREATE TABLE routes (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  departure_city_id INTEGER NOT NULL,
  departure_date DATETIME NOT NULL,
  arrival_city_id INTEGER NOT NULL,
  arrival_date DATETIME NOT NULL,
  CONSTRAINT `FK_routes_arrival_cities` FOREIGN KEY (`arrival_city_id`) REFERENCES `cities` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_routes_departure_cities` FOREIGN KEY (`departure_city_id`) REFERENCES `cities` (`id`) ON DELETE CASCADE
);

CREATE TABLE flights (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    aircraft_id INTEGER NOT NULL,
    route_id INTEGER NOT NULL,
    CONSTRAINT `FK_flights_aircrafts` FOREIGN KEY (`aircraft_id`) REFERENCES `aircrafts` (`id`) ON DELETE CASCADE,
	CONSTRAINT `FK_flights_routes` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`) ON DELETE CASCADE
    );

CREATE TABLE tickets (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    flight_id INTEGER NOT NULL,
    passenger_id INTEGER NOT NULL,
    seat_type VARCHAR(8) NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT `FK_tickets_flights` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`) ON DELETE CASCADE,
	CONSTRAINT `FK_tickets_passengers` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`id`) ON DELETE CASCADE
);


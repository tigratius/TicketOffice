--liquibase formatted sql
--changeset tigratius:2

/*Populate cities*/
INSERT IGNORE INTO cities (name) values ('Novosibirsk');
INSERT IGNORE INTO cities (name) values ('Moscow');
INSERT IGNORE INTO cities (name) values ('Prague');
INSERT IGNORE INTO cities (name) values ('NewYork');
INSERT IGNORE INTO cities (name) values ('Kiev');
INSERT IGNORE INTO cities (name) values ('London');

/*Populate aircrafts*/
INSERT IGNORE INTO aircrafts (name, business_seat_amount, economy_seat_amount) values ('Boeing737', 20, 100);
INSERT IGNORE INTO aircrafts (name, business_seat_amount, economy_seat_amount) values ('Boeing730', 10, 90);

/*Populate passengers*/
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Alex', 'Andreev', '1986-04-11');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Vlad', 'Andreev', '1983-01-01');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Alex', 'Fefelov', '1985-10-15');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor', 'Sumkin', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor1', 'Sumkin1', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor2', 'Sumkin2', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor3', 'Sumkin3', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor4', 'Sumkin4', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Fedor5', 'Sumkin5', '1989-07-25');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Vlad1', 'Andreev1', '1983-01-01');
INSERT IGNORE INTO passengers (first_name, last_name, birth_date) values ('Vlad2', 'Andreev2', '1983-01-01');

/*Populate routes*/
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-07-27 08:00:00', 2, '2019-07-27 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (2, '2019-07-28 10:00:00', 3, '2019-07-28 14:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (2, '2019-07-29 08:00:00', 4, '2019-07-29 16:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-07-30 08:00:00', 2, '2019-07-30 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-07-31 08:00:00', 2, '2019-07-31 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-01 08:00:00', 2, '2019-08-01 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-02 08:00:00', 2, '2019-08-02 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-03 08:00:00', 2, '2019-08-03 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-04 08:00:00', 2, '2019-08-04 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-05 08:00:00', 2, '2019-08-05 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-06 08:00:00', 2, '2019-08-06 12:00:00');
INSERT IGNORE INTO routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (1, '2019-08-07 08:00:00', 2, '2019-08-07 12:00:00');

/*Populate flights*/
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 1, 1, 2);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 2, 0, 2);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 3, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 4, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 5, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 6, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 7, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 8, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 9, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 10, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 11, 0, 1);
INSERT IGNORE INTO flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (1, 12, 0, 1);

/*Populate tickets*/
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (1, 1, 'BUSINESS', 20000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (1, 2, 'ECONOMY', 10000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (1, 3, 'ECONOMY', 9000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (2, 3, 'ECONOMY', 17000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (2, 4, 'ECONOMY', 15000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (3, 3, 'ECONOMY', 25000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (4, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (5, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (6, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (7, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (8, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (9, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (10, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (11, 3, 'ECONOMY', 5000);
INSERT IGNORE INTO tickets (flight_id, passenger_id, seat_type, price) values (12, 3, 'ECONOMY', 5000);
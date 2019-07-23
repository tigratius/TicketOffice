/*5. Поиск пассажиров, купивших билеты в указанный пункт назначения, на сумму, 
больше указанной в запросе, в указанный период времени
*/

USE ticketoffice;

SET @price = 5000;
SET @arrival_city_name = 'mos';
SET @date_from = '2019-07-27';
SET @date_to = '2019-08-05';

select p.*, t.price, c.name, r.arrival_date from passengers p
join tickets t on t.passenger_id = p.id
join flights f on t.flight_id = f.id
join routes r on r.id = f.route_id
join cities c on c.id = r.arrival_city_id
where c.name like CONCAT('%', @arrival_city_name, '%') and 
		r.arrival_date >= @date_from and r.arrival_date <= @date_to
        and price >= @price

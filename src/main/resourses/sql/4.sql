/*4. Поиск пассажиров, которые выполняют полет в период +-3 дня от своего дня рождения
*/
USE airticketofficedb;

SET @number_days = 3;

select p.*, r.departure_date, dayofyear(departure_date) as dd, dayofyear(p.birth_date) + @number_days  as bd from passenger p
join ticket t on t.passenger_id = p.id
join flight f on t.flight_id = f.id
join route r on r.id = f.route_id
where dayofyear(departure_date) <= (dayofyear(p.birth_date) + @number_days) and dayofyear(departure_date) >= (dayofyear(p.birth_date) - @number_days)

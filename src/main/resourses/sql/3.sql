/*3. Поиск пассажира, который совершал более 10 перелетов в текущем квартале
*/

USE airticketofficedb;

SET @number_days = 10;

select * from
(
select p.*, count(*) as number_flight from passenger p
join ticket t on t.passenger_id = p.id
join flight f on t.flight_id = f.id
join route r on r.id = f.route_id
where quarter(r.departure_date) = quarter(current_date())
group by p.id) as t1
where t1.number_flight > @number_days
limit 1

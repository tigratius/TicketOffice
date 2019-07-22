/*2. Поиск наименее прибыльного маршрута за текущий квартал*/

USE airticketofficedb;

select * from
(
select r.id, cd.city_name as cd_name, r.departure_date, ca.city_name as ca_name, r.arrival_date,  sum(price) as total_price from ticket t
join flight f on t.flight_id = f.id
join route r on f.route_id = r.id
join city cd on r.departure_city_id = cd.id
join city ca on r.arrival_city_id = ca.id
group by flight_id) as t1
where quarter(t1.departure_date) = quarter(current_date())
order by total_price
Limit 1





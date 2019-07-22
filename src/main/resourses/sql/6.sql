/*6. Поиск дат, в которые более, чем у 5 пассажиров день рождения
	(день года, когда более, чем у пяти людей др)
*/

USE airticketofficedb;

SET @number_pass = 5;

select dayofyear(t1.birth_date) as day_of_year, t1.birth_date from
(select p.*, count(*) as number_pas from passenger p
group by birth_date) as t1
where t1.number_pas > @number_pass





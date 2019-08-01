package com.tigratius.ticketoffice.repository.dao;

import com.tigratius.ticketoffice.model.*;
import com.tigratius.ticketoffice.repository.CityRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

public class JavaDAORouteRepositoryImpl extends RouteRepository {

    private CityRepository cityRepository;
    private final String sqlQueryGetAll = "select * from routes";
    private final String sqlQueryGetById = "select * from routes where id=?";
    private final String sqlQueryDeleteById = "delete from routes where id=?";
    private final String sqlQueryUpdateById = "update routes set departure_city_id=?, departure_date=?, arrival_city_id=?, arrival_date=? where id=?";
    private final String sqlQueryInsert = "insert into routes (departure_city_id, departure_date, arrival_city_id, arrival_date) values (?, ?, ?, ?)";

    private final Class<Route> aClass = Route.class;

    public JavaDAORouteRepositoryImpl(Connection connection, CityRepository cityRepository) {

        this.cityRepository = cityRepository;
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public Route getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<Route> getAll() {
        return super.getAll(aClass);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    protected void parse(Route route, ResultSet rs) throws Exception {
        route.setId(rs.getLong("id"));
        Long cityDepartureId = rs.getLong("departure_city_id");
        Date departureDate = rs.getDate("departure_date");
        Long cityArrivalId = rs.getLong("arrival_city_id");
        Date arrivalDate = rs.getDate("arrival_date");

        route.setDeparture(cityRepository.getById(cityDepartureId));
        route.setDepartureDate(departureDate);
        route.setArrival(cityRepository.getById(cityArrivalId));
        route.setArrivalDate(arrivalDate);
    }

    @Override
    protected void prepareAddStatement(Route item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setLong(1, item.getDeparture().getId());
        preparedStatement.setDate(2, item.getDepartureDate());
        preparedStatement.setLong(3, item.getArrival().getId());
        preparedStatement.setDate(4, item.getDepartureDate());
    }

    @Override
    protected void prepareUpdateStatement(Route item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(5, item.getId());
    }
}

package com.tigratius.ticketoffice.repository.jdbc;

import com.tigratius.ticketoffice.model.*;
import com.tigratius.ticketoffice.repository.AircraftRepository;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.util.*;
import java.sql.*;

public class JavaJDBCFlightRepositoryImpl extends JDBCBaseRepository<Flight> implements FlightRepository {

    private AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;

    private final String sqlQueryGetAll = "select * from flights";
    private final String sqlQueryGetById = "select * from flights where id=?";
    private final String sqlQueryDeleteById = "delete from flights where id=?";
    private final String sqlQueryUpdateById = "update flights set aircraft_id=?, route_id=? where id=?";
    private final String sqlQueryInsert = "insert into flights (aircraft_id, route_id) values (?, ?)";

    private final Class<Flight> aClass = Flight.class;

    public JavaJDBCFlightRepositoryImpl(Connection connection, AircraftRepository aircraftRepository, RouteRepository routeRepository) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public Flight getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<Flight> getAll() {
        return super.getAll(aClass);
    }

    @Override
    protected void parse(Flight flight, ResultSet rs) throws Exception {
        flight.setId(rs.getLong("id"));
        flight.setAircraft(aircraftRepository.getById(rs.getLong("aircraft_id")));
        flight.setRoute(routeRepository.getById(rs.getLong("route_id")));
    }

    @Override
    protected void prepareAddStatement(Flight item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setLong(1, item.getAircraft().getId());
        preparedStatement.setLong(2, item.getRoute().getId());
    }

    @Override
    protected void prepareUpdateStatement(Flight item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(3, item.getId());
    }
}

package com.tigratius.ticketoffice.repository.dao;

import com.tigratius.ticketoffice.model.*;
import com.tigratius.ticketoffice.repository.AircraftRepository;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.util.*;
import java.sql.*;

public class JavaDAOFlightRepositoryImpl extends FlightRepository {

    private AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;

    private final String sqlQueryGetAll = "select * from flights";
    private final String sqlQueryGetById = "select * from flights where id=?";
    private final String sqlQueryDeleteById = "delete from flights where id=?";
    private final String sqlQueryUpdateById = "update flights set aircraft_id=?, route_id=?, business_seat_occupied_amount=?, economy_seat_occupied_amount=? where id=?";
    private final String sqlQueryInsert = "insert into flights (aircraft_id, route_id, business_seat_occupied_amount, economy_seat_occupied_amount) values (?, ?, ?, ?)";

    private final Class<Flight> aClass = Flight.class;

    public JavaDAOFlightRepositoryImpl(Connection connection, AircraftRepository aircraftRepository, RouteRepository routeRepository) {
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
        HashMap<SeatType, Integer> seatOccupiedMap = new HashMap<>();
        seatOccupiedMap.put(SeatType.BUSINESS, rs.getInt("business_seat_occupied_amount"));
        seatOccupiedMap.put(SeatType.ECONOMY, rs.getInt("economy_seat_occupied_amount"));
        flight.setSeatOccupiedMap(seatOccupiedMap);
    }

    @Override
    protected void prepareAddStatement(Flight item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setLong(1, item.getAircraft().getId());
        preparedStatement.setLong(2, item.getRoute().getId());
        preparedStatement.setInt(3, item.getOccupiedSeatsBySeatType(SeatType.BUSINESS));
        preparedStatement.setInt(4, item.getOccupiedSeatsBySeatType(SeatType.ECONOMY));
    }

    @Override
    protected void prepareUpdateStatement(Flight item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(5, item.getId());
    }
}

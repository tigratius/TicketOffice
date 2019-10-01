package com.tigratius.ticketoffice.repository.jdbc;

import com.tigratius.ticketoffice.model.*;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.PassengerRepository;
import com.tigratius.ticketoffice.repository.TicketRepository;

import java.sql.*;
import java.util.*;

public class JavaJDBCTicketRepositoryImpl extends JDBCBaseRepository<Ticket> implements TicketRepository {

    private FlightRepository flightRepository;
    private PassengerRepository passengerRepository;

    private final String sqlQueryGetAll = "select * from tickets";
    private final String sqlQueryGetById = "select * from tickets where id=?";
    private final String sqlQueryDeleteById = "delete from tickets where id=?";
    private final String sqlQueryUpdateById = "update tickets set flight_id=?, passenger_id=?, seat_type=?, price=? where id=?";
    private final String sqlQueryInsert = "insert into tickets (flight_id, passenger_id, seat_type, price) values (?, ?, ?, ?)";

    private final Class<Ticket> aClass = Ticket.class;

    public JavaJDBCTicketRepositoryImpl(Connection connection, FlightRepository flightRepository, PassengerRepository passengerRepository) {

        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public Ticket getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<Ticket> getAll() {
        return super.getAll(aClass);
    }

    @Override
    protected void parse(Ticket ticket, ResultSet rs) throws Exception {
        ticket.setId(rs.getLong("id"));
        ticket.setFlight(flightRepository.getById(rs.getLong("flight_id")));
        ticket.setPassenger(passengerRepository.getById(rs.getLong("passenger_id")));
        ticket.setSeatType(SeatType.valueOf(rs.getString("seat_type")));
        ticket.setPrice(rs.getDouble("price"));
    }

    @Override
    protected void prepareAddStatement(Ticket item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setLong(1, item.getFlight().getId());
        preparedStatement.setLong(2, item.getPassenger().getId());
        preparedStatement.setString(3, item.getSeatType().name());
        preparedStatement.setDouble(4, item.getPrice());
    }

    @Override
    protected void prepareUpdateStatement(Ticket item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(5, item.getId());
    }
}

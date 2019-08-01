package com.tigratius.ticketoffice.repository.dao;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.model.SeatType;
import com.tigratius.ticketoffice.repository.AircraftRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class JavaDAOAirCraftRepositoryImpl extends AircraftRepository {

    private final String sqlQueryGetAll = "select * from aircrafts";
    private final String sqlQueryGetById = "select * from aircrafts where id=?";
    private final String sqlQueryDeleteById = "delete from aircrafts where id=?";
    private final String sqlQueryUpdateById = "update aircrafts set name=?, business_seat_amount=?, economy_seat_amount=? where id=?";
    private final String sqlQueryInsert = "insert into aircrafts (name, business_seat_amount, economy_seat_amount) values (?, ?, ?)";

    private final Class<Aircraft> aClass = Aircraft.class;

    public JavaDAOAirCraftRepositoryImpl(Connection connection) {
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public Aircraft getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<Aircraft> getAll() {
        return super.getAll(aClass);
    }

    @Override
    protected void parse(Aircraft aircraft, ResultSet rs) throws SQLException {
        aircraft.setId(rs.getLong("id"));
        aircraft.setName(rs.getString("name"));
        HashMap<SeatType, Integer> seatNumberMap = new HashMap<>();
        seatNumberMap.put(SeatType.BUSINESS, rs.getInt("business_seat_amount"));
        seatNumberMap.put(SeatType.ECONOMY, rs.getInt("economy_seat_amount"));
        aircraft.setSeatNumberMap(seatNumberMap);
    }

    @Override
    protected void prepareAddStatement(Aircraft item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setString(1, item.getName());
        preparedStatement.setInt(2, item.getNumberSeatsBySeatType(SeatType.BUSINESS));
        preparedStatement.setInt(3, item.getNumberSeatsBySeatType(SeatType.ECONOMY));
    }

    @Override
    protected void prepareUpdateStatement(Aircraft item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(4, item.getId());
    }
}

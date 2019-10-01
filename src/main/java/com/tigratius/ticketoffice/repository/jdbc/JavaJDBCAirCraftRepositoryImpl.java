package com.tigratius.ticketoffice.repository.jdbc;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.model.AircraftSeatAmount;
import com.tigratius.ticketoffice.model.Message;
import com.tigratius.ticketoffice.model.SeatType;
import com.tigratius.ticketoffice.repository.AircraftRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaJDBCAirCraftRepositoryImpl implements AircraftRepository {

    private Connection connection;

    private final String sqlQueryGetAll = "select a.id, name, s.id, seat_type, amount from aircrafts a join aircraftseatamounts s on s.aircraft_id = a.id";
    private final String sqlQueryGetById = "select a.id, name, s.id, seat_type, amount from aircrafts a join aircraftseatamounts s on s.aircraft_id = a.id where a.id=?";
    private final String sqlQueryDeleteById = "delete from aircrafts where id=?";
    private final String sqlQueryDeleteById1 = "delete from aircraftseatamounts where id=?";
    private final String sqlQueryUpdateById = "update aircrafts set name=? where id=?";
    private final String sqlQueryUpdateById1 = "update aircraftseatamounts set aircraft_id=?, seat_type=?, amount=? where id=?";
    private final String sqlQueryInsert = "insert into aircrafts (name) values (?)";
    private final String sqlQueryInsert1 = "insert into aircraftseatamounts (aircraft_id, seat_type, amount) values (?, ?, ?)";

    public JavaJDBCAirCraftRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Aircraft getById(Long id) throws Exception {

        Aircraft aircraft = new Aircraft();

        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement(sqlQueryGetById);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            Map<SeatType, AircraftSeatAmount> seatNumberMap = new HashMap<>();

            if (rs.next()) {
                aircraft.setId(id);
                aircraft.setName(rs.getString("name"));
                AircraftSeatAmount aircraftSeatAmount = new AircraftSeatAmount(rs.getInt("amount"));
                aircraftSeatAmount.setId(rs.getLong("s.id"));
                SeatType seatType = SeatType.valueOf(rs.getString("seat_type"));
                seatNumberMap.put(seatType, aircraftSeatAmount);
                if (rs.next()) {
                    aircraftSeatAmount = new AircraftSeatAmount(rs.getInt("amount"));
                    aircraftSeatAmount.setId(rs.getLong("s.id"));
                    seatNumberMap.put(SeatType.valueOf(rs.getString("seat_type")), aircraftSeatAmount);
                } else {
                    throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
                }
                aircraft.setSeatNumberMap(seatNumberMap);
            } else {
                throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aircraft;
    }

    @Override
    public List<Aircraft> getAll() {
        List<Aircraft> aircrafts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlQueryGetAll);
            long idPr = 0;
            int count = 0;
            Aircraft aircraft = null;
            Map<SeatType, AircraftSeatAmount> seatNumberMap = null;
            while (rs.next()) {

                long id = rs.getLong("a.id");
                if (id != idPr) {
                    aircraft = new Aircraft();
                    seatNumberMap = new HashMap<>();
                    aircraft.setId(id);
                    aircraft.setName(rs.getString("name"));
                    AircraftSeatAmount aircraftSeatAmount = new AircraftSeatAmount(rs.getInt("amount"));
                    aircraftSeatAmount.setId(rs.getLong("s.id"));
                    seatNumberMap.put(SeatType.valueOf(rs.getString("seat_type")), aircraftSeatAmount);
                    idPr = id;
                    count++;
                } else {
                    AircraftSeatAmount aircraftSeatAmount = new AircraftSeatAmount(rs.getInt("amount"));
                    aircraftSeatAmount.setId(rs.getLong("s.id"));
                    seatNumberMap.put(SeatType.valueOf(rs.getString("seat_type")), new AircraftSeatAmount(rs.getInt("amount")));
                    count++;
                }

                if (count == 2) {
                    aircraft.setSeatNumberMap(seatNumberMap);
                    aircrafts.add(aircraft);
                    count = 0;
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aircrafts;
    }


    @Override
    public void delete(Long id) {
        try {
            Aircraft aircraft = getById(id);

            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlQueryDeleteById1);
            for (Map.Entry<SeatType, AircraftSeatAmount> entry : aircraft.getSeatNumberMap().entrySet()) {
                preparedStatement.setLong(1, entry.getValue().getId());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();


            preparedStatement = connection
                    .prepareStatement(sqlQueryDeleteById);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Aircraft item) {
        try {
            Aircraft aircraftOld = getById(item.getId());

            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlQueryUpdateById);
            prepareUpdateStatement(item, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(sqlQueryUpdateById1);
            for (Map.Entry<SeatType, AircraftSeatAmount> entry : item.getSeatNumberMap().entrySet()) {
                preparedStatement.setLong(1, item.getId());
                SeatType seatType = entry.getKey();
                Long id = aircraftOld.getSeatNumberMap().get(seatType).getId();
                preparedStatement.setString(2, String.valueOf(seatType));
                preparedStatement.setInt(3, entry.getValue().getAmount());
                preparedStatement.setLong(4, id);
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Aircraft item) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlQueryInsert, Statement.RETURN_GENERATED_KEYS);
            prepareAddStatement(item, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            Long newId = 0L;
            if (rs.next()) {
                newId = rs.getLong(1);
            } else {
                throw new Exception(Message.NOT_FIND_ID.getMessage() + newId);
            }
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(sqlQueryInsert1);
            for (Map.Entry<SeatType, AircraftSeatAmount> entry : item.getSeatNumberMap().entrySet()) {
                preparedStatement.setLong(1, newId);
                preparedStatement.setString(2, String.valueOf(entry.getKey()));
                preparedStatement.setInt(3, entry.getValue().getAmount());
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareAddStatement(Aircraft item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setString(1, item.getName());
    }


    private void prepareUpdateStatement(Aircraft item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(2, item.getId());
    }
}

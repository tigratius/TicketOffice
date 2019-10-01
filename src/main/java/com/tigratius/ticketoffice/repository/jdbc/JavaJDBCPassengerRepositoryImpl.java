package com.tigratius.ticketoffice.repository.jdbc;

import com.tigratius.ticketoffice.model.Passenger;
import com.tigratius.ticketoffice.repository.PassengerRepository;

import java.sql.*;
import java.util.List;

public class JavaJDBCPassengerRepositoryImpl extends JDBCBaseRepository<Passenger> implements PassengerRepository {

    private final String sqlQueryGetAll = "select * from passengers";
    private final String sqlQueryGetById = "select * from passengers where id=?";
    private final String sqlQueryDeleteById = "delete from passengers where id=?";
    private final String sqlQueryUpdateById = "update passengers set first_name=?, last_name=?, birth_date=? where id=?";
    private final String sqlQueryInsert = "insert into passengers (first_name, last_name, birth_date) values (?, ?, ?)";

    private final Class<Passenger> aClass = Passenger.class;

    public JavaJDBCPassengerRepositoryImpl(Connection connection) {
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public Passenger getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<Passenger> getAll() {
        return super.getAll(aClass);
    }

    @Override
    protected void parse(Passenger passenger, ResultSet rs) throws Exception {
        passenger.setId(rs.getLong("id"));
        passenger.setFirstName(rs.getString("first_name"));
        passenger.setLastName(rs.getString("last_name"));
        passenger.setBirthDate(rs.getDate("birth_date"));
    }

    @Override
    protected void prepareAddStatement(Passenger item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setString(1, item.getFirstName());
        preparedStatement.setString(2, item.getLastName());
        preparedStatement.setDate(3, item.getBirthDate());
    }

    @Override
    protected void prepareUpdateStatement(Passenger item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(4, item.getId());
    }
}

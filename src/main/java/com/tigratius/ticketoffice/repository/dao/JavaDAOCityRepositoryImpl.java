package com.tigratius.ticketoffice.repository.dao;

import com.tigratius.ticketoffice.model.City;
import com.tigratius.ticketoffice.repository.CityRepository;

import java.sql.*;
import java.util.List;

public class JavaDAOCityRepositoryImpl extends CityRepository {

    private final String sqlQueryGetAll = "select * from cities";
    private final String sqlQueryGetById = "select * from cities where id=?";
    private final String sqlQueryDeleteById = "delete from cities where id=?";
    private final String sqlQueryUpdateById = "update cities set name=? where id=?";
    private final String sqlQueryInsert = "insert into cities (name) values (?)";

    private final Class<City> aClass = City.class;

    public JavaDAOCityRepositoryImpl(Connection connection) {
        super.connection = connection;
        super.sqlQueryDeleteById = sqlQueryDeleteById;
        super.sqlQueryGetById = sqlQueryGetById;
        super.sqlQueryGetAll = sqlQueryGetAll;
        super.sqlQueryInsert = sqlQueryInsert;
        super.sqlQueryUpdateById = sqlQueryUpdateById;
    }

    @Override
    public City getById(Long id) throws Exception {
        return super.getById(id, aClass);
    }

    @Override
    public List<City> getAll() {
        return super.getAll(aClass);
    }

    @Override
    protected void parse(City city, ResultSet rs) throws SQLException {
        city.setId(rs.getLong("id"));
        city.setName(rs.getString("name"));
    }

    @Override
    protected void prepareAddStatement(City item, PreparedStatement preparedStatement) throws Exception {
        preparedStatement.setString(1, item.getName());
    }

    @Override
    protected void prepareUpdateStatement(City item, PreparedStatement preparedStatement) throws Exception {
        prepareAddStatement(item, preparedStatement);
        preparedStatement.setLong(2, item.getId());
    }
}

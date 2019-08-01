package com.tigratius.ticketoffice.repository;

import com.tigratius.ticketoffice.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T> implements GenericRepository<T, Long>{

    protected String sqlQueryDeleteById;
    protected String sqlQueryGetById;
    protected String sqlQueryGetAll;
    protected String sqlQueryInsert;
    protected String sqlQueryUpdateById;
    protected Connection connection;

    @Override
    public void update(T item) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryUpdateById);
            prepareUpdateStatement(item, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(T item) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlQueryInsert);
            prepareAddStatement(item, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected List<T> getAll(Class<T> cls) {

        List<T> items = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlQueryGetAll);
            while (rs.next()) {
                T item  = cls.newInstance();
                parse(item, rs);
                items.add(item);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public void delete(Long id) {
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlQueryDeleteById);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected T getById(Long id, Class<T> cls) throws Exception {

        T item = cls.newInstance();

        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement(sqlQueryGetById);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                parse(item, rs);
            }
            else
            {
                throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    protected abstract void parse(T item, ResultSet rs) throws Exception;
    protected abstract void prepareAddStatement(T item, PreparedStatement preparedStatement) throws Exception;
    protected abstract void prepareUpdateStatement(T item, PreparedStatement preparedStatement) throws Exception;
}

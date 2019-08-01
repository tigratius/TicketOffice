package com.tigratius.ticketoffice.repository;

import java.util.List;

public interface GenericRepository<T, ID>{

    void delete(ID id);

    void update(T item);

    void add(T item);

    T getById(ID id) throws Exception;

    List<T> getAll();
}

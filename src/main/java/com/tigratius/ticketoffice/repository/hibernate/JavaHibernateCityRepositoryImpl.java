package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.City;
import com.tigratius.ticketoffice.repository.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernateCityRepositoryImpl extends HibernateBaseRepository<City> implements CityRepository {

    public JavaHibernateCityRepositoryImpl(SessionFactory sessionFactory) {
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            City city = session.get(City.class, id);
            session.delete(city);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(City item) {
        super.update(item);
    }

    @Override
    public void add(City item) {
        super.add(item);
    }

    @Override
    public City getById(Long id) throws Exception {
        Session session = null;
        City city = null;
        try {
            session = sessionFactory.openSession();
            city = session.get(City.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return city;
    }

    @Override
    public List<City> getAll() {
        Session session = null;
        List<City> cities = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<City> criteria = builder.createQuery(City.class);
            criteria.from(City.class);
            cities = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cities;
    }
}

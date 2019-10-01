package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.model.Flight;
import com.tigratius.ticketoffice.model.Route;
import com.tigratius.ticketoffice.repository.FlightRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernateFlightRepositoryImpl extends HibernateBaseRepository<Flight> implements FlightRepository {

    public JavaHibernateFlightRepositoryImpl(SessionFactory sessionFactory) {

        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Flight flight = session.get(Flight.class, id);
            session.delete(flight);
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
    public void update(Flight item) {
       /* Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
       super.update(item);
    }

    @Override
    public void add(Flight item) {
        /*Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
        super.add(item);
    }

    @Override
    public Flight getById(Long id) throws Exception {
        Session session = null;
        Flight flight = null;
        try {
            session = sessionFactory.openSession();
            flight = session.get(Flight.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return flight;
    }

    @Override
    public List<Flight> getAll() {
        Session session = null;
        List<Flight> flights = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);
            criteria.from(Flight.class);
            flights = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return flights;
    }
}

package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.Passenger;
import com.tigratius.ticketoffice.repository.PassengerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernatePassengerRepositoryImpl extends HibernateBaseRepository<Passenger> implements PassengerRepository {

    public JavaHibernatePassengerRepositoryImpl(SessionFactory sessionFactory) {

        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Passenger passenger = session.get(Passenger.class, id);
            session.delete(passenger);
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
    public void update(Passenger item) {
        /*Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
        super.update(item);
    }

    @Override
    public void add(Passenger item) {
        /*Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
        super.add(item);
    }

    @Override
    public Passenger getById(Long id) throws Exception {
        Session session = null;
        Passenger passenger = null;
        try {
            session = sessionFactory.openSession();
            passenger = session.get(Passenger.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return passenger;
    }

    @Override
    public List<Passenger> getAll() {
        Session session = null;
        List<Passenger> passengers = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Passenger> criteria = builder.createQuery(Passenger.class);
            criteria.from(Passenger.class);
            passengers = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return passengers;
    }
}

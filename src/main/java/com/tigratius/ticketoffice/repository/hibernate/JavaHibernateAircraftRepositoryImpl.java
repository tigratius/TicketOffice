package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.repository.AircraftRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernateAircraftRepositoryImpl extends HibernateBaseRepository<Aircraft> implements AircraftRepository {

    public JavaHibernateAircraftRepositoryImpl(SessionFactory sessionFactory) {
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Aircraft aircraft = session.get(Aircraft.class, id);
            session.delete(aircraft);
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
    public void update(Aircraft item) {
        super.update(item);
    }

    @Override
    public void add(Aircraft item) {
        super.add(item);
    }

    @Override
    public Aircraft getById(Long id) throws Exception {
        Session session = null;
        Aircraft aircraft = null;
        try {
            session = sessionFactory.openSession();
            aircraft = session.get(Aircraft.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return aircraft;
    }

    @Override
    public List<Aircraft> getAll() {
        Session session = null;
        List<Aircraft> aircrafts = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Aircraft> criteria = builder.createQuery(Aircraft.class);
            criteria.from(Aircraft.class);
            aircrafts = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return aircrafts;
    }
}

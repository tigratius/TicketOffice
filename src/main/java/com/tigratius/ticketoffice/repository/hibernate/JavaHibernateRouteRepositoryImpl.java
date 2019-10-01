package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.Route;
import com.tigratius.ticketoffice.repository.RouteRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernateRouteRepositoryImpl extends HibernateBaseRepository<Route> implements RouteRepository {

    public JavaHibernateRouteRepositoryImpl(SessionFactory sessionFactory) {

        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Route route = session.get(Route.class, id);
            session.delete(route);
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
    public void update(Route item) {
        super.update(item);
    }

    @Override
    public void add(Route item) {
       super.add(item);
    }

    @Override
    public Route getById(Long id) throws Exception {
        Session session = null;
        Route route = null;
        try {
            session = sessionFactory.openSession();
            route = session.get(Route.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return route;
    }

    @Override
    public List<Route> getAll() {
        Session session = null;
        List<Route> routes = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Route> criteria = builder.createQuery(Route.class);
            criteria.from(Route.class);
            routes = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return routes;
    }
}

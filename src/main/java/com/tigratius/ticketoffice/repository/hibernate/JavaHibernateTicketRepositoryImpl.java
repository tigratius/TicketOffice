package com.tigratius.ticketoffice.repository.hibernate;

import com.tigratius.ticketoffice.model.Ticket;
import com.tigratius.ticketoffice.repository.TicketRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class JavaHibernateTicketRepositoryImpl extends HibernateBaseRepository<Ticket> implements TicketRepository {

    public JavaHibernateTicketRepositoryImpl(SessionFactory sessionFactory) {
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            session.delete(ticket);
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
    public void update(Ticket item) {
        /*Session session = null;
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
    public void add(Ticket item) {
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
    public Ticket getById(Long id) throws Exception {
        Session session = null;
        Ticket ticket = null;
        try {
            session = sessionFactory.openSession();
            ticket = session.get(Ticket.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        Session session = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Ticket> criteria = builder.createQuery(Ticket.class);
            criteria.from(Ticket.class);
            tickets = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }
}

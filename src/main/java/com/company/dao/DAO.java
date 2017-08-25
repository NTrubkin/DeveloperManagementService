package com.company.dao;

import com.company.entity.Developer;
import com.company.entity.Project;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

//todo абстрактный класс

public abstract class DAO<T> {
    private static final Logger logger = Logger.getLogger(DAO.class);

    protected static final String HIBERNATE_EXC_MSG = "Hibernate exception occurred";

    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        }
        catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        }
        finally {
            session.close();
        }
    }

    public T read(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = (T) session.get(GenericReflector.getParameterType(this.getClass()), id);
            transaction.commit();
            return result;
        }
        catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        }
        finally {
            session.close();
        }
    }

    public void update(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        }
        catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        }
        finally {
            session.close();
        }
    }

    public void delete(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        }
        catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        }
        finally {
            session.close();
        }
    }

    public List<T> readAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<T> projects = session.createCriteria(GenericReflector.getParameterType(this.getClass())).list();
            transaction.commit();
            return projects;
        }
        catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        }
        finally {
            session.close();
        }
    }
}

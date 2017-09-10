package com.company.dao.api;

import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Обобщенный Hibernate DAO.
 * Содержит CRUD методы и сеттер фабрики Hibernate сессий
 *
 * @param <T> Hibernate сущность, для которой разработан DAO
 */
public abstract class DAO<T> {
    protected static final String HIBERNATE_EXC_MSG = "Hibernate exception occurred";
    private static final Logger logger = Logger.getLogger(DAO.class);
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Создает объект в базе данных, используя Hibernate метод session.save(t);
     *
     * @param t transient или detached сущность
     */
    public void create(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Читает из базы данных объект с id
     *
     * @param id id сущности в базе данных
     * @return persistent сущность, если успех
     */
    public T read(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = (T) session.get(GenericReflector.getSuperclassParameterType(this.getClass()), id);
            transaction.commit();
            return result;
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Обновляет объект в базе данных, используя Hibernate метод session.update(t);
     *
     * @param t detached сущность
     */
    public void update(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Удаляет объект из базы данных, используя Hibernate метод session.delete(t);
     *
     * @param t сущность
     */
    public void delete(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Читает из базы данных все объекты установленного типа сущности
     *
     * @return List<T>, если успех
     */
    public List<T> readAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<T> projects = session.createCriteria(GenericReflector.getSuperclassParameterType(this.getClass())).list();
            transaction.commit();
            return projects;
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }
}

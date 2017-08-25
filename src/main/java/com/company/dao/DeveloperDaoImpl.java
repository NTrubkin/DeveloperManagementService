package com.company.dao;

import com.company.entity.Account;
import com.company.entity.Developer;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class DeveloperDaoImpl implements DAO<Developer> {

    private SessionFactory sessionFactory;

    private static final String SQL_SELECT_AVAIL_DEVS = "SELECT * FROM account\n" +
            "WHERE id NOT IN (SELECT DISTINCT d.account_id\n" +
            "                 FROM developer d INNER JOIN project p ON d.project_id = p.id\n" +
            "                 WHERE p.complete = FALSE ) AND role_id = (SELECT id FROM role WHERE code = 'ROLE_DEV')";
    private static final String SQL_DELETE = "DELETE FROM developer WHERE project_id = :id";

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(developer);
        transaction.commit();
        session.close();
    }

    @Override
    public Developer read(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, id);
        transaction.commit();
        session.close();
        return developer;
    }

    @Override
    public void update(Developer developer) {
        throw new UnsupportedOperationException("not supported yet");
    }

    @Override
    public void delete(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(developer);
        transaction.commit();
        session.close();
    }

    public void delete(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, id);
        session.delete(developer);
        transaction.commit();
        session.close();
    }

    public void delete(Serializable projectId, Serializable developerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Developer.class);
        criteria.add(Restrictions.sqlRestriction("project_id = " + projectId));
        criteria.add(Restrictions.sqlRestriction("account_id = " + developerId));
        Developer developer = (Developer) criteria.uniqueResult();
        session.delete(developer);
        transaction.commit();
        session.close();
    }

    public void deleteAllProjectDevelopers(Serializable projectId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        SQLQuery query = session.createSQLQuery(SQL_DELETE);
        query.setParameter("id", projectId);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public List<Developer> readAll() {
        throw new UnsupportedOperationException("need context");
    }

    public List<Developer> readAll(int projectId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Developer.class);
        criteria.add(Restrictions.sqlRestriction("project_id = " + projectId));
        List<Developer> developers = criteria.list();
        transaction.commit();
        session.close();
        return developers;
    }

    public List<Account> readAllAvailable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        SQLQuery query = session.createSQLQuery(SQL_SELECT_AVAIL_DEVS);
        query.addEntity(Account.class);
        List<Account> results = query.list();

        transaction.commit();
        session.close();
        return results;
    }


}

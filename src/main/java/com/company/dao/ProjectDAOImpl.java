package com.company.dao;

import com.company.domain.ProjectDomain;
import com.company.entity.Account;
import com.company.entity.Project;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProjectDAOImpl extends DAO<Project> implements ProjectDAO {
    private static final Logger logger = Logger.getLogger(ProjectDAOImpl.class);

    private static final String SQL_SELECT_CUR_DEV_PROJECT = "SELECT p.id, p.name, p.complete, p.manager_id\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id AND p.complete = FALSE";
    private static final String SQL_SELECT_DEV_PROJECTS = "SELECT p.id, p.name, p.complete, p.manager_id\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id";

    public void create(ProjectDomain projectDomain) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Account account = (Account) session.get(Account.class, projectDomain.getManagerId());
            Project project = new Project(projectDomain, account);
            session.save(project);
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    public void delete(int projectId) {
        Project project = new Project();
        project.setId(projectId);
        delete(project);
    }

    public List<Project> readAllManagerProjects(int managerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
            List<Project> projects = criteria.list();
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

    public Project getCurrentManagerProject(int managerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
            criteria.add(Restrictions.eq("complete", false));
            Project project = (Project) criteria.uniqueResult();
            transaction.commit();
            return project;
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    public Project getCurrentDeveloperProject(int developerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(SQL_SELECT_CUR_DEV_PROJECT);
            query.setParameter("id", developerId);
            query.addEntity(Project.class);
            Project result = (Project) query.uniqueResult();
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

    public List<Project> readAllDeveloperProjects(int developerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(SQL_SELECT_DEV_PROJECTS);
            query.setParameter("id", developerId);
            query.addEntity(Project.class);
            List<Project> results = query.list();
            transaction.commit();
            return results;
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

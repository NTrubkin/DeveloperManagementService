package com.company.dao.impl;

import com.company.dao.api.DAO;
import com.company.dao.api.ProjectDAO;
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

    private static final String SQL_SELECT_CUR_DEV_PROJECT = "SELECT *\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id AND p.complete = FALSE";
    private static final String SQL_SELECT_DEV_PROJECTS = "SELECT *\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id";

    /**
     * Создает объект Project в базе данных
     *
     * @param projectDomain бин-эквивалент сущности Project
     */
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

    /**
     * Удаляет объект Project из базы данных
     *
     * @param projectId id проекта в базе данных
     */
    public void delete(int projectId) {
        Project project = new Project();
        project.setId(projectId);
        delete(project);
    }

    /**
     * Читает все проекты, где автором является менеджер с managerId
     *
     * @param managerId
     * @return
     */
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

    /**
     * Возвращает текущие активные проекты менеджера с managerId
     *
     * @param managerId
     * @return
     */
    public List<Project> getCurrentManagerProjects(int managerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
            criteria.add(Restrictions.eq("complete", false));
            List<Project> projects = (List<Project>) criteria.list();
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

    /**
     * Возвращает текущий активный проект разработчика с managerId
     *
     * @param developerId
     * @return
     */
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
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Читает все проекты с участием разработчика с managerId
     *
     * @param developerId
     * @return
     */
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
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }
}

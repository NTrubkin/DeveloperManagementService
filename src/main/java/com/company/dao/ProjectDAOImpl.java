package com.company.dao;

import com.company.domain.ProjectDomain;
import com.company.entity.Account;
import com.company.entity.Project;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class ProjectDAOImpl implements DAO<Project> {

    private SessionFactory sessionFactory;

    private static final String SQL_SELECT_CUR_DEV_PROJECT = "SELECT p.id, p.name, p.complete, p.manager_id\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id AND p.complete = FALSE";
    private static final String SQL_SELECT_DEV_PROJECTS = "SELECT p.id, p.name, p.complete, p.manager_id\n" +
            "FROM project p INNER JOIN developer d ON p.id = d.project_id\n" +
            "WHERE d.account_id = :id";

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(project);
        transaction.commit();
        session.close();
    }

    public void create(ProjectDomain projectDomain) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = (Account) session.get(Account.class, projectDomain.getManagerId());
        Project project = new Project(projectDomain, account);
        session.save(project);
        transaction.commit();
        session.close();
    }

    @Override
    public Project read(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Project project = (Project) session.get(Project.class, id);
        transaction.commit();
        return project;
    }

    @Override
    public void update(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(project);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(project);
        transaction.commit();
    }

    public void delete(int projectId) {
        Project project = new Project();
        project.setId(projectId);
        delete(project);
    }

    @Override
    public List<Project> readAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Project> projects = session.createCriteria(Project.class).list();
        transaction.commit();
        session.close();
        return projects;
    }

    public List<Project> readAllManagerProjects(int managerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);
        criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
        List<Project> projects = criteria.list();
        transaction.commit();
        session.close();
        return projects;
    }

    public Project getCurrentManagerProject(int managerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);
        criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
        criteria.add(Restrictions.eq("complete", false));
        Project project = (Project) criteria.uniqueResult();
        transaction.commit();
        session.close();
        return project;
    }

        public Project getCurrentDeveloperProject(int developerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        SQLQuery query = session.createSQLQuery(SQL_SELECT_CUR_DEV_PROJECT);
        query.setParameter("id", developerId);
        query.addEntity(Project.class);
        Project result = (Project) query.uniqueResult();

        transaction.commit();
        session.close();
        return result;
    }

    public List<Project> readAllDeveloperProjects(int developerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        SQLQuery query = session.createSQLQuery(SQL_SELECT_DEV_PROJECTS);
        query.setParameter("id", developerId);
        query.addEntity(Project.class);
        List<Project> results = query.list();

        transaction.commit();
        session.close();
        return results;
    }

}

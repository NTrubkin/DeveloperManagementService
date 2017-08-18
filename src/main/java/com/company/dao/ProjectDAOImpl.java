package com.company.dao;

import com.company.domain.ProjectDomain;
import com.company.entity.Account;
import com.company.entity.Project;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class ProjectDAOImpl implements DAO<Project> {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Project project) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(project);
        transaction.commit();
    }

    public void create(ProjectDomain projectDomain) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Account account = (Account) session.get(Account.class, projectDomain.getManagerId());
        Project project = new Project(projectDomain, account);
        session.save(project);
        transaction.commit();
    }

    @Override
    public Project read(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Project project = (Project) session.get(Project.class, id);
        transaction.commit();
        return project;
    }

    @Override
    public void update(Project project) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(project);
        transaction.commit();
    }

    @Override
    public void delete(Project project) {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Project> projects = session.createCriteria(Project.class).list();
        transaction.commit();
        return projects;
    }

    public List<Project> readAll(int managerId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);
        criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
        List<Project> projects = criteria.list();
        transaction.commit();
        return projects;
    }

    public Project getCurrentProject(int managerId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);
        criteria.add(Restrictions.sqlRestriction("manager_id = " + managerId));
        criteria.add(Restrictions.eq("complete", false));
        Project project = (Project) criteria.uniqueResult();
        transaction.commit();
        return project;
    }
}

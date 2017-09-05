package com.company.dao.commentary;

import com.company.dao.DAO;
import com.company.entity.Commentary;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class CommentaryDAOImpl extends DAO<Commentary> implements CommentaryDAO {
    private static final Logger logger = Logger.getLogger(CommentaryDAOImpl.class);

    @Override
    public List<Commentary> readAll() {
        throw new UnsupportedOperationException("need context");
    }

    public List<Commentary> readAll(Serializable projectId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Commentary.class);
            criteria.add(Restrictions.sqlRestriction("project_id = " + projectId));
            List<Commentary> results = criteria.list();
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

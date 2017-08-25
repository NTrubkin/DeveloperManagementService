package com.company.dao;

import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import com.company.entity.Role;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl extends DAO<Account> implements AccountDAO {
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    public void create(SecureAccountDomain secureAccountDomain) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Role role = (Role) session.get(Role.class, secureAccountDomain.getRoleId());
            Account account = new Account(secureAccountDomain, role);
            session.save(account);
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

    public Account read(String nickname) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("nickname", nickname));
            Account account = (Account) criteria.uniqueResult();
            transaction.commit();
            return account;
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

    public void delete(int accountId) {
        Account account = new Account();
        account.setId(accountId);
        delete(account);
    }
}

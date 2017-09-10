package com.company.dao.impl;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.DAO;
import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import com.company.entity.Role;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * DAO, который работает с сущностью Account, реализуя основные методы обработки
 */
@Repository
public class AccountDAOImpl extends DAO<Account> implements AccountDAO {
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    /**
     * Создает объект Account в базе данных на основе secureAccountDomain
     *
     * @param secureAccountDomain бин-эквивалент сущности Account
     */
    public void create(SecureAccountDomain secureAccountDomain) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Role role = (Role) session.get(Role.class, secureAccountDomain.getRoleId());
            Account account = new Account(secureAccountDomain, role);
            session.save(account);
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
     * Читает объект Account из базы данных с nickname
     *
     * @param nickname никнейм аккаунта в базе данных
     * @return Account сущность, если успех
     */
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
        } catch (Exception exc) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw exc;
        } finally {
            session.close();
        }
    }

    /**
     * Удаляет из базы данных объект Account с id
     *
     * @param accountId id аккаунта в базе данных
     */
    public void delete(int accountId) {
        Account account = new Account();
        account.setId(accountId);
        delete(account);
    }
}

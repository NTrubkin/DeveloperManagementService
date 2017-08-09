package com.company.dao;

import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import com.company.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class AccountDAOImpl implements DAO<Account> {
    //@todo написать менеджер транзакций чтобы один метод мог вызывать другой, не создавая конфликт транзакий
    //@todo исправить ошибку org.hibernate.TransactionException: nested transactions not supported (кидает через раз)

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Account account) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
    }

    public void create(SecureAccountDomain secureAccountDomain) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Role role = (Role) session.get(Role.class, secureAccountDomain.getRoleId());
        Account account = new Account(secureAccountDomain, role);
        session.save(account);
        transaction.commit();
    }

    @Override
    public Account read(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Account account = (Account) session.get(Account.class, id);
        transaction.commit();
        return account;
    }

    @Override
    public void update(Account account) {
        throw new UnsupportedOperationException("not supported yet");
    }

    @Override
    public void delete(Account account) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(account);
        transaction.commit();
    }

    public void delete(int accountId) {
        Account account = new Account();
        account.setId(accountId);
        delete(account);
    }

    @Override
    public List<Account> readAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Account> accounts = session.createCriteria(Account.class).list();
        transaction.commit();
        return accounts;
    }
}

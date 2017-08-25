package com.company.dao;

import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;

import java.io.Serializable;
import java.util.List;

public interface AccountDAO {

    void create(Account t);

    Account read(Serializable id);

    void update(Account t);

    void delete(Account t);

    List<Account> readAll();

    void create(SecureAccountDomain secureAccountDomain);

    Account read(String nickname);

    void delete(int accountId);
}

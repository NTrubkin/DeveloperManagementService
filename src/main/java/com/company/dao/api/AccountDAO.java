package com.company.dao.api;

import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;

import java.io.Serializable;
import java.util.List;

/**
 * Определяет методы Hibernate DAO для работы с Account сущностью
 * В том числе и методы общего DAO<T> CRUD + readAll()
 */
public interface AccountDAO {

    /**
     * Создает объект Account в базе данных
     *
     * @param account transient или detached сущность
     */
    void create(Account account);

    /**
     * Читает из базы данных объект Account с id
     *
     * @param id id аккаунта в базе данных
     * @return persistent сущность, если успех
     */
    Account read(Serializable id);

    /**
     * Обновляет объект Account в базе данных
     *
     * @param account detached сущность
     */
    void update(Account account);

    /**
     * Удаляет объект Account из базы данных
     *
     * @param account сущность
     */
    void delete(Account account);

    /**
     * Читает все объекты Account из базы данных
     *
     * @return
     */
    List<Account> readAll();

    /**
     * Создает объект Account в базе данных на основе secureAccountDomain
     *
     * @param secureAccountDomain бин-эквивален сущности Account
     */
    void create(SecureAccountDomain secureAccountDomain);

    /**
     * Читает объект Account из базы данных с nickname
     *
     * @param nickname никнейм аккаунта в базе данных
     * @return Account сущность, если успех
     */
    Account read(String nickname);

    /**
     * Удаляет из базы данных объект Account с id
     *
     * @param accountId id аккаунта в базе данных
     */
    void delete(int accountId);
}

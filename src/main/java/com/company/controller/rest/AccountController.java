package com.company.controller.rest;

import com.company.dao.AccountDAO;
import com.company.domain.AccountDomain;
import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import org.hibernate.StaleStateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер, управляющий частью Rest-сервиса.
 * Здесь представлены методы управления аккаунтами пользователей
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    @Qualifier("accountDAO")
    AccountDAO accountDAO;

    /**
     * Возвращает информацию об аккаунте аутентифицированного пользователя
     *
     * @param authentication
     * @return AccountDomain и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<AccountDomain> getCurrentAccount(Authentication authentication) {
        String auth = authentication.getName();
        AccountDomain accountDomain = new AccountDomain(accountDAO.read(auth));
        return new ResponseEntity<>(accountDomain, HttpStatus.OK);
    }

    /**
     * Возвращает информацию об аккаунте пользователя с id
     *
     * @param accountId id аккаунта в базе данных
     * @return AccountDomain и HttpStatus.OK, если успех,
     * если не найден пользователь с id accountId, HttpStatus.BAD_REQUEST
     */
    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountDomain> getAccount(@PathVariable int accountId) {
        Account account = accountDAO.read(accountId);
        if(account == null) {
            return new ResponseEntity("Bad Request: Account with id " + accountId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new AccountDomain(account), HttpStatus.OK);
    }

    /**
     * Возвращает информацию обо всех пользовательских аккаунтах
     *
     * @return List<AccountDomain> и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getAllAccounts() {
        List<AccountDomain> accountDomains = new ArrayList<>();
        for (Account account : accountDAO.readAll()) {
            accountDomains.add(new AccountDomain(account));
        }
        return new ResponseEntity<>(accountDomains, HttpStatus.OK);
    }

    /**
     * Создает аккаунт пользователя в системе
     *
     * @param accountDomain бин-эквивалент Account сущности
     * @return HttpStatus.OK, если успех
     * если создается аккаунт с неуникальным именем, HttpStatus.BAD_REQUEST
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody SecureAccountDomain accountDomain) {
        accountDomain.encodePass();
        try {
            accountDAO.create(accountDomain);
        }
        catch (ConstraintViolationException exc) {
            return new ResponseEntity("Bad Request: " + exc.getClass().getName()
                    + ". Probably, your nickname " + accountDomain.getNickname() + " is not unique", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет аккаунт пользователя из системы
     *
     * @param accountId id аккаунта в базе данных
     * @return HttpStatus.OK, если успех
     * если удаляется текущий аутентифицированный пользователь, HttpStatus.BAD_REQUEST
     * если удаляется пользователь с неизвестным id, HttpStatus.BAD_REQUEST
     */
    @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable int accountId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        if(account.getId() == accountId) {
            return new ResponseEntity("Bad Request: SelfDeletion is not allowed", HttpStatus.BAD_REQUEST);
        }
        try {
            accountDAO.delete(accountId);
        }
        catch (StaleStateException exc) {
            return new ResponseEntity("Bad Request: " + exc.getClass()
                    + ". Probably, you are trying to delete non-existent account", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}

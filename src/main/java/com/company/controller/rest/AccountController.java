package com.company.controller.rest;

import com.company.dao.AccountDAO;
import com.company.domain.AccountDomain;
import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
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
     * @return AccountDomain и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountDomain> getAccount(@PathVariable int accountId) {
        Account account = accountDAO.read(accountId);
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
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody SecureAccountDomain accountDomain) {
        accountDomain.encodePass();
        accountDAO.create(accountDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет аккаунт пользователя из системы
     *
     * @param accountId id аккаунта в базе данных
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable int accountId) {
        accountDAO.delete(accountId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

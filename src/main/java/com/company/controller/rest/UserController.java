package com.company.controller.rest;

import com.company.dao.AccountDAOImpl;
import com.company.domain.AccountDomain;
import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Qualifier("accountDAO")
    AccountDAOImpl accountDAO;

    //@todo расписать варианты кодов ошибок
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<AccountDomain> getCurrentUser(Authentication authentication) {
        String auth = authentication.getName();
        AccountDomain accountDomain = new AccountDomain(accountDAO.read(auth));
        return new ResponseEntity<>(accountDomain, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<AccountDomain> getUser(@PathVariable int userId) {
        Account account = accountDAO.read(userId);
        return new ResponseEntity<>(new AccountDomain(account), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getAllUsers() {
        List<AccountDomain> accountDomains = new ArrayList<>();
        for (Account account : accountDAO.readAll()) {
            accountDomains.add(new AccountDomain(account));
        }
        return new ResponseEntity<>(accountDomains, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody SecureAccountDomain accountDomain) throws NoSuchAlgorithmException {
        accountDomain.encodePass();
        accountDAO.create(accountDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable int userId) {
        accountDAO.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

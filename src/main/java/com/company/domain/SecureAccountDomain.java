package com.company.domain;

import com.company.entity.Account;
import com.company.util.HashGenerator;

import java.security.NoSuchAlgorithmException;

/**
 * Бин-эквивален сущности Account, использующийся для передачи(в том числе и по сети) основной конфиденциальной информации об аккаунте пользователя.
 * Расширяет функционал AccountDomain для хранения конфиденциальной информации.
 * Добавляет хранение паролей/хешей и метод генерации хешей.
 */
public class SecureAccountDomain extends AccountDomain {

    private String passhash;        // может хранить пароль либо хеш пароля

    public SecureAccountDomain() {
        super();
    }

    public SecureAccountDomain(int id, String nickname, int roleId, String passhash) {
        super(id, nickname, roleId);
        this.passhash = passhash;
    }

    public SecureAccountDomain(Account account) {
        super(account);
        if (account != null) {
            this.passhash = account.getPasshash();
        }
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    /**
     * Генерирует хеш пароля. Подменяет пароль из passhash на новый хеш
     */
    public void encodePass() {
        try {
            passhash = HashGenerator.generateSHA1(passhash);
        } catch (NoSuchAlgorithmException exc) {
            throw new UnsupportedOperationException("Error with hash generator", exc);
        }
    }
}

package com.company.domain;

import com.company.entity.Account;

public class SecureAccountDomain extends AccountDomain {

    private String passhash;

    public SecureAccountDomain() {
        super();
    }

    public SecureAccountDomain(int id, String nickname, int roleId, String passhash) {
        super(id, nickname, roleId);
        this.passhash = passhash;
    }

    public SecureAccountDomain(Account account) {
        super(account);
        this.passhash = account.getPasshash();
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }
}

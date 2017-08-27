package com.company.domain;

import com.company.entity.Account;

/**
 * Бин-эквивален сущности Account, использующийся для передачи(в том числе и по сети) основной публичной информации об аккаунте пользователя.
 * Является облегченным модифицированным аналогом сущности Account.
 * Способен конвертировать Account.
 * Не следует использовать для передачи конфиденциальной информации(пароли, хеши, прочее)
 */
public class AccountDomain {
    private int id;
    private String nickname;
    private int roleId;

    public AccountDomain() {
    }

    public AccountDomain(int id, String nickname, int roleId) {
        this.id = id;
        this.nickname = nickname;
        this.roleId = roleId;
    }

    /**
     * Конструктор преобразования сущности Account в AccountDomain
     *
     * @param account сущность
     */
    public AccountDomain(Account account) {
        if (account != null) {
            this.id = account.getId();
            this.nickname = account.getNickname();
            this.roleId = account.getRole().getId();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

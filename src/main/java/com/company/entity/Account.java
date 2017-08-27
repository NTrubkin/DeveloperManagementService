package com.company.entity;

import com.company.domain.SecureAccountDomain;

import javax.persistence.*;

/**
 * Hibernate сущность, которая представляет аккаунт пользователя.
 * Аккаунт пользователя позволяет однозначно аутентифицировать и авторизовать пользователя.
 */
@Entity
@Table(name = "account", schema = "public", catalog = "postgres")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "passhash")
    private String passhash;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Account() {
    }

    /**
     * Конструктор преобразования сущности SecureAccountDomain в Account
     *
     * @param accountDomain
     */
    public Account(SecureAccountDomain accountDomain) {
        this(accountDomain, null);
    }

    /**
     * Конструктор преобразования сущности SecureAccountDomain в Account
     *
     * @param accountDomain
     * @param role
     */
    public Account(SecureAccountDomain accountDomain, Role role) {
        this.id = accountDomain.getId();
        this.nickname = accountDomain.getNickname();
        this.passhash = accountDomain.getPasshash();
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

// hibernate не принимает. Временно отключен

package com.company.entity;

import javax.persistence.*;

/**
 * Hibernate сущность, которая представляет факт привязки разработчика, к проекту
 */
@Entity
@Table(name = "developer", schema = "public")
public class Developer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Developer() {
    }

    public Developer(Account account, Project project) {
        this.id = id;
        this.account = account;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

// hibernate не принимает. Временно отключен

package com.company.entity;

import javax.persistence.*;

@Entity
@Table(name = "developer", schema = "public", catalog = "postgres")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public Developer(Account account, Project project) {
        this.id = id;
        this.account = account;
        this.project = project;
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

// hibernate не принимает. Временно отключен

/*
package com.company.entity;

import javax.persistence.*;

@Entity
@Table(name = "developer", schema = "public", catalog = "postgres")
public class Developer {
    @Id
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

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
*/

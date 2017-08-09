package com.company.entity;

import com.company.domain.ProjectDomain;

import javax.persistence.*;

@Entity
@Table(name = "project", schema = "public", catalog = "postgres")
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "complete")
    private boolean complete;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Account manager;

    public Project() {
    }

    public Project(ProjectDomain projectDomain) {
        this(projectDomain, null);
    }

    public Project(ProjectDomain projectDomain, Account manager) {
        this.id = projectDomain.getId();
        this.name = projectDomain.getName();
        this.complete = projectDomain.isComplete();
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Account getManager() {
        return manager;
    }

    public void setManager(Account manager) {
        this.manager = manager;
    }
}

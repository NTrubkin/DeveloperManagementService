package com.company.entity;

import com.company.domain.ProjectDomain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Hibernate сущность, которая представляет собой проект.
 * Проект - это некая задача профильная задача предприятия, которую назначает проект менеджер и исполняют разработчики
 */
@Entity
@Table(name = "project", schema = "public", catalog = "wqxzgyfo")
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

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "estimated_end")
    private Timestamp estimatedEnd;

    /**
     * Поле end указывает на фактическое время завершения проекта
     * Если поле complete ложно, то end следует присвоить null и определить позднее по факту завершения проекта
     */
    @Column(name = "end_")
    private Timestamp end;

    public Project() {
    }

    /**
     * Конструктор преобразования сущности ProjectDomain в Project
     *
     * @param projectDomain
     */
    public Project(ProjectDomain projectDomain) {
        this(projectDomain, null);
    }

    /**
     * Конструктор преобразования сущности ProjectDomain в Project
     *
     * @param projectDomain
     * @param manager
     */
    public Project(ProjectDomain projectDomain, Account manager) {
        this.id = projectDomain.getId();
        this.name = projectDomain.getName();
        this.complete = projectDomain.isComplete();
        this.manager = manager;
        this.start = new Timestamp(projectDomain.getStart());
        this.estimatedEnd = new Timestamp(projectDomain.getEstimatedEnd());
        if (projectDomain.getEnd() == null) {
            this.end = null;
        }
        else {
            this.end = new Timestamp(projectDomain.getEnd());
        }
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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEstimatedEnd() {
        return estimatedEnd;
    }

    public void setEstimatedEnd(Timestamp estimatedEnd) {
        this.estimatedEnd = estimatedEnd;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}

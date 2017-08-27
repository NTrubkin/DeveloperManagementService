package com.company.domain;

import com.company.entity.Project;

/**
 * Бин-эквивален сущности Project, использующийся для передачи(в том числе и по сети) основной информации о проекте.
 */
public class ProjectDomain {
    private int id;

    private String name;

    private boolean complete;

    private int managerId;

    public ProjectDomain() {
    }

    public ProjectDomain(int id, String name, boolean complete, int managerId) {
        this.id = id;
        this.name = name;
        this.complete = complete;
        this.managerId = managerId;
    }

    public ProjectDomain(Project project) {
        if (project != null) {
            this.id = project.getId();
            this.name = project.getName();
            this.complete = project.isComplete();
            this.managerId = project.getManager().getId();
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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}

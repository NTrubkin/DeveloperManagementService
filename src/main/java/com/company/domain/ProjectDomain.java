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

    // todo написать тесты проверки корректности конвертирования времени сущность-домен
    private long start;

    private long estimatedEnd;

    /**
     * Поле end указывает на фактическое время завершения проекта
     * Если поле complete ложно, то end следует присвоить null и определить позднее по факту завершения проекта
     */
    private Long end;

    public ProjectDomain() {
    }

    public ProjectDomain(int id, String name, boolean complete, int managerId, long start, long estimatedEnd, long end) {
        this.id = id;
        this.name = name;
        this.complete = complete;
        this.managerId = managerId;
        this.start = start;
        this.estimatedEnd = estimatedEnd;
        this.end = end;
    }

    public ProjectDomain(Project project) {
        if (project != null) {
            this.id = project.getId();
            this.name = project.getName();
            this.complete = project.isComplete();
            this.managerId = project.getManager().getId();
            this.start = project.getStart().getTime();
            this.estimatedEnd = project.getEstimatedEnd().getTime();
            if (project.getEnd() == null) {
                end = null;
            }
            else {
                end = project.getEnd().getTime();
            }
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEstimatedEnd() {
        return estimatedEnd;
    }

    public void setEstimatedEnd(long estimatedEnd) {
        this.estimatedEnd = estimatedEnd;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}

package com.company.dao;

import com.company.domain.ProjectDomain;
import com.company.entity.Project;

import java.io.Serializable;
import java.util.List;

public interface ProjectDAO {

    void create(Project t);

    Project read(Serializable id);

    void update(Project t);

    void delete(Project t);

    List<Project> readAll();

    void create(ProjectDomain projectDomain);

    void delete(int projectId);

    List<Project> readAllManagerProjects(int managerId);

    Project getCurrentManagerProject(int managerId);

    Project getCurrentDeveloperProject(int developerId);

    List<Project> readAllDeveloperProjects(int developerId);
}

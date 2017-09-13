package com.company.dao.api;

import com.company.domain.ProjectDomain;
import com.company.entity.Project;

import java.io.Serializable;
import java.util.List;

/**
 * Определяет методы Hibernate DAO для работы с Project сущностью
 * В том числе и методы общего DAO<T> CRUD + readAll()
 */
public interface ProjectDAO {

    /**
     * Создает объект Project в базе данных
     *
     * @param project transient или detached сущность
     */
    void create(Project project);

    /**
     * Читает из базы данных объект Project с id
     *
     * @param id id проекта в базе данных
     * @return persistent сущность, если успех
     */
    Project read(Serializable id);

    /**
     * Обновляет объект Project в базе данных
     *
     * @param project detached сущность
     */
    void update(Project project);

    /**
     * Удаляет объект Project из базы данных
     *
     * @param project сущность
     */
    void delete(Project project);

    /**
     * Читает все объекты Project из базы данных
     *
     * @return
     */
    List<Project> readAll();

    /**
     * Создает объект Project в базе данных
     *
     * @param projectDomain бин-эквивалент сущности Project
     */
    void create(ProjectDomain projectDomain);

    /**
     * Удаляет объект Project из базы данных
     *
     * @param projectId id проекта в базе данных
     */
    void delete(int projectId);

    /**
     * Читает все проекты, где автором является менеджер с managerId
     *
     * @param managerId
     * @return
     */
    List<Project> readAllManagerProjects(int managerId);

    /**
     * Возвращает текущие активные проекты менеджера с managerId
     *
     * @param managerId
     * @return
     */
    List<Project> getCurrentManagerProjects(int managerId);

    /**
     * Возвращает текущий активный проект разработчика с managerId
     *
     * @param developerId
     * @return
     */
    Project getCurrentDeveloperProject(int developerId);

    /**
     * Читает все проекты с участием разработчика с managerId
     *
     * @param developerId
     * @return
     */
    List<Project> readAllDeveloperProjects(int developerId);
}

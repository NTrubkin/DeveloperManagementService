package com.company.dao;

import com.company.entity.Account;
import com.company.entity.Developer;

import java.io.Serializable;
import java.util.List;

public interface DeveloperDAO {

    /**
     * Создает объект Developer в базе данных
     *
     * @param developer transient или detached сущность
     */
    void create(Developer developer);

    /**
     * Читает из базы данных объект Developer с id
     *
     * @param id id аккаунта в базе данных
     * @return persistent сущность, если успех
     */
    Developer read(Serializable id);

    /**
     * Обновляет объект Developer в базе данных
     *
     * @param developer detached сущность
     */
    void update(Developer developer);

    /**
     * Удаляет объект Developer из базы данных
     *
     * @param developer сущность
     */
    void delete(Developer developer);

    /**
     * Удаляет объект Developer из базы данных
     *
     * @param id id объекта в базе данных
     */
    void delete(Serializable id);

    /**
     * Удаляет объект Developer из базы данных
     *
     * @param projectId   id проекта
     * @param developerId id разработчика
     */
    void delete(Serializable projectId, Serializable developerId);

    /**
     * Удаляет все объекты Developer, которые принадлежат проекту projectId
     *
     * @param projectId id проекта
     */
    void deleteAllProjectDevelopers(Serializable projectId);

    /**
     * Читает все объекты Developer, принадлежащие проекту projectId
     *
     * @param projectId id проекта
     * @return
     */
    List<Developer> readAll(int projectId);

    /**
     * Возвращает аккаунты всех свободных разработчиков
     * Свободным считается разработчик, у которого нет текущего активного проектв
     *
     * @return
     */
    List<Account> readAllAvailable();

    boolean isDeveloperOfProject(int developerId, int projectId);
}

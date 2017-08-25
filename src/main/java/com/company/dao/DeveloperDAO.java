package com.company.dao;

import com.company.entity.Account;
import com.company.entity.Developer;

import java.io.Serializable;
import java.util.List;

public interface DeveloperDAO {

    void create(Developer t);

    Developer read(Serializable id);

    void update(Developer t);

    void delete(Developer t);

    void delete(Serializable id);

    void delete(Serializable projectId, Serializable developerId);

    void deleteAllProjectDevelopers(Serializable projectId);

    List<Developer> readAll(int projectId);

    List<Account> readAllAvailable();
}

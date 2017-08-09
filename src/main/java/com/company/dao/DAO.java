package com.company.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
    void create(T t);

    T read(Serializable id);

    void update(T t);

    void delete(T t);

    List<T> readAll();
}

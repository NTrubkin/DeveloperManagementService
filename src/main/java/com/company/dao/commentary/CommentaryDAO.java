package com.company.dao.commentary;

import com.company.domain.CommentaryDomain;
import com.company.entity.Commentary;

import java.io.Serializable;
import java.util.List;

public interface CommentaryDAO {
    /**
     * Создает объект Commentary в базе данных
     *
     * @param commentary transient или detached сущность
     */
    void create(Commentary commentary);

    /**
     * Читает из базы данных объект Commentary с id
     *
     * @param id id аккаунта в базе данных
     * @return persistent сущность, если успех
     */
    Commentary read(Serializable id);

    /**
     * Обновляет объект Commentary в базе данных
     *
     * @param commentary detached сущность
     */
    void update(Commentary commentary);

    /**
     * Удаляет объект Commentary из базы данных
     *
     * @param commentary сущность
     */
    void delete(Commentary commentary);

    List<Commentary> readAll(Serializable projectId);
}

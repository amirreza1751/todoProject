package com.example.demo.services;

import java.util.List;

public interface GenericService<E> {
    List<E> getEntities();

    E getEntityById(Long id);

    E insert(E entity);

    void updateEntity(Long id, E entity);

    void deleteEntity(Long entityId);
}

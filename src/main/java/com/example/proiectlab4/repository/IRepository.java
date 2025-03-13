package com.example.proiectlab4.repository;



import com.example.proiectlab4.domain.Entity;

import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T>{
    void add(T entity) throws RepositoryException;
    void remove(int id) throws RepositoryException;
    T find(int id);
    Collection<T> getAll();
    void update(T oldEntity, T newEntity) throws RepositoryException;
    void commit();

    T getById(int i);
}

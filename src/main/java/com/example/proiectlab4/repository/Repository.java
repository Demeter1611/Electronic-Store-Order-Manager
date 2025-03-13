package com.example.proiectlab4.repository;



import com.example.proiectlab4.domain.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Repository<T extends Entity> implements IRepository<T>{

    List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) throws RepositoryException {
        if(find(entity.getId()) != null){
            throw new DuplicateEntityException("Duplicate id");
        }
        entities.add(entity);
    }

    @Override
    public void remove(int id) throws RepositoryException {
        T entity = find(id);
        if(entity == null){
            throw new EntityNotFoundException("Entity not found");
        }
        entities.remove(entity);
    }

    @Override
    public T find(int id) {
        for(T entity : entities){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }

    @Override
    public Collection<T> getAll() {
        return entities;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entities).iterator();
    }

    @Override
    public void update(T oldEntity, T newEntity) throws RepositoryException {
        if(find(oldEntity.getId()) == null){
            throw new EntityNotFoundException("Entity not found");
        }

        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId() == oldEntity.getId()){
                entities.set(i, newEntity);
            }
        }
    }

    public T getById(int id){
        for(T entity : entities){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }

    public void commit(){
        return;
    }
}

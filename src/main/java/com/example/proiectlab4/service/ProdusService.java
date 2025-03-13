package com.example.proiectlab4.service;



import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.IRepository;
import com.example.proiectlab4.repository.RepositoryException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ProdusService {
    IRepository<Produs> repo;

    public ProdusService(IRepository<Produs> repo){
        this.repo = repo;
    }

    public void add(int id, String categorie, String nume, int pret) throws RepositoryException {
        Produs p = new Produs(id, categorie, nume, pret);
        repo.add(p);
    }

    public void remove(int id) throws RepositoryException{
        repo.remove(id);
    }

    public void update(int id, String categorie, String nume, int pret) throws RepositoryException{
        Produs p = new Produs(id, categorie, nume, pret);
        repo.update(repo.find(id), p);
    }

    public Collection<Produs> getAll(){
        return repo.getAll();
    }

    public Produs getById(int id){
        return repo.find(id);
    }
}

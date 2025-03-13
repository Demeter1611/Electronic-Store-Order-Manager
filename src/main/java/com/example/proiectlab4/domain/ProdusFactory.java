package com.example.proiectlab4.domain;

public class ProdusFactory implements IEntityFactory<Produs>{
    @Override
    public Produs createEntity(String line) {
        String[] campuri = line.split(",");
        int id = Integer.parseInt(campuri[0]);
        String categorie = campuri[1];
        String nume = campuri[2];
        int pret = Integer.parseInt(campuri[3]);
        return new Produs(id, categorie, nume, pret);
    }
}

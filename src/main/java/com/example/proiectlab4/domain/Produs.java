package com.example.proiectlab4.domain;

import java.util.Objects;

public class Produs extends Entity{
    protected String categorie;
    protected String nume;
    protected int pret;

    public Produs(int id, String categorie, String nume, int pret){
        super(id);
        this.categorie = categorie;
        this.nume = nume;
        this.pret = pret;
    }

    public String getCategorie(){
        return categorie;
    }

    public String getNume(){
        return nume;
    }

    public int getPret(){
        return pret;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Produs produs = (Produs) o;
        return id == produs.id && categorie == produs.categorie && nume == produs.nume && pret == produs.pret;
    }

    @Override
    public int hashCode(){
        return Objects.hash(categorie, nume, pret);
    }

    @Override
    public String toString(){
        return "(id = " + id + ", categorie = " + categorie + ", nume = " + nume + ", pret = " + pret + ")";
    }

    @Override
    public String toFile(){
        return id + "," + categorie + "," + nume + "," + pret;
    }
}

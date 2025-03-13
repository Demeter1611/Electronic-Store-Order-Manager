package com.example.proiectlab4.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Comanda extends Entity
{
    List<Produs> produse;
    Date dataLivrare;

    public Comanda(int id, List<Produs> produse, Date dataLivrare){
        super(id);
        this.produse = produse;
        this.dataLivrare = dataLivrare;
    }

    public List<Produs> getProduse(){
        return produse;
    }

    public Date getDataLivrare(){
        return dataLivrare;
    }


    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass())
            return false;
        Comanda comanda = (Comanda) o;
        return id == comanda.id && produse == comanda.produse && dataLivrare == comanda.dataLivrare;
    }
    @Override
    public int hashCode(){
        return Objects.hash(produse, dataLivrare);
    }

    @Override
    public String toString(){
        return "(id = " + id + ", produse = " + produse + ", data livrare = " + dataLivrare + ")";
    }

    public String toFile(){
        String result = "";
        result = result + id + ",(";
        for(int i = 0; i < produse.size(); i++){
            result += produse.get(i).toFile();
            if(i != produse.size()-1){
                result += ";";
            }
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        result = result + ")," + formatter.format(dataLivrare);
        return result;
    }
}

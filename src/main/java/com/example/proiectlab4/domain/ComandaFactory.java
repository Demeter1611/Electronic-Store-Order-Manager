package com.example.proiectlab4.domain;

import com.example.proiectlab4.repository.IRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ComandaFactory implements IEntityFactory<Comanda>{

    ProdusFactory produsFactory;
    IRepository<Produs> produsRepository;

    public String serializeProduse(List<Produs> produse){
        StringBuilder serialized = new StringBuilder();
        for(Produs produs: produse){
            serialized.append(produs.getId()).append(",");
        }
        if(serialized.length() > 0){
            serialized.setLength(serialized.length() - 1);
        }
        return serialized.toString();
    }

    public List<Produs> deserializeProduse(String serializedProduse){
        List<Produs> produse = new ArrayList<>();
        String[] ids = serializedProduse.split(",");
        for(String id: ids){
            Produs produs = produsRepository.getById(Integer.parseInt(id));
            if(produs != null){
                produse.add(produs);
            }
        }
        return produse;
    }

    public ComandaFactory(ProdusFactory produsFactory, IRepository<Produs> produsRepository) {
        this.produsFactory = produsFactory;
        this.produsRepository = produsRepository;
    }
    @Override
    public Comanda createEntity(String line) {
        String[] campuri = line.split("[()]");
        int id = Integer.parseInt(campuri[0].replace(",", ""));
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = formatter.parse(campuri[2].replace(",", ""));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        List<Produs> listaProduse= new ArrayList<>();
        String[] stringProduse = campuri[1].split(";");
        for(String str : stringProduse){
            Produs p = produsFactory.createEntity(str);
            listaProduse.add(p);
        }
        return new Comanda(id, listaProduse, data);
    }
}

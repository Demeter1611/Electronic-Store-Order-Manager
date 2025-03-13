package com.example.proiectlab4.service;



import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.IRepository;
import com.example.proiectlab4.repository.RepositoryException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComandaService {
    IRepository<Comanda> repo;


    public ComandaService(IRepository<Comanda> repo){
        this.repo = repo;
    }

    public void add(int id, List<Produs> produse, Date dataLivrare) throws RepositoryException {
        Comanda c = new Comanda(id, produse, dataLivrare);
        repo.add(c);
    }

    public void remove(int id) throws RepositoryException {
        repo.remove(id);
    }

    public void update(int id, List<Produs> produse, Date dataLivrare) throws RepositoryException{
        Comanda c = new Comanda(id, produse, dataLivrare);
        repo.update(repo.find(id), c);
    }

    public List<Comanda> getAll(){
        return (List<Comanda>) repo.getAll();
    }

    public Map<String, Long> getProdusePerCategorie(){
        var comenzi = repo.getAll();
        return comenzi.stream().flatMap(comanda -> comanda.getProduse().stream())
                .collect(Collectors.groupingBy(Produs::getCategorie, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<Integer, Map.Entry<Long, Long>> getProfitabilitateLuni(){
        var comenzi = repo.getAll();
        return comenzi.stream()
                .collect(Collectors.groupingBy(
                        comanda -> comanda.getDataLivrare().getMonth() + 1,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                comenziPeLuna -> {
                                    long numarComenzi = comenziPeLuna.size();
                                    long venitTotal = comenziPeLuna.stream()
                                            .flatMap(comanda -> comanda.getProduse().stream())
                                            .mapToLong(Produs::getPret)
                                            .sum();
                                    return Map.entry(numarComenzi, venitTotal);
                                }
                        )
                ));
    }

    public List<Map.Entry<Produs, Long>> getProduseSortateDupaIncasari(){
        var comenzi = repo.getAll();
        return comenzi.stream()
                .flatMap(comanda -> comanda.getProduse().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.summingLong(Produs::getPret)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Produs, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
    }
}

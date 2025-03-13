/*package com.example.proiectlab4.service;


import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.repository.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComandaServiceTest {
    Repository<Comanda> repo = new Repository<>();
    ComandaService service = new ComandaService(repo);

    @Test
    @DisplayName("Add product")
    void add() throws Exception {
        List<Produs> p = new ArrayList<>();
        p.add(new Produs(1, "abc", "def", 100));
        service.add(1, p, new Date());
        service.add(2, p, new Date());
        assert(repo.getAll().size() == 2);
    }

    @Test
    @DisplayName("Remove product")
    void remove() throws Exception {
        List<Produs> p = new ArrayList<>();
        p.add(new Produs(1, "abc", "def", 100));
        service.add(1, p, new Date());
        service.add(2, p, new Date());
        service.remove(1);
        assert(repo.getAll().size() == 1);
    }

    @Test
    @DisplayName("Update product")
    void update() throws Exception {
        Produs produsVechi = new Produs(1, "abc", "def", 100);
        Produs produsNou = new Produs(2, "abc", "def", 100);
        List<Produs> listaVeche = new ArrayList<>();
        listaVeche.add(produsVechi);
        service.add(1, listaVeche, new Date());
        List<Comanda> listaRepo = service.getAll();
        assert(listaRepo.getFirst().getProduse() == listaVeche);
        List<Produs> listaNoua = new ArrayList<>();
        listaNoua.add(produsNou);
        service.update(1, listaNoua, new Date());
        assert(listaRepo.getFirst().getProduse() == listaNoua);

    }
}*/
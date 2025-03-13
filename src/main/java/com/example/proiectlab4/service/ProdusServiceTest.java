/*package com.example.proiectlab4.service;

import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.Repository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdusServiceTest {

    Repository<Produs> repo = new Repository<>();
    ProdusService service = new ProdusService(repo);
    @Test
    void add() throws Exception{
        service.add(1, "abc", "def", 100);
        List<Produs> listaProduse = (List<Produs>) service.getAll();
        assert(listaProduse.size() == 1);
        service.add(2, "abc", "def", 100);
        assert(listaProduse.size() == 2);
        service.add(3, "abc", "def", 100);
        assert(listaProduse.size() == 3);
    }

    @Test
    void remove() throws Exception{
        service.add(1, "abc", "def", 100);
        service.add(2, "abc", "def", 100);
        service.add(3, "abc", "def", 100);
        List<Produs> listaProduse = (List<Produs>) service.getAll();
        assert(listaProduse.size() == 3);
        service.remove(2);
        assert (listaProduse.size() == 2);
    }

    @Test
    void update() throws Exception{
        service.add(1, "abc", "def", 100);
        service.add(2, "abc", "def", 100);
        service.add(3, "abc", "def", 100);
        List<Produs> listaProduse = (List<Produs>) service.getAll();
        service.update(1, "def", "nume", 200);
        assert(listaProduse.get(0).getNume().equals("nume"));
        assert(listaProduse.get(0).getCategorie().equals("def"));
        assert(listaProduse.get(0).getPret() == 200);
    }
}*/
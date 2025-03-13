package com.example.proiectlab4.ui;



import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.RepositoryException;
import com.example.proiectlab4.service.ComandaService;
import com.example.proiectlab4.service.ProdusService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Console {
    private final int PRODUS = 1;
    private final int COMANDA = 2;

    ComandaService comandaService;
    ProdusService produsService;


    public Console(ComandaService comandaService, ProdusService produsService) {
        this.comandaService = comandaService;
        this.produsService = produsService;
    }

    public void printMenu() {
        System.out.println("1.Afiseaza entitate");
        System.out.println("2.Adauga entitate");
        System.out.println("3.Elimina entitate");
        System.out.println("4.Modifica entitate");
        System.out.println("5.Produse pe categorie");
        System.out.println("6.Profitabilitate luni");
        System.out.println("7.Produse sortate dupa incasari");
    }

    public int chooseEntityType() {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != PRODUS && choice != COMANDA) {
            System.out.println("Alege tipul de entitate (1 - Produs; 2 - Comanda)");
            choice = scanner.nextInt();
        }
        return choice;
    }

    private Date inputDate() {
        Scanner scanner = new Scanner(System.in);
        try {
            String dateString = scanner.next();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(dateString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Produs> inputProduse() {
        Scanner scanner = new Scanner(System.in);
        List<Produs> listaProduse = new ArrayList<Produs>();

        printProduse();
        Produs p = null;
        do {
            System.out.println("Id produs(-1 daca nu mai vreti sa introduceti alte produse): ");
            int idProdus = scanner.nextInt();
            p = produsService.getById(idProdus);
            if (p != null) {
                listaProduse.add(p);
            }
        } while (p != null);
        return listaProduse;
    }

    public void printComenzi() {
        Collection<Comanda> lista = comandaService.getAll();
        for (Comanda comanda : lista) {
            System.out.println(comanda);
        }
    }

    public void printProduse() {
        Collection<Produs> lista = produsService.getAll();
        for (Produs produs : lista) {
            System.out.println(produs);
        }
    }

    public void addComanda() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Id: ");
        int id = scanner.nextInt();

        System.out.println("Produse: ");
        List<Produs> listaProduse = inputProduse();

        System.out.println("Data(dd/mm/yyyy): ");
        Date data = inputDate();

        try {
            comandaService.add(id, listaProduse, data);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProdus() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Id: ");
        int id = scanner.nextInt();

        System.out.println("Categorie: ");
        String categorie = scanner.next();

        System.out.println("Nume: ");
        String nume = scanner.next();

        System.out.println("Pret: ");
        int pret = scanner.nextInt();

        try {
            produsService.add(id, categorie, nume, pret);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProdus() {
        Scanner scanner = new Scanner(System.in);

        printProduse();
        System.out.println("Id: ");
        int id = scanner.nextInt();
        try {
            produsService.remove(id);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeComanda() {
        Scanner scanner = new Scanner(System.in);

        printComenzi();
        System.out.println("Id: ");
        int id = scanner.nextInt();
        try {
            comandaService.remove(id);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProdus() {
        Scanner scanner = new Scanner(System.in);

        printProduse();
        System.out.println("Id: ");
        int id = scanner.nextInt();

        System.out.println("Categorie noua: ");
        String categorie = scanner.next();

        System.out.println("Nume nou: ");
        String nume = scanner.next();

        System.out.println("Pret nou: ");
        int pret = scanner.nextInt();
        try {
            produsService.update(id, categorie, nume, pret);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateComanda() {
        Scanner scanner = new Scanner(System.in);

        printComenzi();
        System.out.println("Id: ");
        int id = scanner.nextInt();

        System.out.println("Produse noi: ");
        List<Produs> listaProduse = inputProduse();

        System.out.println("Data noua(dd/mm/yyyy): ");
        Date data = inputDate();
        try {
            comandaService.update(id, listaProduse, data);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printProdusePerCategorie(){
        System.out.println(comandaService.getProdusePerCategorie());
    }

    public void printProfitabilitateLuni(){
        System.out.println(comandaService.getProfitabilitateLuni());
    }

    public void printProduseSortateDupaIncasari(){
        System.out.println(comandaService.getProduseSortateDupaIncasari());
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            try {
                int choice = scanner.nextInt();
                int type = 0;
                switch (choice) {
                    case 1:
                        type = chooseEntityType();
                        if (type == PRODUS) {
                            printProduse();
                        } else if (type == COMANDA) {
                            printComenzi();
                        }
                        break;

                    case 2:
                        type = chooseEntityType();
                        if (type == PRODUS) {
                            addProdus();
                        } else if (type == COMANDA) {
                            addComanda();
                        }
                        break;

                    case 3:
                        type = chooseEntityType();
                        if (type == PRODUS) {
                            removeProdus();
                        } else if (type == COMANDA) {
                            removeComanda();
                        }
                        break;

                    case 4:
                        type = chooseEntityType();
                        if (type == PRODUS) {
                            updateProdus();
                        } else if (type == COMANDA) {
                            updateComanda();
                        }
                        break;
                    case 5:
                        printProdusePerCategorie();
                        break;
                    case 6:
                        printProfitabilitateLuni();
                        break;
                    case 7:
                        printProduseSortateDupaIncasari();
                        break;
                    case 0:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

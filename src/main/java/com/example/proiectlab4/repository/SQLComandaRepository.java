package com.example.proiectlab4.repository;

import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.domain.ComandaFactory;
import com.example.proiectlab4.domain.Produs;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SQLComandaRepository extends Repository<Comanda>{
    ComandaFactory comandaFactory;
    Connection connection;
    private String DB_URL = "jdbc:sqlite:C:/Users/user/IdeaProjects/proiectlab4/sqldb";

    public SQLComandaRepository(ComandaFactory comandaFactory) {
        this.comandaFactory = comandaFactory;
        openConnection();
        createTable();
        initComandaTable();
        loadData();
    }

    private void loadData(){
        entities.addAll(this.getAll());
    }

    private void initComandaTable(){
        try (Statement clearStatement = connection.createStatement()){
            clearStatement.execute("DELETE FROM comenzi");
        } catch (Exception e){
            throw new RuntimeException("Failed to clear table: " + e.getMessage());
        }
        Random rand = new Random();
        List<Comanda> comandaList = new ArrayList<>();
        for(int i = 1000; i < 1100; i++){
            Comanda c = new Comanda(i, comandaFactory.deserializeProduse(Integer.toString(rand.nextInt(100) + 100)), new Date(2024, rand.nextInt(12), 0));
            comandaList.add(c);
        }

        String s = "INSERT INTO comenzi values (?,?,?)";
        try{
            for(Comanda c: comandaList) {
                PreparedStatement add_statement = connection.prepareStatement(s);
                add_statement.setInt(1, c.getId());
                add_statement.setString(2, comandaFactory.serializeProduse(c.getProduse()));
                add_statement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(c.getDataLivrare()));
                add_statement.executeUpdate();
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void openConnection(){
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(DB_URL);
        try{
            if(connection == null || connection.isClosed()){
                connection = dataSource.getConnection();
            }
        } catch(SQLException e){
            System.out.println("Eroare la crearea conexiunii " + e);
        }
    }

    public void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void createTable(){
        String s = "Create Table if not exists comenzi ( id int, produse text, dataLivrare date, PRIMARY KEY (id) )";
        try {
            Statement statement = connection.createStatement();
            boolean executionResult = statement.execute(s);
            System.out.println("createTable() execution result: " + executionResult);
        } catch (SQLException e) {
            System.out.println("Eroare la crearea tabelei comenzi" + e);
        }
    }

    @Override
    public void add(Comanda c) throws RepositoryException{
        super.add(c);
        String s = "INSERT INTO comenzi VALUES (?, ?, ?)";
        try{
            PreparedStatement add_statement = connection.prepareStatement(s);
            add_statement.setInt(1, c.getId());
            add_statement.setString(2, comandaFactory.serializeProduse(c.getProduse()));
            add_statement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(c.getDataLivrare()));

            int executionResult = add_statement.executeUpdate();
            System.out.println("add() execution result: " + executionResult);
        } catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void remove(int id) throws RepositoryException{
        super.remove(id);
        String s = "DELETE FROM comenzi WHERE id = ?";
        try(PreparedStatement remove_statement = connection.prepareStatement(s)){
            remove_statement.setInt(1, id);
            remove_statement.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Collection<Comanda> getAll(){
        List<Comanda> resultList = new ArrayList<>();
        String s = "SELECT * FROM comenzi";
        try(PreparedStatement getAllStatement = connection.prepareStatement(s)){
            ResultSet result = getAllStatement.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                List<Produs> produse = comandaFactory.deserializeProduse(result.getString("produse"));
                String dateStr = result.getString("dataLivrare");
                java.util.Date dataLivrare = null;
                if (dateStr != null) {
                    try {
                        dataLivrare = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    } catch (ParseException e) {
                        throw new RuntimeException("Date parsing failed: " + dateStr, e);
                    }
                }

                Comanda c = new Comanda(id, produse, dataLivrare);
                resultList.add(c);
            }
            return resultList;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

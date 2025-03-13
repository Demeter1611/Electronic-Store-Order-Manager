package com.example.proiectlab4.repository;

import com.example.proiectlab4.domain.Produs;
import com.github.javafaker.Faker;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SQLProdusRepository extends Repository<Produs> {
    Connection connection;
    private String DB_URL = "jdbc:sqlite:C:/Users/user/IdeaProjects/proiectlab4/sqldb";

    public SQLProdusRepository() {
        openConnection();
        createTable();
        initProdusTable();
        loadData();
    }

    private void loadData(){
        entities.addAll(this.getAll());
    }

    private void initProdusTable(){
        try (Statement clearStatement = connection.createStatement()) {
            clearStatement.execute("DELETE FROM produse");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear table: " + e.getMessage());
        }

        List<Produs> produsList = new ArrayList<>();
        Faker faker = new Faker();
        Random rand = new Random();
        for(int i = 100; i < 130; i++) {
            produsList.add(new Produs(i, "food", faker.food().dish(), rand.nextInt(100)));
        }
        for(int i = 130; i < 160; i++){
            produsList.add(new Produs(i, "fruit", faker.food().fruit(), rand.nextInt(100)));
        }
        for(int i = 160; i < 200; i++){
            produsList.add(new Produs(i, "vegetable", faker.food().vegetable(), rand.nextInt(100)));
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO produse VALUES (?,?,?,?);")){
            for(Produs p : produsList){
                statement.setInt(1, p.getId());
                statement.setString(2, p.getCategorie());
                statement.setString(3, p.getNume());
                statement.setInt(4, p.getPret());
                statement.executeUpdate();
            }
        }catch (SQLException e){
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
        String s = "Create Table if not exists produse ( id int, categorie varchar(100), nume varchar(100), pret int, PRIMARY KEY (id) )";
        try {
            Statement statement = connection.createStatement();
            boolean executionResult = statement.execute(s);
            System.out.println("createTable() execution result: " + executionResult);
        } catch (SQLException e) {
            System.out.println("Eroare la crearea tabelei produse" + e);
        }
    }

    @Override
    public void add(Produs p) throws RepositoryException{
        super.add(p);
        String s = "INSERT INTO produse values (?,?,?,?)";
        try{
            PreparedStatement add_statement = connection.prepareStatement(s);
            add_statement.setInt(1, p.getId());
            add_statement.setString(2, p.getCategorie());
            add_statement.setString(3, p.getNume());
            add_statement.setInt(4, p.getPret());

            int executionResult = add_statement.executeUpdate();
            System.out.println("add() execution result: " + executionResult);
        } catch(SQLException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void remove(int id) throws RepositoryException{
        super.remove(id);
        String s = "DELETE FROM produse WHERE id = ?";
        try(PreparedStatement remove_statement = connection.prepareStatement(s)){
            remove_statement.setInt(1, id);
            remove_statement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Collection<Produs> getAll(){
        List<Produs> resultList = new ArrayList<>();
        String s = "SELECT * FROM produse";
        try(PreparedStatement getAllStatement = connection.prepareStatement(s)) {
            ResultSet result = getAllStatement.executeQuery();
            while (result.next()) {
                Produs p = new Produs(result.getInt("id"), result.getString("categorie"), result.getString("nume"), result.getInt("pret"));
                resultList.add(p);
            }
            return resultList;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}

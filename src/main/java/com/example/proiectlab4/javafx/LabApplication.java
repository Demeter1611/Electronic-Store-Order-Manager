package com.example.proiectlab4.javafx;

import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.domain.ComandaFactory;
import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.domain.ProdusFactory;
import com.example.proiectlab4.repository.*;
import com.example.proiectlab4.service.ComandaService;
import com.example.proiectlab4.service.ProdusService;
import com.example.proiectlab4.ui.Console;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LabApplication extends Application {

    private static IRepository<Produs> repoProduse;
    private static IRepository<Comanda> repoComenzi;

    public static void main(String[] args) {
        Properties config = new Properties();
        try {
            FileReader reader = new FileReader("config.properties");
            config.load(reader);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        String fileProduse = config.getProperty("Produs");
        String fileComenzi = config.getProperty("Comanda");
        switch(config.getProperty("repoType")){
            case "normal":
                repoProduse = new Repository<>();
                repoComenzi = new Repository<>();
                break;

            case "file":
                ProdusFactory produsFactory = new ProdusFactory();
                repoProduse = new FileRepository<>(fileProduse, produsFactory);
                ComandaFactory comandaFactory = new ComandaFactory(produsFactory, repoProduse);
                repoComenzi = new FileRepository<>(fileComenzi, comandaFactory);
                break;

            case "binary":
                repoProduse = new BinaryFileRepository<>(fileProduse);
                repoComenzi = new BinaryFileRepository<>(fileComenzi);
                break;
            case "sql":
                repoProduse = new SQLProdusRepository();
                repoComenzi = new SQLComandaRepository(new ComandaFactory(new ProdusFactory(), repoProduse));
                break;
            default:
                repoProduse = new Repository<>();
                repoComenzi = new Repository<>();
                break;
        }
        switch(config.getProperty("interface")) {
            case "console":
                runInConsole();
                break;
            case "gui":
                launch(args);
                break;
            default:
                launch(args);
                break;
        }
    }

    public static void runInConsole(){
        ComandaService serviceComenzi = new ComandaService(repoComenzi);
        ProdusService serviceProduse = new ProdusService(repoProduse);
        Console console = new Console(serviceComenzi, serviceProduse);
        console.run();
        repoComenzi.commit();
        repoProduse.commit();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/proiectlab4/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            MainController mainController = fxmlLoader.getController();

            ProdusController produsController = mainController.getProdusController();
            ComandaController comandaController = mainController.getComandaController();

            produsController.init();
            comandaController.init();

            produsController.setRepository(repoProduse);
            comandaController.setRepository(repoComenzi, new ComandaFactory(new ProdusFactory(), repoProduse));

            stage.setScene(scene);
            stage.setTitle("Produse si Comenzi");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

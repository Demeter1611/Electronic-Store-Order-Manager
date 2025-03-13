package com.example.proiectlab4.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController {

    public StackPane mainContent;

    private ProdusController produsController;
    private ComandaController comandaController;

    public void initialize(){
        try{
            FXMLLoader produsLoader = new FXMLLoader(getClass().getResource("/com/example/proiectlab4/produs-view.fxml"));
            VBox produseView = produsLoader.load();
            produsController = produsLoader.getController();

            FXMLLoader comandaLoader = new FXMLLoader(getClass().getResource("/com/example/proiectlab4/comanda-view.fxml"));
            VBox comandaView = comandaLoader.load();
            comandaController = comandaLoader.getController();

            mainContent.getChildren().addAll(produseView, comandaView);

            produseView.setVisible(true);
            produseView.setManaged(true);

            comandaView.setVisible(false);
            comandaView.setManaged(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void showProduseView(ActionEvent actionEvent) {
        mainContent.getChildren().get(0).setVisible(true);
        mainContent.getChildren().get(0).setManaged(true);

        mainContent.getChildren().get(1).setVisible(false);
        mainContent.getChildren().get(1).setManaged(false);
    }

    @FXML
    public void showComenziView(ActionEvent actionEvent) {
        mainContent.getChildren().get(0).setVisible(false);
        mainContent.getChildren().get(0).setManaged(false);

        mainContent.getChildren().get(1).setVisible(true);
        mainContent.getChildren().get(1).setManaged(true);
    }

    public ProdusController getProdusController() {
        return produsController;
    }
    public ComandaController getComandaController() {
        return comandaController;
    }
}

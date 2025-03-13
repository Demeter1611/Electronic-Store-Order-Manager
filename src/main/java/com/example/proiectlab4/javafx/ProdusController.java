package com.example.proiectlab4.javafx;

import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.IRepository;
import com.example.proiectlab4.repository.SQLProdusRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ProdusController {

    public ListView<Produs> listProduse;
    //public TableView<Produs> tableProduse;
    public Label labelId;
    public TextField textFieldProdusId;
    public Label labelCategorie;
    public TextField textFieldProdusCategorie;
    public Label labelNume;
    public TextField textFieldProdusNume;
    public Label labelPret;
    public TextField textFieldPret;
    public Button buttonAdd;
    public Button buttonUpdate;
    public Button buttonDelete;
    public Label labelMinWidth;
    public Slider sliderWidth;
    public Label labelMaxWidth;

    private ObservableList<Produs> dataProduse = FXCollections.observableList(new ArrayList<Produs>());

    private IRepository<Produs> repository;

    public void init(){
        listProduse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produs>() {
            @Override
            public void changed(ObservableValue<? extends Produs> observableValue, Produs produs, Produs t1) {
                ObservableList<Produs> changes = listProduse.getSelectionModel().getSelectedItems();
                Produs selectedProdus = changes.get(0);

                textFieldProdusId.setText(Integer.toString(selectedProdus.getId()));
                textFieldProdusCategorie.setText(selectedProdus.getCategorie());
                textFieldProdusNume.setText(selectedProdus.getNume());
                textFieldPret.setText(Integer.toString(selectedProdus.getId()));
            }
        });

        //TableColumn<Produs, String> columnId = new TableColumn<>("Produs id");
        //columnId.setCellValueFactory(produs -> new SimpleStringProperty(Integer.toString(produs.getValue().getId())));

        //<Produs, String> columnCategorie = new TableColumn<>("Categorie");
        //columnId.setCellValueFactory(produs -> new SimpleStringProperty(produs.getValue().getCategorie()));

        //TableColumn<Produs, String> columnNume = new TableColumn<>("Nume");
        //columnId.setCellValueFactory(produs -> new SimpleStringProperty(produs.getValue().getNume()));

        //TableColumn<Produs, String> columnPret = new TableColumn<>("Pret");
        //columnId.setCellValueFactory(produs -> new SimpleStringProperty(Integer.toString(produs.getValue().getId())));

        //tableProduse.getColumns().add(columnId);
        //tableProduse.getColumns().add(columnCategorie);
        //tableProduse.getColumns().add(columnNume);
        //tableProduse.getColumns().add(columnPret);
    }

    public void setRepository(IRepository<Produs> repository){
        this.repository = repository;
        listProduse.setItems(dataProduse);
        //tableProduse.setItems(dataProduse);

        dataProduse.addAll(repository.getAll());
    }

    public void onAddButtonClicked(ActionEvent actionEvent) {
        try{
            var produsId = Integer.parseInt(textFieldProdusId.getText());
            var categorie = textFieldProdusCategorie.getText();
            var nume = textFieldProdusNume.getText();
            var pret = Integer.parseInt(textFieldPret.getText());
            Produs produs = new Produs(produsId, categorie, nume, pret);
            repository.add(produs);
            dataProduse.add(produs);
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldProdusId.clear();
            textFieldProdusCategorie.clear();
            textFieldProdusNume.clear();
            textFieldPret.clear();
        }
    }

    public void onUpdateButtonClicked(ActionEvent actionEvent) {
        try{
            var produsId = Integer.parseInt(textFieldProdusId.getText());
            var categorie = textFieldProdusCategorie.getText();
            var nume = textFieldProdusNume.getText();
            var pret = Integer.parseInt(textFieldPret.getText());
            Produs produs = new Produs(produsId, categorie, nume, pret);
            repository.update(repository.getById(produsId), produs);
            for(int i = 0; i < dataProduse.size(); i++){
                if(dataProduse.get(i).getId() == produsId){
                    dataProduse.set(i, produs);
                }
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally{
            textFieldProdusId.clear();
            textFieldProdusCategorie.clear();
            textFieldProdusNume.clear();
            textFieldPret.clear();
        }
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        try{
            var produsId = Integer.parseInt(textFieldProdusId.getText());
            repository.remove(produsId);
            for(int i = 0; i < dataProduse.size(); i++){
                if(dataProduse.get(i).getId() == produsId){
                    dataProduse.remove(dataProduse.get(i));
                }
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldProdusId.clear();
            textFieldProdusCategorie.clear();
            textFieldProdusNume.clear();
            textFieldPret.clear();
        }
    }
}

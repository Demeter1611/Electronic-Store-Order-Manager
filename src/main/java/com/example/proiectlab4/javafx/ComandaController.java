package com.example.proiectlab4.javafx;

import com.example.proiectlab4.domain.Comanda;
import com.example.proiectlab4.domain.ComandaFactory;
import com.example.proiectlab4.domain.Produs;
import com.example.proiectlab4.repository.IRepository;
import com.example.proiectlab4.repository.SQLComandaRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComandaController {
    public ListView<Comanda> listComenzi;
    public Label labelId;
    public TextField textFieldComandaId;
    public Label labelProduse;
    public TextField textFieldComandaProduse;
    public Label labelDataLivrare;
    public TextField textFieldDataLivrare;
    public Button buttonAdd;
    public Button buttonUpdate;
    public Button buttonDelete;

    private ObservableList<Comanda> dataComenzi = FXCollections.observableList(new ArrayList<Comanda>());

    private IRepository<Comanda> repository;
    private ComandaFactory comandaFactory;

    public void init(){
        listComenzi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Comanda>() {

            @Override
            public void changed(ObservableValue<? extends Comanda> observableValue, Comanda comanda, Comanda t1) {
                ObservableList<Comanda> changes = listComenzi.getSelectionModel().getSelectedItems();
                Comanda selectedComanda = changes.get(0);

                textFieldComandaId.setText(Integer.toString(selectedComanda.getId()));
                textFieldComandaProduse.setText(comandaFactory.serializeProduse(selectedComanda.getProduse()));
                textFieldDataLivrare.setText(new SimpleDateFormat("yyyy-MM-dd").format(selectedComanda.getDataLivrare()));
            }
        });
    }

    public void setRepository(IRepository<Comanda> repository, ComandaFactory comandaFactory) {
        this.repository = repository;
        this.comandaFactory = comandaFactory;
        listComenzi.setItems(dataComenzi);

        dataComenzi.addAll(repository.getAll());
    }

    public void onAddButtonClicked(ActionEvent actionEvent) {
        try{
            var comandaId = Integer.parseInt(textFieldComandaId.getText());
            List<Produs> comandaProduse = comandaFactory.deserializeProduse(textFieldComandaProduse.getText());
            Date comandaDataLivrare = new SimpleDateFormat("yyyy-MM-dd").parse(textFieldDataLivrare.getText());
            Comanda comanda = new Comanda(comandaId, comandaProduse, comandaDataLivrare);
            repository.add(comanda);
            dataComenzi.add(comanda);
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldComandaId.clear();
            textFieldComandaProduse.clear();
            textFieldDataLivrare.clear();
        }
    }

    public void onUpdateButtonClicked(ActionEvent actionEvent) {
        try{
            var comandaId = Integer.parseInt(textFieldComandaId.getText());
            List<Produs> comandaProduse = comandaFactory.deserializeProduse(textFieldComandaProduse.getText());
            Date comandaDataLivrare = new SimpleDateFormat("yyyy-MM-dd").parse(textFieldDataLivrare.getText());
            Comanda comanda = new Comanda(comandaId, comandaProduse, comandaDataLivrare);
            repository.update(repository.getById(comandaId), comanda);
            for(int i = 0; i < dataComenzi.size(); i++){
                if(dataComenzi.get(i).getId() == comandaId){
                    dataComenzi.set(i, comanda);
                }
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldComandaId.clear();
            textFieldComandaProduse.clear();
            textFieldDataLivrare.clear();
        }
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        try {
            var comandaId = Integer.parseInt(textFieldComandaId.getText());
            repository.remove(comandaId);
            for (int i = 0; i < dataComenzi.size(); i++) {
                if (dataComenzi.get(i).getId() == comandaId) {
                    dataComenzi.remove(i);
                }
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldComandaId.clear();
            textFieldComandaProduse.clear();
            textFieldDataLivrare.clear();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jonathansuenaga
 */
public class AddItemController implements Initializable {

    @FXML
    TextField tfCodigoItem, tfNomeItem, tfPreco, tfQuantidade;
    
    @FXML
    TextArea taObservacoes;
    
    @FXML
    Button btBuscar, btAdicionar, btLimpar;

    FXMLDocumentController mainController;
    
    int row = 1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btAdicionar.setDisable(true);
    }

    public void init(FXMLDocumentController mc) {
        mainController = mc;
    }

    //busca produtos pelo codigo
    @FXML
    int buscarItem(ActionEvent event) throws FileNotFoundException, IOException {
        System.out.println("Buscando Clientes");
        CSVReader reader = new CSVReader(new FileReader("ProdutosCSV.csv"));
        String codigoItem = tfCodigoItem.getText();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            if (nextLine[0].equals(codigoItem)) {
                System.out.println("Encontrou, Item: " + nextLine[1]);
                tfNomeItem.setText(nextLine[1]);
                tfPreco.setText(nextLine[2]);
                btAdicionar.setDisable(false);
                return 1;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Erro!");
        alert.setHeaderText("Item não encontrado!");
        alert.setContentText("Deseja criar um novo item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //criaCliente();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        return 0;
    }
    
    @FXML
    public void limparCampos(ActionEvent event)
    {
       tfCodigoItem.clear();
       tfNomeItem.clear();
       tfPreco.clear();
       tfQuantidade.clear();
       taObservacoes.clear();
    }
    
    @FXML
    public int adicionarItem(ActionEvent event) {
        if(!verificaCamposObrigatorios())
        {
            System.out.println("Falta preencher!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Incompletos!");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos obrigatórios!");
            alert.showAndWait();
            return -1;
        }
        mainController.adicionaCarrinho(tfCodigoItem.getText(),tfNomeItem.getText(),tfPreco.getText(),tfQuantidade.getText(),taObservacoes.getText());
        return 0;
    }
    
    public boolean verificaCamposObrigatorios(){
        if(tfNomeItem.getText().isEmpty())  return false;
        if(tfPreco.getText().isEmpty()) return false;
        if(tfQuantidade.getText().isEmpty())    return false;
        return true;
    }
}


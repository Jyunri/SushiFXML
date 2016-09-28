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
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author jonathansuenaga
 */
public class ProdutosFXMLController implements Initializable {

    @FXML
    Label lbModoBusca;

    @FXML
    TextField tfProdutoBusca;

    @FXML
    RadioButton rbCodigo, rbNome;

    @FXML
    TextArea taResProdutos;

    ToggleGroup tg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tg = new ToggleGroup();
        rbCodigo.setToggleGroup(tg);
        rbNome.setToggleGroup(tg);
        rbCodigo.setSelected(true);

        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (tg.getSelectedToggle() != null) {

                    if (tg.getSelectedToggle() == rbNome) {
                        lbModoBusca.setText("Nome");
                    } else {
                        lbModoBusca.setText("Código");
                    }

                    // Do something here with the userData of newly selected radioButton
                }

            }
        });
    }

    @FXML
    public void buscaProduto() throws FileNotFoundException, IOException {
        System.out.println("Buscando Clientes");
        taResProdutos.clear();
        CSVReader reader = new CSVReader(new FileReader("MenuSushiTina.csv"));
        String chave = tfProdutoBusca.getText();
        String[] nextLine;
        int coluna;
        if (lbModoBusca.getText().equals("Código")) {
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(chave)) {
                    taResProdutos.appendText("Cód: " + nextLine[0] + ", Nome: " + nextLine[1] + "\n");
                }
            }
        } else {
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[1].toLowerCase().contains(chave.toLowerCase())) {
                    taResProdutos.appendText("Cód: " + nextLine[0] + ", Nome: " + nextLine[1] + "\n");
                }
            }
        }
    }

}

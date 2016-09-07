/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Jimy
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label lbCarrinho;
    
    @FXML
    private TextField tfTelefone, tfCodigoCliente, tfNome, tfEndereco;
    
    @FXML
    private Button btConfirmar;
      
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        //label.setText("Hello World!");
    }
    
    @FXML
    private void limparCampos(ActionEvent event){
        System.out.println("Limpar Campos");
        tfTelefone.clear();
        tfCodigoCliente.clear();
        tfNome.clear();
        tfEndereco.clear();
    }
            
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

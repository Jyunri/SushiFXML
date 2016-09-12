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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button btConfirmar, btAdicionarItem;
      
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
            
    @FXML
    private void adicionarItem(ActionEvent event) throws Exception{
        System.out.println("Adicionando itens");
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddItem.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

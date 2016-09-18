package sushitinafxml;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jonathansuenaga
 */
public class NovoClienteFXMLController implements Initializable {

    @FXML
    TextField tfTelefone, tfNome, tfEndereco, tfNumero, tfComplemento, tfBairro;

    @FXML
    TextArea taObservacoes;

    @FXML
    Button btCadastrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cadastrarCliente(ActionEvent event) {
        System.out.println("Cliente Criado");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cliente Criado");
        alert.setHeaderText(null);
        alert.setContentText("Finalize o pedido na aba *Pedido* ");
        alert.showAndWait();
        
        Stage stage = (Stage) btCadastrar.getScene().getWindow();
        // do what you have to do
        stage.close();
        //label.setText("Hello World!");
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        System.out.println("Limpar Campos");
        tfTelefone.clear();
        tfNome.clear();
        tfEndereco.clear();
        tfNumero.clear();
        tfComplemento.clear();
        tfBairro.clear();
        taObservacoes.clear();
    }
}

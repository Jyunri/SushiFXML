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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jimy
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label lbCarrinho, lbTeste;

    @FXML
    private TextField tfTelefone, tfResNome, tfResEndereco;

    @FXML
    private Button btNovoCliente, btConfirmar, btAdicionarItem, btTeste;

    @FXML
    private Tab tbCliente;

    @FXML
    private VBox defaultContent, vbInicio;
    
    @FXML
    private GridPane gpResultado;
    
    @FXML
    private void criaCliente() {
        System.out.println("Criando cliente");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/NovoClienteFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        System.out.println("Limpar Campos");
        tfTelefone.clear();
        //tfCodigoCliente.clear();
        tfResNome.clear();
        tfResEndereco.clear();
    }

    @FXML
    private int buscarCliente(ActionEvent event) throws FileNotFoundException, IOException {
        System.out.println("Buscando Clientes");
        CSVReader reader = new CSVReader(new FileReader("ClienteCSV.csv"));
        String telefone = tfTelefone.getText();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            if (nextLine[1].equals(telefone)) {
                gpResultado.setVisible(true);
                System.out.println("Encontrou, Nome: " + nextLine[2]);
                tfResNome.setText(nextLine[2]);
                //tfCodigoCliente.setText(nextLine[0]);
                tfResEndereco.setText(nextLine[3]);
                btConfirmar.setDisable(false);
                return 1;
            }
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Erro!");
        alert.setHeaderText("Cliente não encontrado!");
        alert.setContentText("Deseja criar um novo cliente?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            criaCliente();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        return 0;
    }

    @FXML
    private void confirmaResultado(){
        System.out.println("Confirma Resultado");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirma Resultado");
        alert.setHeaderText(null);
        alert.setContentText("Finalize o pedido na aba *Pedido* ");
        alert.showAndWait();
    }
    
    @FXML
    private void adicionarItem(ActionEvent event) throws Exception {
        System.out.println("Adicionando itens");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AddItem.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tbCliente.setContent(vbInicio);
    }

    @FXML
    void loadInicio(ActionEvent event) {
        tbCliente.setContent(vbInicio);
    }

    @FXML
    public void loadSearch(ActionEvent event) {
        //System.out.println("click");
        tbCliente.setContent(defaultContent);
        gpResultado.setVisible(false);
        btConfirmar.setDisable(true);
    }
}

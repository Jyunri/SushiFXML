package sushitinafxml;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    //Referencia ao Controller Principal
    FXMLDocumentController fm;

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

    //recebe referencia do controller pai
    public void init(FXMLDocumentController fm) {
        this.fm = fm;
    }

    @FXML
    private int cadastrarCliente(ActionEvent event) throws IOException {
        if (!verificaCamposObrigatorios()) {
            System.out.println("Falta preencher!");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Campos Incompletos!");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos obrigatórios!");
            alert.showAndWait();
            return -1;
        }

        if (telefoneExistente()) {
            System.out.println("Telefone ja existe");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText("Esse telefone ja esta cadastrado!");
            alert.showAndWait();
            return -1;
        }

        System.out.println("Cliente Criado");
        String filename = "ClienteCSV.csv";
        // feed in your array (or convert your data to an array)
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename, true))) {
            // feed in your array (or convert your data to an array)
            String codigo = String.valueOf(Files.lines(Paths.get(filename)).count() + 1);
            String telefone = tfTelefone.getText();
            String nome = tfNome.getText();
            String endereco = tfEndereco.getText();
            String numero = tfNumero.getText();
            String complemento = tfComplemento.getText();
            String bairro = tfBairro.getText();
            String obs = taObservacoes.getText();
            String[] entries = {codigo, telefone, nome, endereco, numero, complemento, bairro, obs};
            writer.writeNext(entries);
            
            fm.criaModelCliente(nome, endereco, numero, bairro);
        }
        
        fm.buscaPedido();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cliente Criado");
        alert.setHeaderText(null);
        alert.setContentText("Finalize o pedido na aba *Pedido* ");
        alert.showAndWait();

        Stage stage = (Stage) btCadastrar.getScene().getWindow();
        // do what you have to do
        stage.close();
        //label.setText("Hello World!");
        return 0;
    }

    private boolean verificaCamposObrigatorios() {
        if (tfTelefone.getText().isEmpty()) {
            return false;
        }
        if (tfNome.getText().isEmpty()) {
            return false;
        }
        if (tfEndereco.getText().isEmpty()) {
            return false;
        }
        if (tfNumero.getText().isEmpty()) {
            return false;
        }
        if (tfBairro.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean telefoneExistente() throws FileNotFoundException, IOException {
        String novoTelefone = tfTelefone.getText();
        CSVReader reader = new CSVReader(new FileReader("ClienteCSV.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            if (nextLine[1].equals(novoTelefone)) {
                return true;
            }
        }
        return false;
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

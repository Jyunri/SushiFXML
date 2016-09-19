/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sushitinafxml;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField tfTelefone;

    @FXML
    private TextField tfCodigoCliente, tfResTelefone, tfResNome, tfResEndereco, tfResNumero, tfResComp, tfResBairro;

    @FXML
    private TextArea taResObservacoes;

    @FXML
    private Button btNovoCliente, btConfirmar, btAdicionarItem, btTeste, btEditar, btFinalizar;

    @FXML
    private Tab tbCliente;

    @FXML
    private VBox defaultContent, vbInicio;

    @FXML
    private GridPane gpResultado;

    @FXML
    private TableView<Pedido> tvCarrinho;

    @FXML
    private TableColumn<Pedido, Integer> tcCodigo;
    @FXML
    private TableColumn<Pedido, String> tcDescricao;
    @FXML
    private TableColumn<Pedido, Float> tcPrecoUnitario;
    @FXML
    private TableColumn<Pedido, Integer> tcQuantidade;
    @FXML
    private TableColumn<Pedido, Float> tcPrecoFinal;
    @FXML
    private TableColumn<Pedido, String> tcObservacao;
    ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

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
                tfCodigoCliente.setText(nextLine[0]);
                tfResTelefone.setText(nextLine[1]);
                tfResNome.setText(nextLine[2]);
                tfResEndereco.setText(nextLine[3]);
                tfResNumero.setText(nextLine[4]);
                tfResComp.setText(nextLine[5]);
                tfResBairro.setText(nextLine[6]);
                taResObservacoes.setText(nextLine[7]);

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
    private int confirmaResultado() {
        System.out.println("Confirma Resultado");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirma Resultado");
        alert.setHeaderText(null);
        alert.setContentText("Finalize o pedido na aba *Pedido* ");
        alert.showAndWait();

        return 0;
    }

    private boolean verificaCamposObrigatorios() {
        if (tfResTelefone.getText().isEmpty()) {
            return false;
        }
        if (tfResNome.getText().isEmpty()) {
            return false;
        }
        if (tfResEndereco.getText().isEmpty()) {
            return false;
        }
        if (tfResNumero.getText().isEmpty()) {
            return false;
        }
        if (tfResBairro.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    //Aba Pedidos
    @FXML
    private void adicionarItem(ActionEvent event) throws Exception {
        System.out.println("Adicionando itens");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AddItem.fxml"));
            AddItemController ac = new AddItemController();
            fxmlLoader.setController(ac);
            ac.init(this);
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
        btFinalizar.setDisable(true);

        //teste
        tcCodigo.setCellValueFactory(
                new PropertyValueFactory("codigoItem")
        );
        tcDescricao.setCellValueFactory(
                new PropertyValueFactory("descricao")
        );
        tcPrecoUnitario.setCellValueFactory(
                new PropertyValueFactory("precoUnitario")
        );
        tcQuantidade.setCellValueFactory(
                new PropertyValueFactory("codigoItem")
        );
        tcPrecoFinal.setCellValueFactory(
                new PropertyValueFactory("precoFinal")
        );
        tcObservacao.setCellValueFactory(
                new PropertyValueFactory("observacao")
        );
        tvCarrinho.setItems(pedidos);

        //Aba de pedidos
//        gridCarrinho.add(new Label("Código"), 0, 0);
//        gridCarrinho.add(new Label("Descrição"), 1, 0);
//        gridCarrinho.add(new Label("Preço/unidade"), 2, 0);
//        gridCarrinho.add(new Label("Quantidade"), 3, 0);
//        gridCarrinho.add(new Label("Preço Total"), 4, 0);
//        gridCarrinho.add(new Label("Obs"), 5, 0);
    }

    //tela Inicio
    @FXML
    void loadInicio(ActionEvent event) {
        tbCliente.setContent(vbInicio);
    }

    //tela Busca
    @FXML
    public void loadSearch(ActionEvent event) {
        //System.out.println("click");
        tbCliente.setContent(defaultContent);
        gpResultado.setVisible(false);
        btFinalizar.setDisable(true);
        btEditar.setDisable(false);
        btConfirmar.setDisable(true);
        habilitarTextFields(false);
    }

    public void habilitarTextFields(boolean b) {
        tfResTelefone.setEditable(b);
        tfResNome.setEditable(b);
        tfResEndereco.setEditable(b);
        tfResNumero.setEditable(b);
        tfResComp.setEditable(b);
        tfResBairro.setEditable(b);
        taResObservacoes.setEditable(b);
    }

    @FXML
    public void editarResultados(ActionEvent event) {
        btEditar.setDisable(true);
        btFinalizar.setDisable(false);
        btConfirmar.setDisable(true);

        habilitarTextFields(true);
    }

    @FXML
    public int finalizaEdicao(ActionEvent event) throws FileNotFoundException, IOException {
        if (!verificaCamposObrigatorios()) {
            System.out.println("Falta preencher!");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Campos Incompletos!");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos obrigatórios!");
            alert.showAndWait();
            return -1;
        }

        String filename = "ClienteCSV.csv";
        CSVReader reader = new CSVReader(new FileReader(filename));
        List<String[]> clientes = reader.readAll();
        int codigo = Integer.parseInt(tfCodigoCliente.getText());

        //atualiza os valores
        clientes.get(codigo - 1)[1] = tfResTelefone.getText();

        //verifica se o telefone nao esta duplicado
        boolean telefoneAlreadyFound = false;
        for (String[] s : clientes) {
            if (s[1].equals(tfResTelefone.getText())) {
                if (!telefoneAlreadyFound) {
                    telefoneAlreadyFound = true;
                } else {
                    System.out.println("Telefone duplicado!");
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Telefone Duplicado");
                    alert.setHeaderText(null);
                    alert.setContentText("Este telefone ja esta cadastrado");
                    alert.showAndWait();
                    return -1;
                }
            }
        }

        clientes.get(codigo - 1)[2] = tfResNome.getText();
        clientes.get(codigo - 1)[3] = tfResEndereco.getText();
        clientes.get(codigo - 1)[4] = tfResNumero.getText();
        clientes.get(codigo - 1)[5] = tfResComp.getText();
        clientes.get(codigo - 1)[6] = tfResBairro.getText();
        clientes.get(codigo - 1)[7] = taResObservacoes.getText();

        //reescreve o csv com os valores atualizados
        CSVWriter writer = new CSVWriter(new FileWriter(filename));
        writer.writeAll(clientes);
        writer.flush();

        btFinalizar.setDisable(true);
        btEditar.setDisable(false);
        btConfirmar.setDisable(false);

        habilitarTextFields(false);
        return 0;
    }

    public ObservableList<Pedido> getListaPedidos() {
//        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
//        pedidos.add(new Pedido(1, "Rodizio", 30, 2, "duas"));
//        pedidos.add(new Pedido(2, "Fruta", 2, 5, "maca"));

        return pedidos;
    }

    public void adicionaCarrinho(String codigo, String descricao, String preco, String quantidade, String obs) {
          pedidos.add(new Pedido(1, "Rodizio", 30, 2, "duas"));
          pedidos.add(new Pedido(2, "Fruta", 2, 5, "maca"));
          //tvCarrinho.setItems(pedidos);
//        gridCarrinho.add(new Label(codigo), 0, row);
//        gridCarrinho.add(new Label(descricao), 1, row);
//        gridCarrinho.add(new Label(preco), 2, row);
//        gridCarrinho.add(new Label(quantidade), 3, row);
//        gridCarrinho.add(new Label(String.valueOf(Float.valueOf(preco) * Integer.valueOf(quantidade))), 4, row);
//        gridCarrinho.add(new Label(obs), 5, row);
//        gridCarrinho.add(new Button("Editar"), 6, row);
//        gridCarrinho.add(new Button("Remover"), 7, row);

    }
}

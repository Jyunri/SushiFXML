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
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author Jimy
 */
public class FXMLDocumentController implements Initializable {

    //Model declarations
    Ticket ticket;
    ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

    //View declarations
    @FXML
    private SplitPane spRoot;

    @FXML
    private TabPane tpNovoPedido;
    @FXML
    private Pane pnProduto, pnFilaPedido;

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
    private Tab tbCliente, tbPedido;

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

    @FXML
    private Button btRemover;

    @FXML
    private void criaCliente() {
        System.out.println("Criando cliente");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/NovoClienteFXML.fxml"));
            NovoClienteFXMLController nc = new NovoClienteFXMLController();
            nc.init(this);
            fxmlLoader.setController(nc);
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
        CustomUtilities.informationDialog("Confirma Resultado", false, "", "Finalize o pedido na aba *Pedido* ");
        criaModelCliente(tfResNome.getText(), tfResEndereco.getText(), tfResNumero.getText(), tfResBairro.getText());
        return 0;
    }

    public void criaModelCliente(String nome, String endereco, String numero, String bairro) {
        Cliente novoCliente = new Cliente(nome, endereco + ", " + numero + ", " + bairro);
        ticket.cliente = novoCliente;
        buscaPedido();
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

        loadBemVindo();

        tbCliente.setContent(vbInicio);
        btFinalizar.setDisable(true);

        loadTabelaPedidos();

    }

    void loadBemVindo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/BemVindoFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            spRoot.getItems().set(1, root1);

            //cria novo ticket
            ticket = new Ticket();

            //Pedidos
            pedidos.removeAll(pedidos); //limpa todos os pedidos anteriores
            tpNovoPedido.getSelectionModel().select(tbCliente); //inicia a Tab selecionando a primeira tab
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tela NovoPedido
    @FXML
    void loadInicio(ActionEvent event) {
        spRoot.getItems().set(1,tpNovoPedido);
        tbCliente.setContent(vbInicio);
    }
    
    //tela Produtos
    @FXML
    void loadProdutos(ActionEvent event) {
        spRoot.getItems().set(1, pnProduto);
    }
    
    
    //tela Fila de Pedidos
    @FXML
    void loadFilaPedidos(ActionEvent event) {
        spRoot.getItems().set(1, pnFilaPedido);
    }
    
    //inicializa tabela da aba Pedido
    void loadTabelaPedidos() {
        tvCarrinho.setEditable(true);

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
                new PropertyValueFactory("quantidade")
        );
        tcPrecoFinal.setCellValueFactory(
                new PropertyValueFactory("precoFinal")
        );
        tcObservacao.setCellValueFactory(
                new PropertyValueFactory("observacao")
        );
        tvCarrinho.setItems(pedidos);

        //Tornar Quantidade e Observacao Editavel
        tcQuantidade.setCellFactory(TextFieldTableCell.<Pedido, Integer>forTableColumn(new IntegerStringConverter()));

        tcQuantidade.setOnEditCommit(e -> {
            tvCarrinho.getItems().get(e.getTablePosition().getRow()).setQuantidade(e.getNewValue());
            tvCarrinho.getItems().get(e.getTablePosition().getRow()).updatePrecoFinal();
            tvCarrinho.refresh();
        });
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
            CustomUtilities.informationDialog("Erro!", false, "", "Preencha todos os campos obrigatórios");
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
                    CustomUtilities.informationDialog("Erro!", false, "", "Este telefone já está cadastrado");
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
        return pedidos;
    }

    public void adicionaCarrinho(String codigo, String descricao, String preco, String quantidade, String obs) {
        pedidos.add(new Pedido(Integer.valueOf(codigo), descricao, Float.valueOf(preco), Integer.valueOf(quantidade), obs));
    }

    @FXML
    public void removeCarrinho() {
        Pedido p = tvCarrinho.getSelectionModel().getSelectedItem();
        pedidos.remove(p);
        tvCarrinho.setItems(pedidos);
    }

    public void listaCarrinho() {
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    public void buscaPedido() {
        tpNovoPedido.getSelectionModel().select(tbPedido);
    }

    @FXML
    public int finalizaPedido() {
        if (ticket.cliente == null) {
            System.out.println("Selecione um cliente");
            CustomUtilities.informationDialog("Erro!", false, "", "Confirme um cliente na aba *Cliente*");
            return 0;
        }

        ticket.pedidos.clear();
        for (Pedido p : pedidos) {
            ticket.pedidos.add(p);
        }

        if (ticket.pedidos.isEmpty()) {
            System.out.println("Lista de pedidos vazia");
            CustomUtilities.informationDialog("Erro!", false, "", "Selecione pelo menos um pedido para finalizar o pedido!");

            return 0;
        }
        //TODO
        //loadCheckout();
        String clienteCheckout = "Cliente: " + ticket.cliente.nome + "\n";
        String enderecoCheckout = "Endereco: " + ticket.cliente.endereco_simplificado + "\n";
        String pedidoCheckout = ticket.listaPedidos();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Finalizar Pedido");
        alert.setHeaderText(null);
        alert.setContentText("Voce deseja gerar um ticket para o pedido abaixo?");

        TextArea textArea = new TextArea(clienteCheckout + enderecoCheckout + pedidoCheckout);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("gerando ticket para o motoboy");
            CustomUtilities.informationDialog("Ticket Gerado!", false, "", "Seu pedido foi enviado a fila!");
            for (String printer : CustomUtilities.retornaImressoras()) {
                System.out.println(printer);
            }

            loadBemVindo();
            return 1;
        }
        System.out.println("Cancelado");
        return 1;
    }

    @FXML
    public void cancelaPedido() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Pedido!");
        alert.setHeaderText(null);
        alert.setContentText("Deseja eliminar todos os dados do pedido?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            loadBemVindo();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

}

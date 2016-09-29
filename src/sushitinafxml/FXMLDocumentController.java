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
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Control;
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
    private Button btBalcao, btDelivery;

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
    private TextField tfTotal;

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
        criaModelCliente(tfResNumero.getText(), tfResNome.getText(), tfResEndereco.getText(), tfResNumero.getText(), tfResComp.getText(), tfResBairro.getText(), taResObservacoes.getText());
        return 0;
    }

    public void criaModelCliente(String telefone, String nome, String endereco, String numero, String complemento, String bairro, String observacoes) {
        //Cliente novoCliente = new Cliente(nome, endereco + ", " + numero + ", " + bairro);
        Cliente novoCliente = new Cliente(telefone, nome, endereco, numero, complemento, bairro, observacoes);

        ticket.cliente = novoCliente;
        ticket.auxNome = novoCliente.nome;
        ticket.auxEndereco = novoCliente.endereco_simplificado;
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

    @FXML
    void loadBemVindo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/BemVindoFXML.fxml"));
            BemVindoFXMLController bc = new BemVindoFXMLController();
            fxmlLoader.setController(bc);
            bc.init(this);
            Parent root1 = (Parent) fxmlLoader.load();
            spRoot.getItems().set(1, root1);

            //cria novo ticket
            ticket = new Ticket("novo cliente", "endereco");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tela NovoPedido
    @FXML
    void loadInicio(ActionEvent event) {
        //Pedidos
        pedidos.removeAll(pedidos); //limpa todos os pedidos anteriores

        if (ticket.modoAtendimento.equals("d")) {
            tpNovoPedido.getSelectionModel().select(tbCliente); //inicia a Tab selecionando a primeira tab
            tbCliente.setDisable(false);
            tbCliente.setContent(vbInicio);
        } else {
            ticket.cliente = new Cliente(ticket.modoAtendimento);
            ticket.auxNome = ticket.modoAtendimento;
            ticket.auxEndereco = "";
            tpNovoPedido.getSelectionModel().select(tbPedido); //inicia a Tab selecionando a segunda tab
            tbCliente.setDisable(true);
        }
        
        calculaTotal();
        spRoot.getItems().set(1, tpNovoPedido);
    }

    //tela Produtos
    @FXML
    void loadProdutos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ProdutosFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            spRoot.getItems().set(1, root1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tela Fila de Pedidos
    @FXML
    void loadFilaPedidos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/FilaPedidosFXML.fxml"));
            fxmlLoader.setController(SushiTinaFXML.filaPedidosFXMLController);
            Parent root1 = (Parent) fxmlLoader.load();
            spRoot.getItems().set(1, root1);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            calculaTotal();
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

    public void calculaTotal() {
        float sumTotal = 0;
        for (Pedido p : pedidos) {
            sumTotal += p.precoFinal;
        }
        tfTotal.setText(CustomUtilities.formataDecimais(sumTotal));
    }

    public void adicionaCarrinho(String codigo, String descricao, String preco, String quantidade, String obs) {
        pedidos.add(new Pedido(Integer.valueOf(codigo), descricao, Float.valueOf(preco), Integer.valueOf(quantidade), obs));
        calculaTotal();
    }

    @FXML
    public void removeCarrinho() {
        Pedido p = tvCarrinho.getSelectionModel().getSelectedItem();
        pedidos.remove(p);
        tvCarrinho.setItems(pedidos);
        calculaTotal();
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
        if (ticket.modoAtendimento.equals("d") && ticket.cliente == null) {
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

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Finalizar Pedido");
        alert.setHeaderText(null);
        alert.setContentText("Voce deseja gerar um ticket para o pedido abaixo?");

        ticket.setTimestamp(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
        ticket.setPrecoTotal(tfTotal.getText());

        TextArea textArea = new TextArea(ticket.imprimeTicket());
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
            CustomUtilities.informationDialog("Ticket Gerado!", false, "", "Seu pedido foi enviado a fila! Retire seu ticket na impressora!");
            SushiTinaFXML.filaPedidosFXMLController.tickets.add(ticket);
            for (String printer : CustomUtilities.retornaImpressoras()) {
                System.out.println(printer);
            }

            tfTotal.clear();
            loadBemVindo();
            return 1;
        }
        System.out.println("Cancelado");
        return 0;
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

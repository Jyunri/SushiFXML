/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * FXML Controller class
 *
 * @author jonathansuenaga
 */
public class FilaPedidosFXMLController implements Initializable {

    @FXML
    TableView<Ticket> tvTickets;

    @FXML
    TableColumn<Ticket, Integer> tcCodigo;
    @FXML
    TableColumn<Ticket, String> tcTimestamp, tcCliente, tcEndereco;
    @FXML
    TableColumn<Ticket, Float> tcTotal;
    @FXML
    TableColumn<Ticket, ArrayList<Pedido>> tcPedidos;

    ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tcCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        tcTimestamp.setCellValueFactory(new PropertyValueFactory("timestamp"));

        tcCliente.setCellValueFactory(new PropertyValueFactory("auxNome"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory("auxEndereco"));
        tcTotal.setCellValueFactory(new PropertyValueFactory("precoTotal"));
        tcPedidos.setCellValueFactory(new PropertyValueFactory("auxPedidos"));

        tvTickets.setItems(tickets);
        

    }

    @FXML
    public int gerarTicket() {
        Ticket t = tvTickets.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Gerando Ticket");
        alert.setHeaderText(null);
        alert.setContentText("Voce deseja gerar um ticket para o pedido abaixo?");

        TextArea textArea = new TextArea(t.imprimeTicket());
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
            CustomUtilities.informationDialog("Ticket Gerado!", false, "", "Retire seu ticket na impressora!");
            for (String printer : CustomUtilities.retornaImpressoras()) {
                System.out.println(printer);
            }
            return 1;
        }
        System.out.println("Cancelado");
        return 1;
    }

    //todo
    @FXML
    public void editaTicket() {
        Ticket t = tvTickets.getSelectionModel().getSelectedItem();
        EditarTicketFXMLController et = new EditarTicketFXMLController();
        et.init(this,t);
    }

    @FXML
    public void removeTicket() {
        Ticket t = tvTickets.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Ticket!");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente remover ticket?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            tickets.remove(t);
            tvTickets.setItems(tickets);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
}

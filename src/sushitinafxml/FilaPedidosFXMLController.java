/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
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
    TableColumn<Ticket, String> tcCliente, tcEndereco, tcPedidos;

    ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tcCliente.setCellValueFactory(new PropertyValueFactory("auxNome"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory("auxEndereco"));
        //tcPedidos.setCellValueFactory(new PropertyValueFactory("auxNome"));

        tvTickets.setItems(tickets);

    }

    @FXML
    public int gerarTicket() {
        Ticket t = tvTickets.getSelectionModel().getSelectedItem();
        t.imprimeTicket();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Finalizar Pedido");
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
            CustomUtilities.informationDialog("Ticket Gerado!", false, "", "Seu pedido foi enviado a fila!");
            for (String printer : CustomUtilities.retornaImressoras()) {
                System.out.println(printer);
            }
            return 1;
        }
        System.out.println("Cancelado");
        return 1;
    }

}

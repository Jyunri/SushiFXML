/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

}

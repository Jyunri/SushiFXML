/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jonathansuenaga
 */
public class EditarTicketFXMLController implements Initializable {
    
    FilaPedidosFXMLController fc;
    Ticket t;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void init(FilaPedidosFXMLController fc, Ticket t){
        this.fc = fc;
        this.t = t;
    }
}

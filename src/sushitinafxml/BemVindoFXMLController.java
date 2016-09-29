/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author Jimy
 */
public class BemVindoFXMLController implements Initializable {

    FXMLDocumentController fm;
    @FXML
    Button btBalcao, btDelivery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(FXMLDocumentController fm) {
        this.fm = fm;
    }

    @FXML
    public void loadInicio(ActionEvent event) {
        if (event.getSource() == btBalcao) {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Pedido no balcão");
            dialog.setHeaderText(null);
            dialog.setContentText("Insira o numero da mesa:");

// Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                fm.ticket.modoAtendimento = "Mesa " + result.get();
            }
        } else {
            System.out.println("botao delivery");
            fm.ticket.modoAtendimento = "d";
        }
        fm.loadInicio(event);
    }
}

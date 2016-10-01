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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daniel.adamis
 */
public class LoginFXMLControllerController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    TextField tfLogin, tfSenha;
    
    @FXML
    Button btLogin;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    @FXML
    private void login() throws FileNotFoundException, IOException {
        if (!checagensCampos())
            return;
        
        CSVReader tabelaLogin = new CSVReader(new FileReader("Login.csv"));
        String[] nextLine;
        while ((nextLine = tabelaLogin.readNext()) != null) {
            if (nextLine[1].equals(tfLogin.getText()) && nextLine[2].equals(tfSenha.getText())){
                Stage stage = (Stage) btLogin.getScene().getWindow();
                stage.close();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/FXMLDocument.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage.setScene(new Scene(root1));
                stage.show();
                return;
            }
        }
        CustomUtilities.informationDialog("Login", false, "", "Login ou senha incorretos");
    }
    
    @FXML
    private boolean checagensCampos() {
        if (camposPreenchidos()) {
            removeCaracsIlegais();
            return true;
        }
        CustomUtilities.informationDialog("Login", false, "", "Preencha todos os campos");
        return false;
    }
    
    @FXML
    private boolean camposPreenchidos() {
        if (tfLogin.getText().trim().isEmpty() || tfSenha.getText().trim().isEmpty())
            return false;
        return true;
    }
    
    @FXML
    private void removeCaracsIlegais() {
        tfLogin.setText(tfLogin.getText().replaceAll("[^a-zA-Z0-9]", ""));
    }
        
}

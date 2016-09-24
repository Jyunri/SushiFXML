/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import javafx.scene.control.Alert;

/**
 *
 * @author Jimy
 */
public class CustomUtilities {
    public static void informationDialog(String title, boolean hasHeader, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        if(hasHeader)   alert.setHeaderText(header);
        else    alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

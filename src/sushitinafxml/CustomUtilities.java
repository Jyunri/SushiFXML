/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javax.print.PrintService;
import javax.print.DocFlavor;
import javax.print.PrintServiceLookup;

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
    
    public static List<String> retornaImpressoras(){
        try {
            List<String> listaImpressoras = new ArrayList<>();
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;  
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);  
            for (PrintService p : ps) {  
                listaImpressoras.add(p.getName());     
            }  
            return listaImpressoras;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
    }
    
    public static String formataDecimais(float numero){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(numero);
    }
}

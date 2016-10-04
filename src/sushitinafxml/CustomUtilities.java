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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
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
        if (hasHeader) {
            alert.setHeaderText(header);
        } else {
            alert.setHeaderText(null);
        }
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static List<String> retornaImpressoras() {
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

    public static String formataDecimais(float numero) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(numero);
    }

    public static Date trim(Date date, String dateformat) {
        DateFormat format = new SimpleDateFormat(dateformat);
        Date trimmed = null;
        try {
            trimmed = format.parse(format.format(date));
        } catch (ParseException e) {
        } // will never happen
        return trimmed;
    }

    public static int getCodigoTicket() throws FileNotFoundException, IOException {
//        String filename = "Specifications.csv";
//        CSVReader reader = new CSVReader(new FileReader(filename));
//        CSVWriter writer = new CSVWriter(new FileWriter(filename, false));
//
//        reader.readNext();
//
//        int codigo;
//        String[] nextLine;
//
//        reader.readNext();
//        while ((nextLine = reader.readNext()) != null) {
//            codigo = Integer.valueOf(nextLine[0]);
//            //nextLine[0].
//        }
// 
//        return codigo;
return 0;
    }
}

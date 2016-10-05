/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Alert;
import javax.print.Doc;
import javax.print.PrintService;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

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

    public static boolean imprime(String texto) {
        PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();
        if (impressora == null) {
            informationDialog("Erro!", false, "", "Nenhuma impressora encontrada! Verifique sua impressora padrão");
        } else {
            try {
                DocPrintJob dpj = impressora.createPrintJob();
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);
                return true;
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
        return false;
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
        String filename = "Specifications.csv";
        CSVReader reader = new CSVReader(new FileReader(filename));
        int codigo;

        List<String[]> csvBody = reader.readAll();
        codigo = Integer.valueOf(csvBody.get(1)[0]);
        reader.close();

        return codigo;
    }

    public static int updateCodigoTicket() throws FileNotFoundException, IOException {
        String filename = "Specifications.csv";
        CSVReader reader = new CSVReader(new FileReader(filename));
        int codigo;

        List<String[]> csvBody = reader.readAll();
        codigo = Integer.valueOf(csvBody.get(1)[0]);
        csvBody.get(1)[0] = String.valueOf(codigo + 1);
        reader.close();

        CSVWriter writer = new CSVWriter(new FileWriter(filename, false));
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();

        return codigo;
    }
}

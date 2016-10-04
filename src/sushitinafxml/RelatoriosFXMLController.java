/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Jimy
 */
public class RelatoriosFXMLController implements Initializable {

    FXMLDocumentController fm;
    String filename = "TicketsLancados.csv";

    @FXML
    TextArea taRelatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void init(FXMLDocumentController fm) {
        this.fm = fm;
    }

    @FXML
    void lancaTickets() throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename, true))) {
            for (Ticket t : SushiTinaFXML.filaPedidosFXMLController.tickets) {
                String[] entries = {"0", t.getTimestamp(), t.getModoAtendimento(), t.getPrecoTotal(), t.getFormaPagamento()};
                writer.writeNext(entries);
            }
            CustomUtilities.informationDialog("Tickets lançados!", false, "", "Os tickets foram lançados com sucesso!");
            SushiTinaFXML.filaPedidosFXMLController.tickets.clear();
        }
    }

    @FXML
    void geraRelatorio() throws IOException, ParseException {
        CSVReader reader = new CSVReader(new FileReader(filename));
        int qtd_tickets_mensal = 0, qtd_delivery_mensal = 0, qtd_balcao_mensal = 0;
        float receita_tickets_mensal = 0, receita_delivery_mensal = 0, receita_balcao_mensal = 0;
        int qtd_tickets_diario = 0, qtd_delivery_diario = 0, qtd_balcao_diario = 0;
        float receita_tickets_diario = 0, receita_delivery_diario = 0, receita_balcao_diario = 0;

        String[] nextLine;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date today = new Date();
        today = CustomUtilities.trim(today,"dd/MM/yyyy");
        Date month = CustomUtilities.trim(today,"MM/yyyy");
        
        //desconsidera cabecalho
        reader.readNext();

        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            Date date = formatter.parse(nextLine[1]);
            date = CustomUtilities.trim(date,"dd/MM/yyyy");

            //Tratamento de tickets do dia
            if (date.compareTo(today) == 0) {
                qtd_tickets_diario++;
                receita_tickets_diario += Float.valueOf(nextLine[3]);
                if (nextLine[2].equals("d")) {
                    qtd_delivery_diario++;
                    receita_delivery_diario += Float.valueOf(nextLine[3]);

                } else {
                    qtd_balcao_diario++;
                    receita_balcao_diario += Float.valueOf(nextLine[3]);
                }
            }
            
            //Tratamento de tickets do mes
            Date date_month = CustomUtilities.trim(date,"MM/yyyy");
            if (date_month.compareTo(month) == 0) {
                qtd_tickets_mensal++;
                receita_tickets_mensal += Float.valueOf(nextLine[3]);
                if (nextLine[2].equals("d")) {
                    qtd_delivery_mensal++;
                    receita_delivery_mensal += Float.valueOf(nextLine[3]);

                } else {
                    qtd_balcao_mensal++;
                    receita_balcao_mensal += Float.valueOf(nextLine[3]);
                }
            }
        }
        
        String relatorio_diario = 
                "** Relatorio diario ("+ today +") **\n" + "\n"
                + "Quantidade de tickets total: " + qtd_tickets_diario + "\n"
                + "Quantidade de tickets delivery: " + qtd_delivery_diario + "\n"
                + "Quantidade de tickets por balcão: " + qtd_balcao_diario + "\n"
                + "Receita total: " + receita_tickets_diario + "\n"
                + "Receita por delivery: " + receita_delivery_diario + "\n"
                + "Receita por balcão: " + receita_balcao_diario;
        
        String relatorio_mensal = 
                "** Relatorio mensal + **\n" + "\n"
                + "Quantidade de tickets total: " + qtd_tickets_mensal + "\n"
                + "Quantidade de tickets delivery: " + qtd_delivery_mensal + "\n"
                + "Quantidade de tickets por balcão: " + qtd_balcao_mensal + "\n"
                + "Receita total: " + receita_tickets_mensal + "\n"
                + "Receita por delivery: " + receita_delivery_mensal + "\n"
                + "Receita por balcão: " + receita_balcao_mensal;
        
        taRelatorio.setText(
                relatorio_diario + "\n\n" + relatorio_mensal
        );
    }
}

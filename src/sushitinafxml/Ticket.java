/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathansuenaga
 */
public class Ticket {

    List<Pedido> pedidos = new ArrayList();
    Cliente cliente;

    public String listaPedidos() {
        String s = "Pedidos:\n";
        for (Pedido p : pedidos) {
            s += p.getQuantidade() + " "
               + p.getDescricao()+ ", R$"
               + p.precoUnitario + " cada, Obs:"
               + p.observacao + "\n";
        }
        return s;
    }

}

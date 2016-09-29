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

    String auxNome, auxEndereco;
    
    String modoAtendimento;
    
    String timestamp;
    
    String precoTotal;

    public String getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(String precoTotal) {
        this.precoTotal = precoTotal;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuxNome() {
        return auxNome;
    }

    public void setAuxNome(String auxNome) {
        this.auxNome = auxNome;
    }

    public String getAuxEndereco() {
        return auxEndereco;
    }

    public Ticket(String auxNome, String auxEndereco) {
        this.auxNome = auxNome;
        this.auxEndereco = auxEndereco;
    }

    public void setAuxEndereco(String auxEndereco) {
        this.auxEndereco = auxEndereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String listaPedidos() {
        String s = "Pedidos:\n";
        for (Pedido p : pedidos) {
            s += p.getQuantidade() + " "
                    + p.getDescricao() + ", R$"
                    + p.precoFinal + ", Obs:"
                    + p.observacao + "\n";
        }
        return s;
    }

    public String imprimeTicket() {
        String timestampCheckout  = "Hora do pedido: "+ this.getTimestamp() + "\n";
        String telefoneCheckout = "Telefone: " + this.cliente.telefone + "\n";
        String clienteCheckout = "Cliente: " + this.cliente.nome + "\n";
        String enderecoCheckout = "Endereco: " + this.cliente.endereco_simplificado + "\n";
        String observacaoCheckout = "Obs do cliente: " + this.cliente.observacoes + "\n";
        String pedidoCheckout = "\n\n" + this.listaPedidos();
        String totalCheckout = "\nTotal: " + this.precoTotal;
        
        String ticket = timestampCheckout + clienteCheckout;
        if(modoAtendimento.equals("d")) ticket += telefoneCheckout  + enderecoCheckout + observacaoCheckout;
        ticket += pedidoCheckout + totalCheckout;
        
        return ticket;
    }

}

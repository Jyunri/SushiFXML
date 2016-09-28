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
                    + p.precoUnitario + " cada, Obs:"
                    + p.observacao + "\n";
        }
        return s;
    }

    public String imprimeTicket() {
        String telefoneCheckout = "Telefone: " + this.cliente.telefone + "\n";
        String clienteCheckout = "Cliente: " + this.cliente.nome + "\n";
        String enderecoCheckout = "Endereco: " + this.cliente.endereco_simplificado + "\n";
        String observacaoCheckout = "Observações: " + this.cliente.observacoes + "\n";
        String pedidoCheckout = this.listaPedidos();

        String ticket = telefoneCheckout + clienteCheckout + enderecoCheckout + observacaoCheckout + pedidoCheckout;

        return ticket;
    }

}

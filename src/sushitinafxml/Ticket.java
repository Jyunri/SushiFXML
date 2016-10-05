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

    String formaPagamento, totalReceber, troco;

    public String getTotalReceber() {
        return totalReceber;
    }

    public void setTotalReceber(String totalReceber) {
        this.totalReceber = totalReceber;
    }

    int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    List<String> auxPedidos;

    public List<String> getAuxPedidos() {
        return auxPedidos;
    }

    public void setAuxPedidos(List<String> auxPedidos) {
        this.auxPedidos = auxPedidos;
    }

    public String getModoAtendimento() {
        return modoAtendimento;
    }

    public void setModoAtendimento(String modoAtendimento) {
        this.modoAtendimento = modoAtendimento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getTroco() {
        return troco;
    }

    public void setTroco(String troco) {
        this.troco = troco;
    }

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
        String codigoCheckout = "Código do Ticket: " + this.getCodigo() + "\n";
        String timestampCheckout = "Hora do pedido: " + this.getTimestamp() + "\n";
        String telefoneCheckout = "Telefone: " + this.cliente.telefone + "\n";
        String clienteCheckout = "Cliente: " + this.cliente.nome + "\n";
        String enderecoCheckout = "Endereco: " + this.cliente.endereco_simplificado + "\n";
        String observacaoCheckout = "Obs do cliente: " + this.cliente.observacoes + "\n";
        String pedidoCheckout = "\n" + this.listaPedidos();
        String formaPagamentoCheckout = "\nForma de Pagamento: " + this.formaPagamento;
        String totalCheckout = "\nTotal: " + this.precoTotal;
        String trocoCheckout = "\nValor a receber: " + (this.totalReceber) + "\nTroco: " + this.troco;;

        String ticket = codigoCheckout + timestampCheckout + clienteCheckout;
        if (modoAtendimento.equals("d")) {
            ticket += telefoneCheckout + enderecoCheckout + observacaoCheckout;
        }
        ticket += pedidoCheckout + formaPagamentoCheckout + totalCheckout + trocoCheckout;

        return ticket;
    }

    @Override
    public String toString() {
        return "Ticket{" + "pedidos=" + pedidos + ", cliente=" + cliente + ", auxNome=" + auxNome + ", auxEndereco=" + auxEndereco + ", modoAtendimento=" + modoAtendimento + ", timestamp=" + timestamp + ", precoTotal=" + precoTotal + ", formaPagamento=" + formaPagamento + ", troco=" + troco + ", auxPedidos=" + auxPedidos + '}';
    }

}

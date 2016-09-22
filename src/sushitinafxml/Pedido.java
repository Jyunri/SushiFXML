/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

/**
 *
 * @author Jimy
 */
public class Pedido {
    int codigoItem, quantidade;
    String descricao, observacao;
    float precoUnitario, precoFinal;

    public Pedido(int codigoItem, String descricao, float precoUnitario, int quantidade, String observacao) {
        this.codigoItem = codigoItem;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.observacao = observacao;
        this.precoUnitario = precoUnitario;
        this.precoFinal = precoUnitario*quantidade;
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(int codigoItem) {
        this.codigoItem = codigoItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(float precoTotal) {
        this.precoFinal = precoTotal;
    }
    
    public void updatePrecoFinal(){
        this.precoFinal = precoUnitario*quantidade;
    }

    @Override
    public String toString() {
        return "Pedido{" + "codigoItem=" + codigoItem + ", quantidade=" + quantidade + ", descricao=" + descricao + ", observacao=" + observacao + ", precoUnitario=" + precoUnitario + ", precoFinal=" + precoFinal + '}';
    }
}

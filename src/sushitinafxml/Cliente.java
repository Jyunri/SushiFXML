/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushitinafxml;

/**
 *
 * @author jonathansuenaga
 */
public class Cliente {

    String telefone, nome, endereco, numero, complemento, bairro, observacoes;
    String endereco_simplificado;

    public Cliente(String telefone, String nome, String endereco, String numero, String complemento, String bairro, String observacoes) {
        this.telefone = telefone;
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.observacoes = observacoes;
        this.endereco_simplificado  = endereco + ", " + numero + ", " + complemento + "," + bairro;
    }
    
    public Cliente(String nome, String endereco_simplificado) {
        this.nome = nome;
        this.endereco_simplificado = endereco_simplificado;
    }
}

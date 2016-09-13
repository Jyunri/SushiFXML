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
    
    private int tel,cod;
    private String nome, endereco;
    
    public Cliente(int tel, int cod, String nome, String endereco) {
        this.tel = tel;
        this.cod = cod;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
     
            
}

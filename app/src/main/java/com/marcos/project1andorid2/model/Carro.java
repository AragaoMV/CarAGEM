package com.marcos.project1andorid2.model;

public class Carro {
    private String ano, cor, marca, modelo, nomeProprietario, obs, placa;

    public Carro() {
    }

    public Carro(String ano, String cor, String marca, String modelo, String nomeProprietario, String obs, String placa) {
        this.ano = ano;
        this.cor = cor;
        this.marca = marca;
        this.modelo = modelo;
        this.nomeProprietario = nomeProprietario;
        this.obs = obs;
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.models;

/**
 *
 * @author Ivan Zambrano and Sergio Mora
 */
public class CamionDTO {

    private String placa;

    private String marca;

    private String modelo;

    private double capacidadCarga;

    private String tipoCarroceria;

    private int viajes;

    private double pesoTotalTransportado;

    public CamionDTO(){

    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public int getViajes() {
        return viajes;
    }

    public void setViajes(int viajes) {
        this.viajes = viajes;
    }

    public double getPesoTotalTransportado() {
        return pesoTotalTransportado;
    }

    public void setPesoTotalTransportado(double pesoTotalTransportado) {
        this.pesoTotalTransportado = pesoTotalTransportado;
    }
    
}

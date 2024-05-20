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
public class SolicitudDTO {
    
    private String fecha;

    private String propietarioCarga;

    private String propietarioCamion;

    private String conductorCamion;

    private String referenciaRemision;

    private String origen;

    private String destino;

    private String dimensiones;

    private double peso;

    private double valorAsegurado;

    private String empaque;

    private String estado;

    public SolicitudDTO() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPropietarioCarga() {
        return propietarioCarga;
    }

    public void setPropietarioCarga(String propietarioCarga) {
        this.propietarioCarga = propietarioCarga;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValorAsegurado() {
        return valorAsegurado;
    }

    public void setValorAsegurado(double valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }

    public String getEmpaque() {
        return empaque;
    }

    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPropietarioCamion() {
        return propietarioCamion;
    }

    public void setPropietarioCamion(String propietarioCamion) {
        this.propietarioCamion = propietarioCamion;
    }

    public String getReferenciaRemision() {
        return referenciaRemision;
    }

    public void setReferenciaRemision(String referenciaRemision) {
        this.referenciaRemision = referenciaRemision;
    }

    public String getConductorCamion() {
        return conductorCamion;
    }

    public void setConductorCamion(String conductorCamion) {
        this.conductorCamion = conductorCamion;
    }

}

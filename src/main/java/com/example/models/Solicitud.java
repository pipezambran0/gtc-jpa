/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ivan Zambrano and Sergio Mora
 */

@Entity
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;

    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;

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

    public Solicitud() {
    }

    public Solicitud(String fechaN, String propietarioCargaN, String propietarioCamionN, String conductorCamionN, String referenciaRemisionN, String origenN, String destinoN, String dimensionesN, double pesoN, double valorAseguradoN, String empaqueN, String estadoN) {
        fecha = fechaN;
        propietarioCarga = propietarioCargaN;
        propietarioCamion = propietarioCamionN;
        conductorCamion = conductorCamionN;
        referenciaRemision = referenciaRemisionN;
        origen = origenN;
        destino = destinoN;
        dimensiones = dimensionesN;
        peso = pesoN;
        valorAsegurado = valorAseguradoN;
        empaque = empaqueN;
        estado = estadoN;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }

    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

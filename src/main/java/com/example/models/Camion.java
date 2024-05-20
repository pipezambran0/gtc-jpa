/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ivan Zambrano and Sergio Mora
 */

@Entity
public class Camion implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "camion_seq") // Utilizar GenerationType.SEQUENCE con un generador específico
    //@SequenceGenerator(name = "camion_seq", sequenceName = "camion_sequence", allocationSize = 1)
    //private Long id;

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

    private String placa;

    private String marca;

    private String modelo;

    private double capacidadCarga;

    private String tipoCarroceria;

    private int viajes;

    private double pesoTotalTransportado;


    public Camion() {
    }

    public Camion(String placaN, String marcaN, String modeloN, double capacidadCargaN, String tipoCarroceriaN, int viajesN, double pesoTotalTransportadoN ) {
        placa = placaN;
        marca = marcaN;
        modelo = modeloN;
        capacidadCarga = capacidadCargaN;
        tipoCarroceria = tipoCarroceriaN;
        viajes = viajesN;
        pesoTotalTransportado = pesoTotalTransportadoN; 
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

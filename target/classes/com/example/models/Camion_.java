package com.example.models;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2024-05-19T21:14:39")
@StaticMetamodel(Camion.class)
public class Camion_ { 

    public static volatile SingularAttribute<Camion, Calendar> createdAt;
    public static volatile SingularAttribute<Camion, String> marca;
    public static volatile SingularAttribute<Camion, String> tipoCarroceria;
    public static volatile SingularAttribute<Camion, Long> id;
    public static volatile SingularAttribute<Camion, Double> capacidadCarga;
    public static volatile SingularAttribute<Camion, Integer> viajes;
    public static volatile SingularAttribute<Camion, Double> pesoTotalTransportado;
    public static volatile SingularAttribute<Camion, String> modelo;
    public static volatile SingularAttribute<Camion, Calendar> updatedAt;
    public static volatile SingularAttribute<Camion, String> placa;

}
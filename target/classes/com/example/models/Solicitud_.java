package com.example.models;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2024-05-19T21:14:39")
@StaticMetamodel(Solicitud.class)
public class Solicitud_ { 

    public static volatile SingularAttribute<Solicitud, String> dimensiones;
    public static volatile SingularAttribute<Solicitud, Double> valorAsegurado;
    public static volatile SingularAttribute<Solicitud, String> estado;
    public static volatile SingularAttribute<Solicitud, String> propietarioCamion;
    public static volatile SingularAttribute<Solicitud, Double> peso;
    public static volatile SingularAttribute<Solicitud, String> empaque;
    public static volatile SingularAttribute<Solicitud, String> origen;
    public static volatile SingularAttribute<Solicitud, String> conductorCamion;
    public static volatile SingularAttribute<Solicitud, String> propietarioCarga;
    public static volatile SingularAttribute<Solicitud, Calendar> createdAt;
    public static volatile SingularAttribute<Solicitud, String> fecha;
    public static volatile SingularAttribute<Solicitud, String> referenciaRemision;
    public static volatile SingularAttribute<Solicitud, Long> id;
    public static volatile SingularAttribute<Solicitud, String> destino;
    public static volatile SingularAttribute<Solicitud, Calendar> updatedAt;

}
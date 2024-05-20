package com.example.models;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2024-05-19T21:14:39")
@StaticMetamodel(Remision.class)
public class Remision_ { 

    public static volatile SingularAttribute<Remision, Calendar> createdAt;
    public static volatile SingularAttribute<Remision, String> ruta;
    public static volatile SingularAttribute<Remision, String> fechaRecogida;
    public static volatile SingularAttribute<Remision, Integer> valoracion;
    public static volatile SingularAttribute<Remision, Long> id;
    public static volatile SingularAttribute<Remision, String> origen;
    public static volatile SingularAttribute<Remision, String> destino;
    public static volatile SingularAttribute<Remision, String> horaRecogida;
    public static volatile SingularAttribute<Remision, String> conductor;
    public static volatile SingularAttribute<Remision, Calendar> updatedAt;
    public static volatile SingularAttribute<Remision, String> placa;

}
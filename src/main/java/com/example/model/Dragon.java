package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dragones")

public class Dragon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int intensidadFuego;
    private int resistencia;


    public Dragon() {

    }

    public Dragon(String nombre, int intensidad, int resistencia) {
        this.nombre = nombre;
        this.intensidadFuego = intensidad;
        this.resistencia = resistencia;
    }


    public void exhalar(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - intensidadFuego);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIntensidadFuego() {
        return intensidadFuego;
    }

    public void setIntensidadFuego(int intensidadFuego) {
        this.intensidadFuego = intensidadFuego;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    
    @Override
    public String toString() {
        return "Dragon [id=" + id + ", nombre=" + nombre + ", intensidadFuego=" + intensidadFuego + ", resistencia="
                + resistencia + "]";
    }


    

    
}

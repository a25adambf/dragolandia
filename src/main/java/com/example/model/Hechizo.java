package com.example.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Hechizos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Hechizo {

    @Id
    private int id;
    private String nombre;
    private String descripcion;

    @Transient
    private int nivelMagia;
    
    
    public Hechizo() {
        
    }           
    
    public Hechizo(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    public void efecto(Mago lanzador, List<Monstruo> objetivos) {

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


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "Hechizo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }

}

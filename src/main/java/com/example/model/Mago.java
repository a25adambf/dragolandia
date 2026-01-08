package com.example.model;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;




/**
 * Clase mago 
 */


@Entity
@Table (name = "magos")
public class Mago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int vida;
    private int nivelMagia;
    @ManyToAny
    private List<Hechizo> conjuros = new ArrayList<>();
    
    
    public Mago() {

    }


    public Mago(String nombre, int vida, int nivelMagia) {
        this.nombre = nombre;
        this.vida = vida;
        this.nivelMagia = nivelMagia;
    }


    public void lanzarHechizo(Monstruo monstruo) {

        monstruo.setVida(monstruo.getVida() - nivelMagia);
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


    public int getVida() {
        return vida;
    }


    public void setVida(int vida) {
            this.vida = vida;
    }


    public int getNivelMagia() {
        return nivelMagia;
    }

    public void setConjuros(List<Hechizo> conjuros) {
        this.conjuros = conjuros;
    }

    public void addConjuro(Hechizo conjuro) {
        if (conjuros == null) conjuros = new ArrayList<>();
        conjuros.add(conjuro);
    }

    public void eliminarConjuro(Hechizo conjuro) {
        conjuros.remove(conjuro);
    }

    public List<Hechizo> getConjuros() {
        return conjuros;
    }

    public void setNivelMagia(int nivelMagia) {
        this.nivelMagia = nivelMagia;
    }
}

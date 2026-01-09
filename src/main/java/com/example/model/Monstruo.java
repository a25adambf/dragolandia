package com.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monstruos")
public class Monstruo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int vida;

    @Enumerated(value = EnumType.STRING)
    private TipoMonstruo tipo;

    private int fuerza;
    
    
    public Monstruo() {
    }



    public Monstruo(String nombre, int vida, TipoMonstruo tipo, int fuerza) {
        this.nombre = nombre;
        this.vida = vida;
        this.tipo = tipo;
        this.fuerza = fuerza;
    }


    public void atacar(Mago mago) {

        mago.setVida(mago.getVida() - fuerza);
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
        if (vida < 0 ) {
            this.vida = 0;
        } else
            this.vida = vida;
    }


    public TipoMonstruo getTipo() {
        return tipo;
    }


    public void setTipo(TipoMonstruo tipo) {
        this.tipo = tipo;
    }


    public int getFuerza() {
        return fuerza;
    }


    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }



    @Override
    public String toString() {
        return "Monstruo [id=" + id + ", nombre=" + nombre + ", vida=" + vida + ", tipo=" + tipo + ", fuerza=" + fuerza
                + "]";
    }

}

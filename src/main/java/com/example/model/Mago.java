package com.example.model;

import java.util.List;


import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;




/**
 * Clase Mago - Representa un mago en el juego Dragolandia.
 * 
 * Un mago es un personaje jugable que puede:
 * - Lanzar hechizos contra los monstruos
 * - Aprender y usar diferentes tipos de hechizos
 * - Invocar al dragón
 * - Recibir daño del monstruo jefe
 * - Morir si su vida llega a 0
 * 
 * Atributos:
 * - id: Identificador único en la BD
 * - nombre: Nombre del mago
 * - vida: Puntos de vida actual (máximo 200)
 * - nivelMagia: Nivel de poder mágico que determina el daño infligido
 * - conjuros: Lista de hechizos que el mago ha aprendido
 * 
 * @see Hechizo
 * @see Monstruo
 */


@Entity
@Table (name = "magos")
public class Mago {
    
    /** Identificador único del mago */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Nombre del mago */
    private String nombre;
    
    /** Puntos de vida actuales del mago */
    private int vida;
    
    /** Nivel de poder mágico que afecta el daño de los hechizos */
    private int nivelMagia;
    
    /** Hechizos que el mago ha aprendido y puede usar */
    @ManyToMany
    private List<Hechizo> conjuros = new ArrayList<>();
    
    
    /**
     * Constructor por defecto de la clase Mago.
     * Requerido por Hibernate para la persistencia.
     */
    public Mago() {

    }

    /**
     * Constructor de la clase Mago.
     * 
     * @param nombre El nombre del mago
     * @param vida Los puntos de vida iniciales
     * @param nivelMagia El nivel de poder mágico del mago
     */
    public Mago(String nombre, int vida, int nivelMagia) {
        this.nombre = nombre;
        this.vida = vida;
        this.nivelMagia = nivelMagia;
    }

    /**
     * Lanza un hechizo contra un monstruo.
     * 
     * El daño infligido es igual al nivel de magia del mago.
     * Este método se llama después de verificar que el mago
     * conoce el hechizo.
     * 
     * @param monstruo El monstruo que recibe el daño
     */
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
        if (vida < 0 ) {
            this.vida = 0;
        } else
            this.vida = vida;    }


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
        if (nivelMagia < 0 ) {
            this.nivelMagia = 0;
        } else
            this.nivelMagia = nivelMagia;    
        }
}

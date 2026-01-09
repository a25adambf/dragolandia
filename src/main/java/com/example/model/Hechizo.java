package com.example.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Clase abstracta Hechizo - Representa un hechizo en el mundo de Dragolandia.
 * 
 * Esta es la clase base para todos los tipos de hechizos que pueden lanzar los magos.
 * Utiliza herencia de tabla única (SINGLE_TABLE) en Hibernate para gestionar
 * los diferentes tipos de hechizos (BolaFuego, BolaNieve, Rayo, AtaqueBasico).
 * 
 * Atributos:
 * - id: Identificador único del hechizo
 * - nombre: Nombre del hechizo (ej: "Bola de Fuego", "Bola de Nieve")
 * - descripcion: Descripción de los efectos del hechizo
 * - nivelMagia: Nivel de poder mágico (transient, no persistido en BD)
 * 
 * Subclases:
 * - BolaFuego: Hechizo de fuego con daño elevado
 * - BolaNieve: Hechizo de hielo con daño medio
 * - Rayo: Hechizo eléctrico con daño variable
 * - AtaqueBasico: Ataque mágico simple
 * 
 * @see BolaFuego
 * @see BolaNieve
 * @see Rayo
 * @see AtaqueBasico
 * @see Mago
 */
@Entity
@Table(name = "Hechizos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Hechizo {

    /** Identificador único del hechizo */
    @Id
    private int id;
    
    /** Nombre del hechizo */
    private String nombre;
    
    /** Descripción de los efectos del hechizo */
    private String descripcion;
    
    /**
     * Constructor por defecto de la clase Hechizo.
     * Requerido por Hibernate para la persistencia.
     */
    public Hechizo() {
        
    }           
    
    /**
     * Constructor parametrizado de la clase Hechizo.
     * 
     * @param id El identificador único del hechizo
     * @param nombre El nombre del hechizo
     * @param descripcion La descripción de los efectos del hechizo
     */
    public Hechizo(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    /**
     * Método abstracto que aplica el efecto del hechizo.
     * 
     * Cada subclase implementa su propio efecto:
     * - BolaFuego: daño de fuego a objetivo único
     * - BolaNieve: daño de hielo reducido a múltiples objetivos
     * - Rayo: daño variable a objetivo único
     * - AtaqueBasico: daño básico a objetivo único
     * 
     * @param lanzador El mago que lanza el hechizo
     * @param objetivos La lista de monstruos objetivo del hechizo
     */
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {

    }

    /**
     * Obtiene el identificador del hechizo.
     * 
     * @return El ID único del hechizo en la BD
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del hechizo.
     * 
     * @param id El nuevo ID del hechizo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del hechizo.
     * 
     * @return El nombre del hechizo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del hechizo.
     * 
     * @param nombre El nuevo nombre del hechizo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del hechizo.
     * 
     * @return La descripción de los efectos del hechizo
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del hechizo.
     * 
     * @param descripcion La nueva descripción del hechizo
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Representación en string del hechizo.
     * 
     * Muestra el ID, nombre y descripción del hechizo.
     * 
     * @return String con la información del hechizo
     */
    @Override
    public String toString() {
        return "Hechizo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }

}

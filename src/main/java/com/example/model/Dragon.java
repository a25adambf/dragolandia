package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase Dragon - Representa el dragón aliado en el juego Dragolandia.
 * 
 * El dragón es un personaje especial que:
 * - Puede ser invocado por los magos (con 50% de probabilidad)
 * - Ataca solo al monstruo jefe cuando aparece
 * - No puede morir durante el juego
 * - Ayuda a los magos a derrotar a los monstruos
 * 
 * Atributos:
 * - id: Identificador único en la BD
 * - nombre: Nombre del dragón
 * - intensidadFuego: Poder de ataque del dragón
 * - resistencia: Puntos de resistencia (no se afecta en el juego actual)
 * 
 * @see Monstruo
 * @see Partida
 */
@Entity
@Table(name = "dragones")

public class Dragon {
    
    /** Identificador único del dragón */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Nombre del dragón */
    private String nombre;
    
    /** Poder de ataque (daño que inflige) */
    private int intensidadFuego;
    
    /** Puntos de resistencia del dragón */
    private int resistencia;

    /**
     * Constructor por defecto de la clase Dragon.
     * Requerido por Hibernate para la persistencia.
     */
    public Dragon() {

    }

    /**
     * Constructor de la clase Dragon.
     * 
     * @param nombre El nombre del dragón
     * @param intensidad El poder de ataque del dragón
     * @param resistencia Los puntos de resistencia del dragón
     */
    public Dragon(String nombre, int intensidad, int resistencia) {
        this.nombre = nombre;
        this.intensidadFuego = intensidad;
        this.resistencia = resistencia;
    }

    /**
     * Exhala fuego contra un monstruo infligiendo daño.
     * 
     * Este método es llamado cuando el dragón ataca al monstruo jefe.
     * El daño infligido es igual a la intensidad de fuego del dragón.
     * 
     * @param monstruo El monstruo que recibe el ataque
     */
    public void exhalar(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - intensidadFuego);
    }

    /**
     * Obtiene el identificador del dragón.
     * 
     * @return El ID único del dragón
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del dragón.
     * 
     * @param id El identificador único del dragón
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del dragón.
     * 
     * @return El nombre del dragón
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del dragón.
     * 
     * @param nombre El nuevo nombre del dragón
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la intensidad de fuego del dragón.
     * 
     * @return El poder de ataque del dragón
     */
    public int getIntensidadFuego() {
        return intensidadFuego;
    }

    /**
     * Establece la intensidad de fuego del dragón.
     * 
     * @param intensidadFuego El nuevo poder de ataque
     */
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

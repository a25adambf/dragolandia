package com.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase Monstruo - Representa un monstruo en el juego Dragolandia.
 * 
 * Un monstruo es una entidad enemiga que:
 * - Habita en el bosque
 * - Ataca a los magos durante el juego
 * - Puede ser derrotado por hechizos de los magos o ataques del dragón
 * - Puede ser promovido a monstruo jefe si el jefe actual muere
 * 
 * Atributos:
 * - id: Identificador único en la BD
 * - nombre: Nombre del monstruo
 * - vida: Puntos de vida actuales
 * - tipo: Tipo de monstruo (Ogro, Troll, Espectro)
 * - fuerza: Nivel de daño que inflige al atacar
 * 
 * @see TipoMonstruo
 * @see Bosque
 * @see Mago
 */
@Entity
@Table(name = "monstruos")
public class Monstruo {

    /** Identificador único del monstruo */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Nombre del monstruo */
    private String nombre;
    
    /** Puntos de vida actuales del monstruo */
    private int vida;

    /** Tipo de monstruo que determina sus características */
    @Enumerated(value = EnumType.STRING)
    private TipoMonstruo tipo;

    /** Fuerza (daño) que inflige cuando ataca a un mago */
    private int fuerza;
    
    /**
     * Constructor por defecto de la clase Monstruo.
     * Requerido por Hibernate para la persistencia.
     */
    public Monstruo() {
    }

    /**
     * Constructor de la clase Monstruo.
     * 
     * @param nombre El nombre del monstruo
     * @param vida Los puntos de vida iniciales
     * @param tipo El tipo de monstruo (Ogro, Troll, Espectro)
     * @param fuerza El nivel de daño al atacar
     */
    public Monstruo(String nombre, int vida, TipoMonstruo tipo, int fuerza) {
        this.nombre = nombre;
        this.vida = vida;
        this.tipo = tipo;
        this.fuerza = fuerza;
    }

    /**
     * Ataca a un mago infligiendo daño basado en su fuerza.
     * 
     * El daño infligido es igual al valor de fuerza del monstruo.
     * Si la vida del mago llega a 0 o menos, será eliminado del juego.
     * 
     * @param mago El mago que recibe el ataque
     */
    public void atacar(Mago mago) {

        mago.setVida(mago.getVida() - fuerza);
    }

    /**
     * Establece el identificador del monstruo.
     * 
     * @param id El identificador único del monstruo
     */
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

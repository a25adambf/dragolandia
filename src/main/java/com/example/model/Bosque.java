package com.example.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Clase Bosque - Representa el bosque donde ocurre el juego en Dragolandia.
 * 
 * El bosque es el escenario principal que contiene:
 * - Un monstruo jefe (el principal antagonista)
 * - Una lista de monstruos que habitan en él
 * - Un dragón aliado que ayuda a los magos
 * 
 * Atributos:
 * - id: Identificador único en la BD
 * - nombre: Nombre del bosque
 * - nivelPeligro: Nivel de dificultad/peligro del bosque
 * - monstruoJefe: El monstruo más poderoso que ataca a los magos
 * - monstruos: Lista de todos los monstruos del bosque
 * - dragon: El dragón aliado de los magos
 * 
 * @see Monstruo
 * @see Dragon
 * @see Partida
 */
@Entity
@Table(name = "bosques")
public class Bosque {

    /** Identificador único del bosque */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Nombre del bosque */
    private String nombre;
    
    /** Nivel de peligro/dificultad del bosque */
    private int nivelPeligro;

    /** Monstruo jefe del bosque (el más poderoso) */
    @OneToOne (targetEntity = Monstruo.class)
    private Monstruo monstruoJefe;

    /** Lista de monstruos que habitan en el bosque */
    @OneToMany (targetEntity = Monstruo.class)
    private List<Monstruo> monstruos;

    /** Dragón aliado del bosque */
    @OneToOne (targetEntity = Dragon.class)
    private Dragon dragon;
    
    /**
     * Constructor por defecto de la clase Bosque.
     * Requerido por Hibernate para la persistencia.
     */
    public Bosque(){
        
    }
    
    /**
     * Constructor de la clase Bosque.
     * 
     * @param nombre El nombre del bosque
     * @param nivelPeligro El nivel de dificultad del bosque
     * @param dragon El dragón aliado del bosque
     */
    public Bosque(String nombre, int nivelPeligro, Dragon dragon) {
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.dragon = dragon;
    }

    /**
     * Muestra información del monstruo jefe.
     * 
     * @deprecated Usar getMonstruoJefe().toString() en su lugar
     */
    public void mostrarJefe() {
        System.out.println(monstruoJefe.toString());
    }

    /**
     * Cambia el monstruo jefe del bosque.
     * 
     * Se utiliza cuando el jefe actual es derrotado,
     * para asignar un nuevo jefe de los monstruos restantes.
     * 
     * @param jefeNuevo El nuevo monstruo jefe
     */
    public void cambiarJefe(Monstruo jefeNuevo) {
        monstruoJefe = jefeNuevo;
    }

    /**
     * Obtiene el nombre del bosque.
     * 
     * @return El nombre del bosque
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del bosque.
     * 
     * @param nombre El nuevo nombre del bosque
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nivel de peligro del bosque.
     * 
     * @return El nivel de peligro (dificultad)
     */
    public int getNivelPeligro() {
        return nivelPeligro;
    }

    /**
     * Establece el nivel de peligro del bosque.
     * 
     * @param nivelPeligro El nuevo nivel de peligro
     */
    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }


    /**
     * Obtiene el monstruo jefe del bosque.
     * 
     * El monstruo jefe es quien ataca a los magos durante el combate.
     * 
     * @return El monstruo jefe del bosque
     */
    public Monstruo getMonstruoJefe() {
        return monstruoJefe;
    }

    /**
     * Establece el monstruo jefe del bosque.
     * 
     * @param monstruoJefe El nuevo monstruo jefe
     */
    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    /**
     * Obtiene el identificador único del bosque.
     * 
     * @return El ID del bosque en la BD
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la lista de todos los monstruos del bosque.
     * 
     * @return Lista de monstruos que habitan el bosque
     */
    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    /**
     * Establece la lista de monstruos del bosque.
     * 
     * @param monstruos La nueva lista de monstruos
     */
    public void setMonstruos(List<Monstruo> monstruos) {
        this.monstruos = monstruos;
    }

    /**
     * Añade un monstruo a la lista del bosque.
     * 
     * @param monstruo El monstruo a agregar
     * @return true si se agregó correctamente, false en caso contrario
     */
    public boolean addMonstruo(Monstruo monstruo) {
        return monstruos.add(monstruo);
    }

    /**
     * Elimina un monstruo de la lista del bosque.
     * 
     * @param monstruo El monstruo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean removeMonstruo(Monstruo monstruo) {
        
        return monstruos.remove(monstruo);
    }

    /**
     * Obtiene el dragón aliado del bosque.
     * 
     * @return El dragón que habita el bosque
     */
    public Dragon getDragon() {
        return dragon;
    }

    /**
     * Establece el dragón aliado del bosque.
     * 
     * @param dragon El nuevo dragón del bosque
     */
    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    /**
     * Representación en string del bosque.
     * 
     * Muestra todos los atributos incluyendo id, nombre, nivel de peligro,
     * monstruo jefe, lista de monstruos y dragón.
     * 
     * @return String con la información completa del bosque
     */
    @Override
    public String toString() {
        return "Bosque [id=" + id + ", nombre=" + nombre + ", nivelPeligro=" + nivelPeligro + ", monstruoJefe="
                + monstruoJefe + ", monstruos=" + monstruos + ", dragon=" + dragon + "]";
    }

    
    
    
}

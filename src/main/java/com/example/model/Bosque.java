package com.example.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bosques")
public class Bosque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int nivelPeligro;

    @OneToOne (targetEntity = Monstruo.class)
    private Monstruo monstruoJefe;

    @OneToMany (targetEntity = Monstruo.class)
    private List<Monstruo> monstruos;

    @OneToOne (targetEntity = Dragon.class)
    private Dragon dragon;
    
    public Bosque(){
        
    }
    
    public Bosque(String nombre, int nivelPeligro, Monstruo monstruoJefe) {
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.monstruoJefe = monstruoJefe;
    }

    public void mostrarJefe() {
        System.out.println(monstruoJefe.toString());
    }

    public void cambiarJefe(Monstruo jefeNuevo) {
        monstruoJefe = jefeNuevo;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getNivelPeligro() {
        return nivelPeligro;
    }


    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }


    public Monstruo getMonstruoJefe() {
        return monstruoJefe;
    }


    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    public int getId() {
        return id;
    }

    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    public void setMonstruos(List<Monstruo> monstruos) {
        this.monstruos = monstruos;
    }

    public boolean addMonstruo(Monstruo monstruo) {
        return monstruos.add(monstruo);
    }

    public boolean removeMonstruo(Monstruo monstruo) {
        
        return monstruos.remove(monstruo);
    }

    public Dragon getDragon() {
        return dragon;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    



    @Override
    public String toString() {
        return "Bosque [id=" + id + ", nombre=" + nombre + ", nivelPeligro=" + nivelPeligro + ", monstruoJefe="
                + monstruoJefe + ", monstruos=" + monstruos + ", dragon=" + dragon + "]";
    }

    
    
    
}

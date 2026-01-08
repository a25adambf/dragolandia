package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Rayo")
public class Rayo extends Hechizo {

    public Rayo() {
        super(3, "Rayo", "Invoca un rayo que daña a un enemigo.");
    }

    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        
        if (objetivos.size() < 2 && objetivos.size() > 0) {
            for (Monstruo monstruo : objetivos) {
                monstruo.setVida(monstruo.getVida() - 10*lanzador.getNivelMagia());
            }
        }
    }
}

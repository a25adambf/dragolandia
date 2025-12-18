package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Bola de fuego")
public class BolaFuego extends Hechizo {

    public BolaFuego() {
        super(1, "Bola de fuego", "Una gran bola de fuego que puede dañar a varios monstruos, el daño se reparte entre los objetivos afectados");
    }

    
    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {

        for (Monstruo monstruo : objetivos) {
            monstruo.setVida(monstruo.getVida() - (20 * lanzador.getNivelMagia())/objetivos.size());
        }

    }

    
}

package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue(value = "Ataque Básico")
public class AtaqueBasico extends Hechizo {
 
    
    public AtaqueBasico() {
        super(4, "Ataque Básico", "Haz de luz mágico que daña al enemigo en el que impacta.");
    }

    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        
        if (objetivos.size() < 2 && objetivos.size() > 0) {
            for (Monstruo monstruo : objetivos) {
                monstruo.setVida(monstruo.getVida() - 2*lanzador.getNivelMagia());
            }
        }
    }
}

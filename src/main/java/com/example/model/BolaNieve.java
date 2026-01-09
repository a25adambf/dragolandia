package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Clase BolaNieve - Hechizo de hielo que congela y mata al enemigo.
 * 
 * BolaNieve es un hechizo de ataque poderoso que lanza una bola de nieve
 * capaz de congelar y matar instantáneamente al monstruo impactado.
 * 
 * Solo funciona contra un único objetivo:
 * - Si hay exactamente 1 objetivo: congela y mata (vida = 0)
 * - Si hay 2 o más objetivos: el hechizo falla (no causa daño)
 * - Si no hay objetivos: no hace nada
 * 
 * Atributos heredados:
 * - id: 2
 * - nombre: "Bola de nieve"
 * - descripcion: "Una bola de nieve que congela al enemigo impactado matándolo."
 * 
 * Estrategia: Este hechizo es muy poderoso pero tiene limitación:
 * solo afecta a un enemigo. Es útil para acabar monstruos débiles rápidamente.
 * 
 * @see Hechizo
 * @see Mago
 * @see Monstruo
 */
@Entity
@DiscriminatorValue(value = "Bola de nieve")
public class BolaNieve extends Hechizo {
    
    /**
     * Constructor de BolaNieve.
     * 
     * Inicializa el hechizo con ID 2, nombre "Bola de nieve"
     * y descripción del efecto congelador instantáneo.
     */
    public BolaNieve() {
            super(2,"Bola de nieve", "Una bola de nieve que congela al enemigo impactado matándolo.");
        }

    /**
     * Aplica el efecto del hechizo BolaNieve.
     * 
     * El efecto solo funciona si hay exactamente UN objetivo.
     * En ese caso, mata instantáneamente al monstruo estableciendo su vida a 0.
     * Si hay múltiples objetivos, el hechizo no causa daño.
     * 
     * @param lanzador El mago que lanza la bola de nieve
     * @param objetivos Lista de monstruos objetivo (debe tener 1 para que funcione)
     */
    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        
        if (objetivos.size() < 2 && objetivos.size() > 0) {
            for (Monstruo monstruo : objetivos) {
                monstruo.setVida(0);
            }
        }
    }
}

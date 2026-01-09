package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Clase Rayo - Hechizo eléctrico de daño variable a un único objetivo.
 * 
 * Rayo es un hechizo de ataque que invoca un rayo eléctrico
 * capaz de infligir daño a un monstruo enemigo.
 * 
 * Características del daño:
 * - Solo afecta a UN objetivo (múltiples objetivos causan fallo)
 * - Fórmula de daño: 10 * nivelMagia del lanzador
 * - El daño es constante sin distribución entre objetivos
 * 
 * Atributos heredados:
 * - id: 3
 * - nombre: "Rayo"
 * - descripcion: "Invoca un rayo que daña a un enemigo."
 * 
 * Ejemplo: Un mago con nivelMagia=5 lanzando Rayo causará 10*5 = 50 puntos de daño.
 * 
 * Nota: Similar a BolaNieve, solo funciona contra un objetivo.
 * Si se lanza contra múltiples objetivos, el hechizo falla.
 * 
 * @see Hechizo
 * @see Mago
 * @see Monstruo
 */
@Entity
@DiscriminatorValue(value = "Rayo")
public class Rayo extends Hechizo {

    /**
     * Constructor de Rayo.
     * 
     * Inicializa el hechizo con ID 3, nombre "Rayo"
     * y descripción del efecto eléctrico.
     */
    public Rayo() {
        super(3, "Rayo", "Invoca un rayo que daña a un enemigo.");
    }

    /**
     * Aplica el efecto del hechizo Rayo.
     * 
     * El efecto solo funciona si hay exactamente UN objetivo.
     * En ese caso, aplica daño eléctrico según la fórmula: 10 * nivelMagia.
     * Si hay múltiples objetivos, el hechizo no causa daño.
     * 
     * @param lanzador El mago que lanza el rayo
     * @param objetivos Lista de monstruos objetivo (debe tener 1 para que funcione)
     */
    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        
        if (objetivos.size() < 2 && objetivos.size() > 0) {
            for (Monstruo monstruo : objetivos) {
                monstruo.setVida(monstruo.getVida() - 10*lanzador.getNivelMagia());
            }
        }
    }
}

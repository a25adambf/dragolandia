package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Clase AtaqueBasico - Hechizo de daño mínimo a un único objetivo.
 * 
 * AtaqueBasico es el hechizo más simple y débil disponible para los magos.
 * Lanza un haz de luz mágico que causa daño mínimo a un monstruo enemigo.
 * 
 * Características del daño:
 * - Solo afecta a UN objetivo (múltiples objetivos causan fallo)
 * - Fórmula de daño: 2 * nivelMagia del lanzador
 * - El daño es muy bajo pero confiable
 * 
 * Atributos heredados:
 * - id: 4
 * - nombre: "Ataque Básico"
 * - descripcion: "Haz de luz mágico que daña al enemigo en el que impacta."
 * 
 * Ejemplo: Un mago con nivelMagia=5 lanzando AtaqueBasico causará 2*5 = 10 puntos de daño.
 * 
 * Propósito estratégico: Este hechizo es muy débil pero se usa como alternativa
 * cuando otros hechizos fallan o en situaciones donde se conoce el hechizo
 * pero se desea conservar energía mágica.
 * 
 * @see Hechizo
 * @see Mago
 * @see Monstruo
 */
@Entity
@DiscriminatorValue(value = "Ataque Básico")
public class AtaqueBasico extends Hechizo {
 
    /**
     * Constructor de AtaqueBasico.
     * 
     * Inicializa el hechizo con ID 4, nombre "Ataque Básico"
     * y descripción del efecto de luz mágica.
     */
    public AtaqueBasico() {
        super(4, "Ataque Básico", "Haz de luz mágico que daña al enemigo en el que impacta.");
    }

    /**
     * Aplica el efecto del hechizo AtaqueBasico.
     * 
     * El efecto solo funciona si hay exactamente UN objetivo.
     * En ese caso, aplica daño mínimo según la fórmula: 2 * nivelMagia.
     * Si hay múltiples objetivos, el hechizo no causa daño.
     * 
     * @param lanzador El mago que lanza el ataque básico
     * @param objetivos Lista de monstruos objetivo (debe tener 1 para que funcione)
     */
    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        
        if (objetivos.size() < 2 && objetivos.size() > 0) {
            for (Monstruo monstruo : objetivos) {
                monstruo.setVida(monstruo.getVida() - 2*lanzador.getNivelMagia());
            }
        }
    }
}

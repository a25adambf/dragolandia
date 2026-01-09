package com.example.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Clase BolaFuego - Hechizo de fuego que daña a múltiples objetivos.
 * 
 * BolaFuego es un hechizo de ataque que lanza una gran bola de fuego
 * capaz de dañar a varios monstruos simultáneamente.
 * El daño se reparte equitativamente entre todos los objetivos afectados.
 * 
 * Fórmula de daño:
 * Daño por objetivo = (20 * nivelMagia del lanzador) / número de objetivos
 * 
 * Atributos heredados:
 * - id: 1
 * - nombre: "Bola de fuego"
 * - descripcion: "Una gran bola de fuego que puede dañar a varios monstruos..."
 * 
 * Ejemplo: Un mago con nivelMagia=5 lanzando BolaFuego contra 2 monstruos
 * causará (20*5)/2 = 50 puntos de daño a cada uno.
 * 
 * @see Hechizo
 * @see Mago
 * @see Monstruo
 */
@Entity
@DiscriminatorValue(value = "Bola de fuego")
public class BolaFuego extends Hechizo {

    /**
     * Constructor de BolaFuego.
     * 
     * Inicializa el hechizo con ID 1, nombre "Bola de fuego"
     * y descripción del efecto de daño múltiple.
     */
    public BolaFuego() {
        super(1, "Bola de fuego", "Una gran bola de fuego que puede dañar a varios monstruos, el daño se reparte entre los objetivos afectados");
    }

    /**
     * Aplica el efecto del hechizo BolaFuego.
     * 
     * El efecto reduce la vida de cada monstruo objetivo
     * aplicando la fórmula: (20 * nivelMagia) / número de objetivos
     * 
     * @param lanzador El mago que lanza la bola de fuego
     * @param objetivos Lista de monstruos que sufrirán el daño
     */
    @Override
    public void efecto(Mago lanzador, List<Monstruo> objetivos) {
        for (Monstruo monstruo : objetivos) {
            monstruo.setVida(monstruo.getVida() - (20 * lanzador.getNivelMagia())/objetivos.size());
        }
    }
}

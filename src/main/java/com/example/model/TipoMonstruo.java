package com.example.model;

/**
 * Enum TipoMonstruo - Define los tipos de monstruos disponibles en Dragolandia.
 * 
 * Este enumerado categoriza los diferentes tipos de enemigos que pueden
 * encontrarse en el bosque. Cada tipo representa una clasificación única
 * de monstruo con sus propias características (aunque actualmente no se
 * diferencia el comportamiento por tipo en el juego).
 * 
 * Valores:
 * - OGRO: Monstruo humanoide fuerte y lento
 * - TROLL: Monstruo regenerador con dureza media
 * - ESPECTRO: Monstruo etéreo y especial
 * 
 * Uso: Se asigna a cada Monstruo en el momento de su creación
 * para identificar y clasificar los enemigos del bosque.
 * 
 * @see Monstruo
 * @see Bosque
 */
public enum TipoMonstruo {

    /** Monstruo tipo ogro - humanoides grandes y fuertes */
    ogro,
    
    /** Monstruo tipo troll - criaturas regeneradoras */
    troll,
    
    /** Monstruo tipo espectro - entidades etéreas y sobrenaturales */
    espectro

}

package com.example.view;

/**
 * Clase principal que inicia el juego Dragolandia.
 * 
 * Esta clase es el punto de entrada de la aplicación. Se encarga de:
 * - Mostrar un mensaje de bienvenida al jugador
 * - Crear una nueva instancia de Partida
 * - Iniciar el juego
 * 
 */
public class Juego {

    /**
     * Método principal que inicia la ejecución del juego.
     * 
     * Muestra un banner de bienvenida y crea una nueva partida que es iniciada
     * inmediatamente. El control del juego pasa completamente a la clase Partida.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       ¡BIENVENIDO A DRAGOLANDIA!       ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // Crear e iniciar una nueva partida
        Partida partida = new Partida();
        partida.iniciarJuego();
    }


}

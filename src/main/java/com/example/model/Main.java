package com.example.model;


import com.example.controller.ControllerMago;
import com.example.controller.ControllerHechizos;

public class Main {
    

    public static void main(String[] args) {

        // Inicializar controladores
        ControllerMago cm = new ControllerMago();
        ControllerHechizos ch = new ControllerHechizos();

        // Inicializar las instancias únicas de hechizos
        // Esto garantiza que exista una sola instancia de cada hechizo en BD y en memoria
        ch.inicializarHechizos();

        // Obtener las instancias únicas
        BolaFuego bolaFuego = ch.obtenerBolaFuego();
        BolaNieve bolaNieve = ch.obtenerBolaNieve();
        Rayo rayo = ch.obtenerRayo();
        AtaqueBasico ataqueBasico = ch.obtenerAtaqueBasico();

        System.out.println("Hechizo BolaFuego: " + bolaFuego.getNombre() + " (ID: " + bolaFuego.getId() + ")");
        System.out.println("Hechizo BolaNieve: " + bolaNieve.getNombre() + " (ID: " + bolaNieve.getId() + ")");
        System.out.println("Hechizo Rayo: " + rayo.getNombre() + " (ID: " + rayo.getId() + ")");

        // Guardar un mago
        cm.guardarMago("mago", 40, 5);

        // Ejemplo de modificación de hechizo único
        ch.modificarDescripcion("Una bola de fuego más potente", bolaFuego.getId());

        // Demostración de gestión de hechizos del mago
        System.out.println("\n--- Gestión de Hechizos del Mago ---");
        
        // Añadir hechizos al mago (ID del mago = 1, IDs de hechizos = 1, 2, 3)
        cm.anadirHechizo(1, bolaFuego.getId());
        cm.anadirHechizo(1, bolaNieve.getId());
        cm.anadirHechizo(1, rayo.getId());

        // Contar hechizos
        int cantidadHechizos = cm.contarHechizos(1);
        System.out.println("El mago tiene " + cantidadHechizos + " hechizos");

        // Listar hechizos del mago
        java.util.List<Hechizo> hechizosDelMago = cm.obtenerHechizos(1);
        System.out.println("Hechizos del mago:");
        for (Hechizo h : hechizosDelMago) {
            System.out.println("  - " + h.getNombre() + " (ID: " + h.getId() + ")");
        }

        // Eliminar un hechizo del mago
        System.out.println("\nEliminando hechizo Bola de Nieve del mago...");
        cm.eliminarHechizo(1, bolaNieve.getId());

        // Contar hechizos después de eliminar
        int cantidadHechizosRestantes = cm.contarHechizos(1);
        System.out.println("El mago ahora tiene " + cantidadHechizosRestantes + " hechizos");
    }
}

package com.example.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.example.controller.*;
import com.example.model.*;

/**
 * Clase Partida - Gestiona toda la lógica del juego Dragolandia.
 * 
 * Esta clase es responsable de:
 * - Inicializar la partida con todos los componentes del juego (magos, monstruos, dragón, bosque)
 * - Gestionar el flujo principal del juego en rondas
 * - Controlar los turnos de los magos y sus acciones
 * - Manejar los ataques del monstruo jefe y dragón
 * - Detectar el fin del juego
 * - Mostrar el estado del juego en cada ronda
 * 
 * El juego termina cuando no hay magos vivos o no hay monstruos en el bosque.
 * 
 * @see Mago
 * @see Monstruo
 * @see Dragon
 * @see Bosque
 * @see Hechizo
 */
public class Partida {
    
    /** Controlador para gestionar operaciones del bosque en la BD */
    private ControllerBosque cBosque;
    
    /** Controlador para gestionar operaciones del dragón en la BD */
    private ControllerDragon cDragon;
    
    /** Controlador para gestionar hechizos en la BD */
    private ControllerHechizos cHechizos;
    
    /** Controlador para gestionar magos en la BD */
    private ControllerMago cMago;
    
    /** Controlador para gestionar monstruos en la BD */
    private ControllerMonstruo cMonstruo;
    
    /** Bosque actual del juego */
    private Bosque bosque;
    
    /** Lista de magos vivos en la partida */
    private List<Mago> magos;
    
    /** Dragón del juego */
    private Dragon dragon;
    
    /** Lista de hechizos disponibles en el juego */
    private List<Hechizo> hechizosDisponibles;
    
    /** Scanner para entrada del usuario */
    private Scanner scanner;
    
    /** Generador de números aleatorios */
    private Random random;
    
    /**
     * Constructor de la clase Partida.
     * 
     * Inicializa todos los controladores, listas y objetos necesarios para la partida.
     */
    public Partida() {
        this.cBosque = new ControllerBosque();
        this.cDragon = new ControllerDragon();
        this.cHechizos = new ControllerHechizos();
        this.cMago = new ControllerMago();
        this.cMonstruo = new ControllerMonstruo();
        
        this.magos = new ArrayList<>();
        this.hechizosDisponibles = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    /**
     * Inicializa la partida con todos los componentes necesarios.
     * 
     * Este método:
     * - Limpia la base de datos para reiniciarla
     * - Inicializa los 4 hechizos disponibles (Bola de fuego, Bola de nieve, Rayo, Ataque Básico)
     * - Crea 2 magos con 2 hechizos cada uno
     * - Crea 3 monstruos
     * - Crea un dragón
     * - Crea un bosque y asigna los monstruos
     * - Asigna un monstruo jefe al bosque
     * - Recarga los datos desde la BD para consistencia
     * - Muestra el estado inicial del juego
     */
    public void inicializarPartida() {
        System.out.println("=== INICIALIZANDO PARTIDA ===\n");
        
        // LIMPIAR LA BASE DE DATOS
        InitDB.limpiarBD();
        
        // Inicializar hechizos
        cHechizos.inicializarHechizos();
        BolaFuego bolaFuego = cHechizos.obtenerBolaFuego();
        BolaNieve bolaNieve = cHechizos.obtenerBolaNieve();
        Rayo rayo = cHechizos.obtenerRayo();
        AtaqueBasico ataqueBasico = cHechizos.obtenerAtaqueBasico();
        
        hechizosDisponibles.add(bolaFuego);
        hechizosDisponibles.add(bolaNieve);
        hechizosDisponibles.add(rayo);
        hechizosDisponibles.add(ataqueBasico);
        
        // Crear magos y guardar en BD
        Mago harryPotter = cMago.guardarMago("Harry Potter", 200, 3);
        Mago judini = cMago.guardarMago("Judini", 180, 2);
        
        // Asignar hechizos a Harry Potter
        cMago.anadirHechizo(harryPotter.getId(), bolaFuego.getId());
        cMago.anadirHechizo(harryPotter.getId(), rayo.getId());
        
        // Asignar hechizos a Judini
        cMago.anadirHechizo(judini.getId(), ataqueBasico.getId());
        cMago.anadirHechizo(judini.getId(), bolaNieve.getId());
        
        // Recargar magos desde BD para obtener los hechizos asignados
        harryPotter = cMago.obtenerMago(harryPotter.getId());
        judini = cMago.obtenerMago(judini.getId());
        
        magos.add(harryPotter);
        magos.add(judini);
        
        // Crear monstruos y guardar en BD
        Monstruo ogroPepe = cMonstruo.guardarMonstruo("Pepe", 400, TipoMonstruo.ogro, 20);
        Monstruo trollJofrey = cMonstruo.guardarMonstruo("Jofrey", 450, TipoMonstruo.troll, 16);
        Monstruo espectroBenjen = cMonstruo.guardarMonstruo("Benjen", 300, TipoMonstruo.espectro, 30);
        
        // Crear dragón y guardar en BD
        dragon = cDragon.guardarDragon("Viserys", 50, 1000);
        
        // Crear bosque y guardar en BD
        bosque = cBosque.guardarBosque("Bosque Verde", 10, dragon);
        
        // Asignar monstruos al bosque
        cBosque.anadirMonstruo(bosque.getId(), ogroPepe.getId());
        cBosque.anadirMonstruo(bosque.getId(), trollJofrey.getId());
        cBosque.anadirMonstruo(bosque.getId(), espectroBenjen.getId());
        
        // Asignar monstruo jefe
        cBosque.modificarMonstruoJefe(ogroPepe.getId(), bosque.getId());
        
        // Recargar datos del bosque desde BD
        bosque = cBosque.obtenerBosque(bosque.getId());
        
        System.out.println(" Partida inicializada correctamente\n");
        mostrarEstadoInicial();
    }
    
    /**
     * Muestra el estado inicial de la partida.
     * 
     * Presenta al jugador:
     * - Nombre del bosque y monstruo jefe
     * - Lista de magos con sus vidas
     * - Monstruos presentes en el bosque
     * - Dragón del juego
     * - Hechizos disponibles
     */
    private void mostrarEstadoInicial() {
        System.out.println("=== ESTADO INICIAL ===");
        System.out.println("Bosque: " + bosque.getNombre());
        System.out.println("Monstruo Jefe: " + bosque.getMonstruoJefe().getNombre() + 
                        " (Vida: " + bosque.getMonstruoJefe().getVida() + ")");
        System.out.println("\nMagos:");
        for (Mago mago : magos) {
            System.out.println("  - " + mago.getNombre() + " (Vida: " + mago.getVida() + ")");
        }
        System.out.println("\nMonstruos del bosque:");
        for (Monstruo m : bosque.getMonstruos()) {
            System.out.println("  - " + m.getNombre() + " (Vida: " + m.getVida() + ")");
        }
        System.out.println("\nDragón: " + dragon.getNombre() + " (Resistencia: " + dragon.getResistencia() + ")");
        System.out.println("\nHechizos disponibles:");
        for (Hechizo h : hechizosDisponibles) {
            System.out.println("  - " + h.getNombre());
        }
        System.out.println("\n==========================================\n");
    }
    
    /**
     * Inicia el bucle principal del juego.
     * 
     * Controla el flujo de todas las rondas:
     * - Cada mago realiza su turno
     * - El monstruo jefe ataca a todos los magos vivos
     * - El dragón ataca al monstruo jefe
     * - Se verifica si hay magos o monstruos muertos
     * - Se asigna un nuevo jefe si es necesario
     * - Muestra el estado después de cada ronda
     * 
     * El juego termina cuando no hay magos o no hay monstruos.
     */
    public void iniciarJuego() {
        inicializarPartida();
        
        int ronda = 1;
        boolean juegoEnCurso = true;
        
        while (juegoEnCurso) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║           RONDA " + String.format("%2d", ronda) + "                    ║");
            System.out.println("╚════════════════════════════════════════╝\n");
            
            // Turno de cada mago
            for (Mago mago : magos) {
                if (mago.getVida() > 0) {
                    turnoMago(mago);
                }
            }
            
            // Turno del monstruo jefe (ataca a TODOS los magos vivos)
            if (bosque.getMonstruoJefe().getVida() > 0) {
                System.out.println("\n " + bosque.getMonstruoJefe().getNombre() + " (JEFE) ataca a todos los magos:");
                for (Mago mago : magos) {
                    if (mago.getVida() > 0) {
                        bosque.getMonstruoJefe().atacar(mago);
                        System.out.println("   " + mago.getNombre() + " recibe daño | Vida: " + mago.getVida());
                    }
                }
            }
            
            // Turno del dragón (ataca al monstruo jefe)
            if (dragon.getResistencia() > 0 && bosque.getMonstruoJefe().getVida() > 0) {
                System.out.println("\n  " + dragon.getNombre() + " ataca al monstruo jefe " + 
                                bosque.getMonstruoJefe().getNombre());
                dragon.exhalar(bosque.getMonstruoJefe());
                System.out.println("   Vida del monstruo jefe: " + bosque.getMonstruoJefe().getVida());
            }
            
            // Eliminar monstruo jefe si está muerto
            if (bosque.getMonstruoJefe().getVida() <= 0) {
                System.out.println("\n " + bosque.getMonstruoJefe().getNombre() + " (JEFE) ha sido derrotado!");
                bosque.getMonstruos().remove(bosque.getMonstruoJefe());
                
                // Reasignar monstruo jefe si hay más monstruos
                if (!bosque.getMonstruos().isEmpty()) {
                    asignarNuevoJefe();
                }
            }
            
            // Eliminar magos muertos
            magos.removeIf(mago -> mago.getVida() <= 0);
            
            // Mostrar estado de la ronda
            mostrarEstadoRonda(ronda);
            
            // Verificar fin del juego
            if (magos.isEmpty() || bosque.getMonstruos().isEmpty()) {
                juegoEnCurso = false;
            }
            
            ronda++;
            
            if (juegoEnCurso) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        mostrarResultadoFinal();
    }
    
    /**
     * Gestiona el turno de un mago.
     * 
     * Presenta un menú con 3 opciones:
     * 1. Lanzar hechizo - Elige un hechizo disponible
     * 2. Ver mis hechizos - Muestra los hechizos que el mago conoce
     * 3. Invocar dragón - Intenta invocar al dragón (50% probabilidad)
     * 
     * Si elige "Ver mis hechizos", vuelve a mostrar el menú para elegir acción.
     * 
     * @param mago El mago cuyo turno se gestiona
     */
    private void turnoMago(Mago mago) {
        System.out.println("\n>>> Turno de " + mago.getNombre());
        System.out.println("Vida actual: " + mago.getVida());
        System.out.println("\n1. Lanzar hechizo");
        System.out.println("2. Ver mis hechizos");
        System.out.println("3. Invocar dragón");
        System.out.print("Elige una opción: ");
        
        int opcion = obtenerOpcionValida(1, 3);
        
        switch (opcion) {
            case 1:
                lanzarHechizo(mago);
                break;
            case 2:
                mostrarHechizosDelMago(mago);
                turnoMago(mago); // Volver a elegir
                break;
            case 3:
                invocarDragon(mago);
                break;
        }
    }
    
    /**
     * Lanza un hechizo contra el monstruo jefe.
     * 
     * - Muestra la lista de hechizos disponibles
     * - El mago elige uno
     * - Si el mago conoce el hechizo: lo lanza contra el jefe e inflige daño
     * - Si no lo conoce: pierde 1 de vida
     * 
     * @param mago El mago que lanza el hechizo
     */
    private void lanzarHechizo(Mago mago) {
        System.out.println("\n=== HECHIZOS DISPONIBLES ===");
        for (int i = 0; i < hechizosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + hechizosDisponibles.get(i).getNombre());
        }
        System.out.print("Elige un hechizo: ");
        
        int opcion = obtenerOpcionValida(1, hechizosDisponibles.size());
        Hechizo hechizoBuscado = hechizosDisponibles.get(opcion - 1);
        
        // Verificar si el mago conoce el hechizo (por ID)
        boolean hechizoConocido = false;
        if (mago.getConjuros() != null) {
            for (Hechizo h : mago.getConjuros()) {
                if (h.getId() == hechizoBuscado.getId()) {
                    hechizoConocido = true;
                    break;
                }
            }
        }
        
        if (hechizoConocido) {
            System.out.println("\n " + mago.getNombre() + " lanza " + hechizoBuscado.getNombre() + "!");
            
            // Aplicar el hechizo solo al monstruo jefe
            if (bosque.getMonstruoJefe().getVida() > 0) {
                mago.lanzarHechizo(bosque.getMonstruoJefe());
                System.out.println("  - " + bosque.getMonstruoJefe().getNombre() + " recibe daño. Vida: " + bosque.getMonstruoJefe().getVida());
            }
        } else {
            System.out.println("\n " + mago.getNombre() + " no conoce este hechizo y pierde 1 de vida");
            mago.setVida(mago.getVida() - 1);
        }
    }
    
    /**
     * Intenta invocar el dragón.
     * 
     * El dragón aparece con un 50% de probabilidad.
     * Si aparece, ataca al monstruo jefe.
     * Si no aparece, el mago simplemente espera su turno.
     * 
     * @param mago El mago que invoca al dragón
     */
    private void invocarDragon(Mago mago) {
        System.out.println("\n" + mago.getNombre() + " intenta invocar al dragón " + dragon.getNombre() + "...");
        
        // 50% de probabilidad de que aparezca
        if (random.nextBoolean()) {
            System.out.println(" ¡El dragón aparece!");
            if (bosque.getMonstruoJefe().getVida() > 0) {
                System.out.println("El dragón ataca al monstruo jefe!");
                dragon.exhalar(bosque.getMonstruoJefe());
                System.out.println("Vida del monstruo jefe: " + bosque.getMonstruoJefe().getVida());
            }
        } else {
            System.out.println(" El dragón no aparece esta vez");
        }
    }
    
    /**
     * Muestra los hechizos que conoce un mago.
     * 
     * Lista todos los hechizos que el mago ha aprendido y puede usar
     * sin perder vida.
     * 
     * @param mago El mago cuyos hechizos se mostrarán
     */
    private void mostrarHechizosDelMago(Mago mago) {
        System.out.println("\n=== HECHIZOS DE " + mago.getNombre().toUpperCase() + " ===");
        if (mago.getConjuros().isEmpty()) {
            System.out.println("No conoces ningún hechizo");
        } else {
            for (Hechizo h : mago.getConjuros()) {
                System.out.println("- " + h.getNombre());
            }
        }
    }
    
    /**
     * Asigna un nuevo monstruo jefe cuando el anterior muere.
     * 
     * Selecciona el primer monstruo vivo de la lista del bosque
     * como el nuevo monstruo jefe.
     */
    private void asignarNuevoJefe() {
        if (!bosque.getMonstruos().isEmpty()) {
            Monstruo nuevoJefe = bosque.getMonstruos().get(0);
            bosque.cambiarJefe(nuevoJefe);
            System.out.println("\nNUEVO MONSTRUO JEFE: " + nuevoJefe.getNombre() + " (Vida: " + nuevoJefe.getVida() + ")");
        }
    }
    
    /**
     * Muestra el estado actual de la ronda.
     * 
     * Presenta:
     * - Lista de magos vivos con sus vidas
     * - Monstruos presentes en el bosque y cuál es el jefe
     * - Estado del dragón
     * 
     * @param ronda Número de la ronda actual
     */
    private void mostrarEstadoRonda(int ronda) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ESTADO DESPUÉS DE RONDA " + String.format("%2d", ronda) + "      ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        System.out.println("MAGOS:");
        if (magos.isEmpty()) {
            System.out.println("  Todos los magos están muertos");
        } else {
            for (Mago mago : magos) {
                System.out.println("  - " + mago.getNombre() + " | Vida: " + mago.getVida() + "/200");
            }
        }
        
        System.out.println("\n MONSTRUOS EN EL BOSQUE:");
        if (bosque.getMonstruos().isEmpty()) {
            System.out.println("  Todos los monstruos están muertos");
        } else {
            for (Monstruo m : bosque.getMonstruos()) {
                String jefe = m.equals(bosque.getMonstruoJefe()) ? " [JEFE]" : "";
                System.out.println("  - " + m.getNombre() + jefe + " | Vida: " + m.getVida() + 
                                " | Tipo: " + m.getTipo());
            }
        }
        
        System.out.println("\n DRAGÓN:");
        System.out.println("  - " + dragon.getNombre() + " | Resistencia: " + dragon.getResistencia());
        
        System.out.println("\n" + "─".repeat(40));
    }
    
    /**
     * Muestra el resultado final del juego.
     * 
     * Determina si fue VICTORIA (todos los monstruos derrotados)
     * o DERROTA (todos los magos derrotados).
     * En caso de victoria, muestra los magos supervivientes.
     */
    private void mostrarResultadoFinal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         FIN DE LA PARTIDA              ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        if (magos.isEmpty()) {
            System.out.println("DERROTA: Todos los magos han sido derrotados");
            System.out.println("Los monstruos han ganado la partida");
        } else if (bosque.getMonstruos().isEmpty()) {
            System.out.println("VICTORIA: Todos los monstruos han sido derrotados");
            System.out.println("Los magos han ganado la partida");
            System.out.println("\nMagos supervivientes:");
            for (Mago mago : magos) {
                System.out.println("  - " + mago.getNombre() + " (Vida: " + mago.getVida() + ")");
            }
        }
    }
    
    /**
     * Obtiene una opción válida del usuario dentro de un rango.
     * 
     * Solicita al usuario que ingrese un número entre min y max (inclusive).
     * Si la entrada es inválida o fuera de rango, solicita nuevamente.
     * 
     * @param min El valor mínimo permitido
     * @param max El valor máximo permitido
     * @return La opción válida ingresada por el usuario
     */
    private int obtenerOpcionValida(int min, int max) {
        int opcion = -1;
        while (opcion < min || opcion > max) {
            try {
                opcion = scanner.nextInt();
                if (opcion < min || opcion > max) {
                    System.out.print("Opción inválida. Intenta de nuevo: ");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Entrada inválida. Intenta de nuevo: ");
            }
        }
        scanner.nextLine(); // Limpiar buffer
        return opcion;
    }

}

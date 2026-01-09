package com.example.controller;

import com.example.model.HibernateUtil;

import jakarta.persistence.EntityManager;

/**
 * Clase InitDB - Utilidad para inicializar y limpiar la base de datos de Dragolandia.
 * 
 * Proporciona métodos estáticos para realizar operaciones de limpieza y reinicio
 * de la base de datos. Se utiliza típicamente al inicio del juego para asegurar
 * que la BD comienza en un estado limpio y consistente.
 * 
 * Operaciones:
 * - limpiarBD(): Elimina todos los datos de todas las tablas
 * - reiniciarBD(): Realiza una limpieza completa (actualmente igual que limpiarBD)
 * 
 * Tablas afectadas (en orden de eliminación):
 * 1. Bosque (contiene relaciones con Monstruo y Dragon)
 * 2. Mago (contiene relaciones con Hechizo)
 * 3. Monstruo (entidad independiente)
 * 4. Dragon (entidad independiente)
 * 5. Hechizo (entidad base para subclases de hechizos)
 * 
 * Se elimina en orden inverso de las relaciones para evitar violaciones de FK.
 * 
 * Uso típico en Partida.java:
 * InitDB.limpiarBD(); // Llamado al inicio de inicializarPartida()
 * 
 * @see HibernateUtil
 * @see Partida
 */
public class InitDB {
    
    /**
     * Limpia todas las tablas de la base de datos.
     * 
     * Elimina todos los registros de:
     * - Bosque (incluyendo relaciones con monstruos y dragón)
     * - Mago (incluyendo relaciones con hechizos)
     * - Monstruo (enemigos)
     * - Dragon (aliado)
     * - Hechizo (hechizos de todas las subclases)
     * 
     * El orden de eliminación es importante para respetar las restricciones
     * de clave foránea (Foreign Key) en la BD.
     * 
     * Se ejecuta dentro de una transacción y se hace rollback automático
     * si ocurre algún error.
     */
    public static void limpiarBD() {
        System.out.println("\n Limpiando base de datos...");
        
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            
            // Eliminar en orden inverso de las relaciones
            // Primero eliminar monstruos, magos, dragones, hechizos y bosques
            em.createQuery("DELETE FROM Bosque").executeUpdate();
            em.createQuery("DELETE FROM Mago").executeUpdate();
            em.createQuery("DELETE FROM Monstruo").executeUpdate();
            em.createQuery("DELETE FROM Dragon").executeUpdate();
            em.createQuery("DELETE FROM Hechizo").executeUpdate();
            
            em.getTransaction().commit();
            System.out.println(" Base de datos limpiada correctamente\n");
        } catch (Exception e) {
            System.out.println(" Error al limpiar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Reinicia la base de datos realizando una limpieza completa.
     * 
     * Actualmente ejecuta la misma operación que limpiarBD().
     * Puede ser ampliado en el futuro para incluir recreación de tablas.
     * 
     * @see #limpiarBD()
     */
    public static void reiniciarBD() {
        System.out.println("\n Reiniciando base de datos...");
        limpiarBD();
    }
}

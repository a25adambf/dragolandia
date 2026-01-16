package com.example.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase HibernateUtil - Utilidad singleton para gestionar la conexión a la BD con Hibernate.
 * 
 * Esta clase proporciona un punto de acceso centralizado para obtener EntityManager
 * instances que se utilizan para realizar operaciones de persistencia (CRUD) con la BD.
 * Implementa el patrón Singleton para garantizar que solo existe una única
 * EntityManagerFactory en toda la aplicación.
 * 
 * Características:
 * - Constructor privado para prevenir instanciación
 * - EntityManagerFactory estática y final (singleton pattern)
 * - Método estático para obtener nuevos EntityManager
 * - Método estático para cerrar la factoría de manera ordenada
 * 
 * Configuración:
 * - Unidad de persistencia: "dragolandiaServizo"
 * - Base de datos: MySQL en localhost:3306/dragolandia
 * - Usuario: dam2user
 * 
 * Uso típico:
 * EntityManager em = HibernateUtil.getEntityManager();
 * // ... operaciones de BD ...
 * em.close();
 * 
 * Al finalizar la aplicación:
 * HibernateUtil.close(); // Cierra la factoría de entidades
 * 
 * @see EntityManager
 * @see EntityManagerFactory
 */
public class HibernateUtil {

    /**
     * Constructor privado para prevenir instanciación de la clase utilidad.
     */
    private HibernateUtil() {}

    /**
     * EntityManagerFactory singleton que gestiona la conexión a la BD.
     * 
     * Se crea una única instancia al cargar la clase, usando la unidad de
     * persistencia "dragolandiaServizo" definida en persistence.xml
     */
    private static final EntityManagerFactory xestorEntidades =
        Persistence.createEntityManagerFactory("dragolandiaServizo");

    /**
     * Obtiene un nuevo EntityManager para operaciones de persistencia.
     * 
     * Cada llamada crea una nueva instancia de EntityManager.
     * Es responsabilidad del cliente cerrar el EntityManager después de usar.
     * 
     * @return Un nuevo EntityManager listo para operaciones de BD
     */
    public static EntityManager getEntityManager() {
        return xestorEntidades.createEntityManager();
    }

    /**
     * Cierra la EntityManagerFactory de manera ordenada.
     * 
     * Debe llamarse al finalizar la aplicación para liberar recursos
     * y cerrar la conexión a la BD. Verifica si la factoría está abierta
     * antes de intentar cerrarla.
     */
    public static void close() {
        if (xestorEntidades.isOpen()) {
            xestorEntidades.close();
        }
    }

}

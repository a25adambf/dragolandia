package com.example.controller;

import com.example.model.HibernateUtil;
import com.example.model.Hechizo;
import com.example.model.AtaqueBasico;
import com.example.model.BolaFuego;
import com.example.model.BolaNieve;
import com.example.model.Rayo;

import jakarta.persistence.EntityManager;

/**
 * Clase ControllerHechizos - Controlador para operaciones CRUD de hechizos.
 * 
 * Este controlador gestiona todas las operaciones de persistencia
 * relacionadas con los hechizos incluyendo:
 * - Obtención o creación de instancias únicas de cada hechizo
 * - Modificación de atributos (nombre, descripción)
 * - Consulta de hechizos por ID
 * - Inicialización de hechizos en la BD
 * 
 * Implementa el patrón singleton para los hechizos:
 * Existe una única instancia de cada tipo de hechizo en la BD con IDs fijos.
 * 
 * Métodos principales:
 * - obtenerBolaFuego/BolaNieve/Rayo/AtaqueBasico(): Obtienen o crean instancia
 * - modificarNombre/Descripcion(): Actualiza atributos de hechizo
 * - obtenerHechizoPorId(): Recupera hechizo por ID
 * - inicializarHechizos(): Prepara todos los hechizos en la BD
 * 
 * IDs de hechizos (fijos):
 * - 1: BolaFuego
 * - 2: BolaNieve
 * - 3: Rayo
 * - 4: AtaqueBasico
 * 
 * @see Hechizo
 * @see BolaFuego
 * @see BolaNieve
 * @see Rayo
 * @see AtaqueBasico
 * @see HibernateUtil
 */
public class ControllerHechizos {
    
    /** ID único para la instancia de BolaFuego */
    private static final int ID_BOLA_FUEGO = 1;
    
    /** ID único para la instancia de BolaNieve */
    private static final int ID_BOLA_NIEVE = 2;
    
    /** ID único para la instancia de Rayo */
    private static final int ID_RAYO = 3;
    
    /** ID único para la instancia de AtaqueBasico */
    private static final int ID_ATAQUE_BASICO = 4;

    /**
     * Obtiene o crea la instancia única de BolaFuego en la BD.
     * 
     * Si el hechizo no existe en la BD, se crea e inmediatamente se persiste.
     * 
     * @return La instancia única de BolaFuego, o null si hay error
     */
    public BolaFuego obtenerBolaFuego() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            BolaFuego bola = em.find(BolaFuego.class, ID_BOLA_FUEGO);
            
            if (bola == null) {
                bola = new BolaFuego();
                em.getTransaction().begin();
                em.persist(bola);
                em.getTransaction().commit();
                System.out.println("Hechizo Bola de fuego guardado con id: " + bola.getId());
            }
            
            return bola;
        } catch (Exception e) {
            System.out.println("Error al obtener Bola de Fuego: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene o crea la instancia única de BolaNieve en la BD.
     * 
     * Si el hechizo no existe en la BD, se crea e inmediatamente se persiste.
     * 
     * @return La instancia única de BolaNieve, o null si hay error
     */
    public BolaNieve obtenerBolaNieve() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            BolaNieve bola = em.find(BolaNieve.class, ID_BOLA_NIEVE);
            
            if (bola == null) {
                // No existe en la base de datos, crear nueva 
                bola = new BolaNieve();
                em.getTransaction().begin();
                em.persist(bola);
                em.getTransaction().commit();
                System.out.println("Hechizo Bola de nieve guardado con id: " + bola.getId());
            }
            
            return bola;
        } catch (Exception e) {
            System.out.println("Error al obtener Bola de Nieve: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene o crea la instancia única de Rayo en la BD.
     * 
     * Si el hechizo no existe en la BD, se crea e inmediatamente se persiste.
     * 
     * @return La instancia única de Rayo, o null si hay error
     */
    public Rayo obtenerRayo() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Rayo rayo = em.find(Rayo.class, ID_RAYO);
            
            if (rayo == null) {
                // No existe en la base de datos, crear nueva
                rayo = new Rayo();
                em.getTransaction().begin();
                em.persist(rayo);
                em.getTransaction().commit();
                System.out.println("Hechizo Rayo guardado con id: " + rayo.getId());
            }
            
            return rayo;
        } catch (Exception e) {
            System.out.println("Error al obtener Rayo: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene o crea la instancia única de AtaqueBasico en la BD.
     * 
     * Si el hechizo no existe en la BD, se crea e inmediatamente se persiste.
     * 
     * @return La instancia única de AtaqueBasico, o null si hay error
     */
    public AtaqueBasico obtenerAtaqueBasico() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            AtaqueBasico ataqueBasico = em.find(AtaqueBasico.class, ID_ATAQUE_BASICO);
            
            if (ataqueBasico == null) {
                // No existe en la base de datos, crear nueva
                ataqueBasico = new AtaqueBasico();
                em.getTransaction().begin();
                em.persist(ataqueBasico);
                em.getTransaction().commit();
                System.out.println("Hechizo Ataque Básico guardado con id: " + ataqueBasico.getId());
            }
            
            return ataqueBasico;
        } catch (Exception e) {
            System.out.println("Error al obtener Ataque Básico: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica el nombre de un hechizo existente.
     * 
     * @param nombre El nuevo nombre del hechizo
     * @param id El ID del hechizo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarNombre(String nombre, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Hechizo hechizo = em.find(Hechizo.class, id);

            if (hechizo != null) {
                hechizo.setNombre(nombre);
                em.merge(hechizo);
                em.getTransaction().commit();
                System.out.println("Nombre del hechizo modificado correctamente");
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nombre " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica la descripción de un hechizo existente.
     * 
     * @param descripcion La nueva descripción del hechizo
     * @param id El ID del hechizo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarDescripcion(String descripcion, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Hechizo hechizo = em.find(Hechizo.class, id);

            if (hechizo != null) {
                hechizo.setDescripcion(descripcion);
                em.merge(hechizo);
                em.getTransaction().commit();
                System.out.println("Descripción del hechizo modificada correctamente");
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar la descripción " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene un hechizo de la BD por su ID.
     * 
     * @param id El ID único del hechizo
     * @return El hechizo encontrado, o null si no existe
     */
    public Hechizo obtenerHechizoPorId(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Hechizo.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Verifica e inicializa todas las instancias únicas de hechizos en la BD.
     * 
     * Si algún hechizo no existe, se crea e inmediatamente se persiste.
     * Debe llamarse al inicio de la aplicación para preparar el sistema de hechizos.
     */
    public void inicializarHechizos() {
        System.out.println("Inicializando hechizos únicos...");
        obtenerBolaFuego();
        obtenerBolaNieve();
        obtenerRayo();
        obtenerAtaqueBasico();
        System.out.println("Hechizos inicializados correctamente");
    }


}

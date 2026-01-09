package com.example.controller;

import com.example.model.HibernateUtil;
import com.example.model.Hechizo;
import com.example.model.AtaqueBasico;
import com.example.model.BolaFuego;
import com.example.model.BolaNieve;
import com.example.model.Rayo;

import jakarta.persistence.EntityManager;


public class ControllerHechizos {
    
    // IDs fijos para cada hechizo único
    private static final int ID_BOLA_FUEGO = 1;
    private static final int ID_BOLA_NIEVE = 2;
    private static final int ID_RAYO = 3;
    private static final int ID_ATAQUE_BASICO = 4;


    /**
     * Obtiene o crea la instancia única de BolaFuego en la base de datos
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
     * Obtiene o crea la instancia única de BolaNieve en la base de datos
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
     * Obtiene o crea la instancia única de Rayo en la base de datos
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
     * Obtiene o crea la instancia única de Ataque Básico en la base de datos
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
     * Modifica el nombre de un hechizo
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
     * Modifica la descripción de un hechizo
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
     * Obtiene un hechizo por su id
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
     * Verifica si existe una instancia única de cada hechizo y las carga en memoria
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

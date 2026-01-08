package com.example.controller;


import com.example.model.HibernateUtil;

import jakarta.persistence.EntityManager;

import com.example.model.Dragon;


public class ControllerDragon {

    public Dragon crearDragon(String nombre, int intensidadFuego, int resistencia) {

        Dragon dragon = null;

        if (nombre.length() > 0 && intensidadFuego > 0 && resistencia > 0) {
            dragon = new Dragon(nombre, intensidadFuego, resistencia);
        }

        return dragon;
    }



    public boolean guardarDragon(String nombre, int intensidadFuego, int resistencia) {
        
        Dragon dragon = crearDragon(nombre, intensidadFuego, resistencia);

        boolean guardado = false;

        if (dragon != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();
            
            em.persist(dragon);
            em.getTransaction().commit();
            System.out.println("Dragon guardado con id: " + dragon.getId());
            guardado = true;

        } catch (Exception e) {
            System.out.println("Error al guarda el Dragon " + e.getMessage());
            return guardado;
        }
        }
        
        return guardado;
    }


    /**
     * Modifica el nombre del dragón
     */
    public boolean modificarNombre(String nombre, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Dragon dragon = em.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setNombre(nombre);
                em.merge(dragon);
                em.getTransaction().commit();
                System.out.println("Nombre del dragón modificado correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nombre: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica la intensidad de fuego del dragón
     */
    public boolean modificarIntensidadFuego(int intensidad, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Dragon dragon = em.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setIntensidadFuego(intensidad);
                em.merge(dragon);
                em.getTransaction().commit();
                System.out.println("Intensidad de fuego del dragón modificada correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar la intensidad de fuego: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica la resistencia del dragón
     */
    public boolean modificarResistencia(int resistencia, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Dragon dragon = em.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setResistencia(resistencia);
                em.merge(dragon);
                em.getTransaction().commit();
                System.out.println("Resistencia del dragón modificada correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar la resistencia: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }


    public boolean eliminarDragon(int id) {

        boolean eliminado = false;

        try (EntityManager em = HibernateUtil.getEntityManager()) {

            em.getTransaction().begin();
            
            Dragon dragon = em.find(Dragon.class, id);

            if (dragon != null) {
                em.remove(dragon);
                em.getTransaction().commit();
                eliminado = true;
                
                System.out.println("Eliminado con éxito");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el Dragon");
            return eliminado;
        }
        
        return eliminado;
    }
}



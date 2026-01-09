package com.example.controller;


import com.example.model.HibernateUtil;

import jakarta.persistence.EntityManager;

import com.example.model.Dragon;

/**
 * Clase ControllerDragon - Controlador para operaciones CRUD del dragón.
 * 
 * Este controlador gestiona todas las operaciones de persistencia
 * relacionadas con el dragón aliado (Dragon) incluyendo:
 * - Crear, leer, actualizar y eliminar dragón (CRUD)
 * - Modificar atributos específicos (nombre, intensidad de fuego, resistencia)
 * 
 * Implementa el patrón DAO (Data Access Object) usando Jakarta Persistence.
 * 
 * Métodos principales:
 * - crearDragon(): Crea nueva instancia en memoria
 * - guardarDragon(): Persiste dragón en BD
 * - obtenerDragon(): Recupera dragón de BD por ID
 * - modificarNombre/IntensidadFuego/Resistencia(): Actualiza atributos
 * - eliminarDragon(): Borra dragón de BD
 * 
 * @see Dragon
 * @see HibernateUtil
 */
public class ControllerDragon {

    /**
     * Crea una nueva instancia de Dragon en memoria sin persistencia.
     * 
     * Valida que los parámetros sean válidos:
     * - nombre con longitud > 0
     * - intensidadFuego > 0
     * - resistencia > 0
     * 
     * @param nombre El nombre del dragón
     * @param intensidadFuego El poder de ataque del dragón
     * @param resistencia La resistencia/defensa del dragón
     * @return Nuevo Dragon si los parámetros son válidos, null en caso contrario
     */
    public Dragon crearDragon(String nombre, int intensidadFuego, int resistencia) {

        Dragon dragon = null;

        if (nombre.length() > 0 && intensidadFuego > 0 && resistencia > 0) {
            dragon = new Dragon(nombre, intensidadFuego, resistencia);
        }

        return dragon;
    }

    /**
     * Crea y persiste un nuevo dragón en la BD.
     * 
     * Realiza validación mediante crearDragon() y luego
     * persiste el dragón en la BD si es válido.
     * 
     * @param nombre El nombre del dragón
     * @param intensidadFuego El poder de ataque inicial
     * @param resistencia La resistencia inicial
     * @return El Dragon persistido con ID asignado, o null si hay error
     */
    public Dragon guardarDragon(String nombre, int intensidadFuego, int resistencia) {
        
        Dragon dragon = crearDragon(nombre, intensidadFuego, resistencia);


        if (dragon != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();
            
            em.persist(dragon);
            em.getTransaction().commit();
            System.out.println("Dragon guardado con id: " + dragon.getId());

        } catch (Exception e) {
            System.out.println("Error al guarda el Dragon " + e.getMessage());
            return null;
        }
        }
        
        return dragon;
    }


    /**
     * Modifica el nombre del dragón.
     * 
     * @param nombre El nuevo nombre del dragón
     * @param id El ID del dragón a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
     * Modifica la intensidad de fuego del dragón.
     * 
     * @param intensidad El nuevo valor de intensidad de fuego
     * @param id El ID del dragón a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
     * Modifica la resistencia del dragón.
     * 
     * @param resistencia El nuevo valor de resistencia
     * @param id El ID del dragón a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
    /**
     * Obtiene un dragón de la BD por su ID.
     * 
     * @param id El ID único del dragón
     * @return El dragón encontrado, o null si no existe
     */
    public Dragon obtenerDragon(int id) {

    Dragon dragon = null;

        try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            dragon = em.find (Dragon.class, id);

        } catch (Exception e) {
            System.out.println("Error al obtener el dragón " + e.getMessage());
            return dragon;
        }

        return dragon;
    }
    /**
     * Elimina un dragón de la BD por su ID.
     * 
     * @param id El ID del dragón a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
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



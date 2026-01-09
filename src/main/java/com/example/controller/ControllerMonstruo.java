package com.example.controller;


import com.example.model.Dragon;
import com.example.model.HibernateUtil;
import com.example.model.Monstruo;
import com.example.model.TipoMonstruo;

import jakarta.persistence.EntityManager;

/**
 * Clase ControllerMonstruo - Controlador para operaciones CRUD de monstruos.
 * 
 * Este controlador gestiona todas las operaciones de persistencia
 * relacionadas con los monstruos enemigos (Monstruo) incluyendo:
 * - Crear, leer, actualizar y eliminar monstruos (CRUD)
 * - Modificar atributos específicos (nombre, vida, fuerza, tipo)
 * 
 * Implementa el patrón DAO (Data Access Object) usando Jakarta Persistence.
 * 
 * Métodos principales:
 * - crearMonstruo(): Crea nueva instancia en memoria
 * - guardarMonstruo(): Persiste monstruo en BD
 * - obtenerMonstruo(): Recupera monstruo de BD por ID
 * - modificarNombre/Vida/Fuerza/Tipo(): Actualiza atributos
 * - eliminarMonstruo(): Borra monstruo de BD
 * 
 * @see Monstruo
 * @see HibernateUtil
 * @see TipoMonstruo
 */
public class ControllerMonstruo {
    
    /**
     * Crea una nueva instancia de Monstruo en memoria sin persistencia.
     * 
     * Valida que los parámetros sean válidos:
     * - nombre con longitud > 0
     * - vida > 0
     * - fuerza > 0
     * 
     * @param nombre El nombre del monstruo
     * @param vida Los puntos de vida del monstruo
     * @param tipo El tipo de monstruo (ogro, troll, espectro)
     * @param fuerza El poder de ataque del monstruo
     * @return Nuevo Monstruo si los parámetros son válidos, null en caso contrario
     */
    public Monstruo crearMonstruo(String nombre, int vida,TipoMonstruo tipo , int fuerza) {

        Monstruo monstruo = null;

        if (nombre.length() > 0 && vida > 0 && fuerza > 0) {
            monstruo = new Monstruo(nombre, vida, tipo, fuerza);
        }

        return monstruo;
    }

    /**
     * Crea y persiste un nuevo monstruo en la BD.
     * 
     * Realiza validación mediante crearMonstruo() y luego
     * persiste el monstruo en la BD si es válido.
     * 
     * @param nombre El nombre del monstruo
     * @param vida Los puntos de vida iniciales
     * @param tipo El tipo de monstruo
     * @param fuerza El poder de ataque inicial
     * @return El Monstruo persistido con ID asignado, o null si hay error
     */
    public Monstruo guardarMonstruo(String nombre, int vida, TipoMonstruo tipo, int fuerza) {
        
        Monstruo monstruo = crearMonstruo(nombre, vida, tipo, fuerza);


        if (monstruo != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            em.persist(monstruo);
            em.getTransaction().commit();
            System.out.println("Monstruo guardado con id: " + monstruo.getId());

        } catch (Exception e) {
            System.out.println("Error al guarda el Monstruo " + e.getMessage());
            return null;
        }
        }
        
        return monstruo;
    }


    /**
     * Modifica el nombre de un monstruo existente.
     * 
     * @param nombre El nuevo nombre del monstruo
     * @param id El ID del monstruo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarNombre(String nombre, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Monstruo monstruo = em.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setNombre(nombre);
                em.merge(monstruo);
                em.getTransaction().commit();
                System.out.println("Nombre del monstruo modificado correctamente");
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
     * Modifica los puntos de vida de un monstruo existente.
     * 
     * @param vida El nuevo valor de vida del monstruo
     * @param id El ID del monstruo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarVida(int vida, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Monstruo monstruo = em.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setVida(vida);
                em.merge(monstruo);
                em.getTransaction().commit();
                System.out.println("Vida del monstruo modificada correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar la vida: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica el poder de ataque de un monstruo existente.
     * 
     * @param fuerza El nuevo valor de fuerza del monstruo
     * @param id El ID del monstruo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarFuerza(int fuerza, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Monstruo monstruo = em.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setFuerza(fuerza);
                em.merge(monstruo);
                em.getTransaction().commit();
                System.out.println("Fuerza del monstruo modificada correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar la fuerza: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica el tipo de un monstruo existente.
     * 
     * @param tipo El nuevo tipo del monstruo (ogro, troll, espectro)
     * @param id El ID del monstruo a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarTipo(TipoMonstruo tipo, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Monstruo monstruo = em.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setTipo(tipo);
                em.merge(monstruo);
                em.getTransaction().commit();
                System.out.println("Tipo del monstruo modificado correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el tipo: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
    /**
     * Obtiene un monstruo de la BD por su ID.
     * 
     * @param id El ID único del monstruo
     * @return El monstruo encontrado, o null si no existe
     */
    public Monstruo obtenerMonstruo(int id) {

    Monstruo monstruo = null;

        try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            monstruo = em.find (Monstruo.class, id);

        } catch (Exception e) {
            System.out.println("Error al obtener el dragón " + e.getMessage());
            return monstruo;
        }

        return monstruo;
    }
    /**
     * Elimina un monstruo de la BD por su ID.
     * 
     * @param id El ID del monstruo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarMonstruo(int id) {

        boolean eliminado = false;

        try (EntityManager em = HibernateUtil.getEntityManager()) {


            em.getTransaction().begin();

            Monstruo monstruo = em.find(Monstruo.class, id);

            if (monstruo != null) {
                em.remove(monstruo);
                em.getTransaction().commit();
                eliminado = true;
                
                System.out.println("Eliminado con éxito");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el Monstruo");
            return eliminado;
        }
        
        return eliminado;
    }
}



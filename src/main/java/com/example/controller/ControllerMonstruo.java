package com.example.controller;


import com.example.model.HibernateUtil;
import com.example.model.Monstruo;
import com.example.model.TipoMonstruo;

import jakarta.persistence.EntityManager;


public class ControllerMonstruo {
    

    public Monstruo crearMonstruo(String nombre, int vida,TipoMonstruo tipo , int fuerza) {

        Monstruo monstruo = null;

        if (nombre.length() > 0 && vida > 0 && fuerza > 0) {
            monstruo = new Monstruo(nombre, vida, tipo, fuerza);
        }

        return monstruo;
    }



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
     * Modifica el nombre del monstruo
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
     * Modifica la vida del monstruo
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
     * Modifica la fuerza del monstruo
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
     * Modifica el tipo del monstruo
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



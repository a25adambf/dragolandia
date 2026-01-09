package com.example.controller;


import com.example.model.Hechizo;
import com.example.model.HibernateUtil;
import com.example.model.Mago;

import jakarta.persistence.EntityManager;


public class ControllerMago {
    

    public Mago crearMago(String nombre, int vida, int nivelMagia) {

        Mago mago = null;

        if (nombre.length() > 0 && vida > 0 && nivelMagia > 0) {
            mago = new Mago(nombre, vida, nivelMagia);
        }

        return mago;
    }



    public Mago guardarMago(String nombre, int vida, int nivelMagia) {
        
        Mago mago = crearMago(nombre, vida, nivelMagia);

        if (mago != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            em.persist(mago);
            em.getTransaction().commit();

            System.out.println("Mago guardado con id: " + mago.getId());

        } catch (Exception e) {
            System.out.println("Error al guarda el mago " + e.getMessage());
            return null;
        }
        }
        
        return mago;
    }


    /**
     * Modifica el nombre del mago
     */
    public boolean modificarNombre(String nombre, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Mago mago = em.find(Mago.class, id);

            if (mago != null) {
                mago.setNombre(nombre);
                em.merge(mago);
                em.getTransaction().commit();
                System.out.println("Nombre del mago modificado correctamente");
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
     * Modifica la vida del mago
     */
    public boolean modificarVida(int vida, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Mago mago = em.find(Mago.class, id);

            if (mago != null) {
                mago.setVida(vida);
                em.merge(mago);
                em.getTransaction().commit();
                System.out.println("Vida del mago modificada correctamente");
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
     * Modifica el nivel de magia del mago
     */
    public boolean modificarNivelMagia(int nivelMagia, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Mago mago = em.find(Mago.class, id);

            if (mago != null) {
                mago.setNivelMagia(nivelMagia);
                em.merge(mago);
                em.getTransaction().commit();
                System.out.println("Nivel de magia del mago modificado correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nivel de magia: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }


    /**
     * Añade un hechizo al mago (sin permitir duplicados)
     */
    public boolean anadirHechizo(int magoId, int hechizoId) {
        boolean anadido = false;

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Mago mago = em.find(Mago.class, magoId);
            Hechizo hechizo = em.find(Hechizo.class, hechizoId);

            if (mago != null && hechizo != null) {
                // Verificar si el hechizo ya existe en la lista del mago
                if (!hechizoYaExisteEnMago(mago, hechizoId)) {
                    mago.addConjuro(hechizo);
                    em.merge(mago);
                    em.getTransaction().commit();
                    System.out.println("Hechizo añadido al mago correctamente");
                    anadido = true;
                } else {
                    System.out.println("El mago ya tiene este hechizo");
                    em.getTransaction().commit();
                }
            } else {
                System.out.println("Mago o Hechizo no encontrado");
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            System.out.println("Error al añadir hechizo al mago: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return anadido;
    }

    /**
     * Verifica si un hechizo ya existe en la lista de conjuros del mago
     */
    private boolean hechizoYaExisteEnMago(Mago mago, int hechizoId) {
        boolean existe = false;

        if (mago.getConjuros() != null || mago.getConjuros().isEmpty() == false) {
            for (Hechizo hechizo : mago.getConjuros()) {
                if (hechizo.getId() == hechizoId) {
                    existe = true;
                }
            }
        }
        
        return existe;
    }

    /**
     * Elimina un hechizo del mago
     */
    public boolean eliminarHechizo(int magoId, int hechizoId) {
        boolean eliminado = false;

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Mago mago = em.find(Mago.class, magoId);
            Hechizo hechizo = em.find(Hechizo.class, hechizoId);

            if (mago != null && hechizo != null) {
                mago.eliminarConjuro(hechizo);
                em.merge(mago);
                em.getTransaction().commit();
                System.out.println("Hechizo eliminado del mago correctamente");
                eliminado = true;
            } else {
                System.out.println("Mago o Hechizo no encontrado");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar hechizo del mago: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return eliminado;
    }

    /**
     * Obtiene todos los hechizos de un mago
     */
    public java.util.List<Hechizo> obtenerHechizos(int magoId) {
        
        java.util.List<Hechizo> hechizos = new java.util.ArrayList<>();

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Mago mago = em.find(Mago.class, magoId);
            if (mago != null && mago.getConjuros() != null) {
                hechizos = new java.util.ArrayList<>(mago.getConjuros());
            }
            return hechizos;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad de hechizos que tiene un mago
     */
    public int contarHechizos(int magoId) {
        int numHechizos  = 0;

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Mago mago = em.find(Mago.class, magoId);
            if (mago != null && mago.getConjuros() != null) {
                numHechizos = mago.getConjuros().size();
            }
            return numHechizos;
        } finally {
            em.close();
        }
    }


    public boolean eliminarMago(int id) {

        boolean eliminado = false;

        try (EntityManager em = HibernateUtil.getEntityManager()) {

            em.getTransaction().begin();

            Mago mago = em.find(Mago.class, id);

            if (mago != null) {
                em.remove(mago);
                em.getTransaction().commit();
                eliminado = true;
                
                System.out.println("Eliminado con éxito");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el mago");
            return eliminado;
        }
        
        return eliminado;
    }
}


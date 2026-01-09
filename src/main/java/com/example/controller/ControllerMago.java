package com.example.controller;


import com.example.model.Dragon;
import com.example.model.Hechizo;
import com.example.model.HibernateUtil;
import com.example.model.Mago;

import jakarta.persistence.EntityManager;

/**
 * Clase ControllerMago - Controlador para operaciones CRUD de magos.
 * 
 * Este controlador gestiona todas las operaciones de persistencia
 * relacionadas con los magos (Mago) incluyendo:
 * - Crear, leer, actualizar y eliminar magos (CRUD)
 * - Modificar atributos específicos (nombre, vida, nivel de magia)
 * - Gestionar hechizos asociados a cada mago
 * - Verificar y contar hechizos del mago
 * 
 * Implementa el patrón DAO (Data Access Object) usando Jakarta Persistence.
 * 
 * Métodos principales:
 * - crearMago(): Crea nueva instancia en memoria
 * - guardarMago(): Persiste mago en BD
 * - obtenerMago(): Recupera mago de BD por ID
 * - modificarNombre/Vida/NivelMagia(): Actualiza atributos
 * - anadirHechizo/eliminarHechizo(): Gestiona hechizos del mago
 * - obtenerHechizos/contarHechizos(): Consulta hechizos
 * - eliminarMago(): Borra mago de BD
 * 
 * @see Mago
 * @see HibernateUtil
 * @see Hechizo
 */
public class ControllerMago {
    
    /**
     * Crea una nueva instancia de Mago en memoria sin persistencia.
     * 
     * Valida que los parámetros sean válidos:
     * - nombre con longitud > 0
     * - vida > 0
     * - nivelMagia > 0
     * 
     * @param nombre El nombre del mago
     * @param vida Los puntos de vida del mago
     * @param nivelMagia El nivel de poder mágico del mago
     * @return Nuevo Mago si los parámetros son válidos, null en caso contrario
     */
    public Mago crearMago(String nombre, int vida, int nivelMagia) {

        Mago mago = null;

        if (nombre.length() > 0 && vida > 0 && nivelMagia > 0) {
            mago = new Mago(nombre, vida, nivelMagia);
        }

        return mago;
    }

    /**
     * Crea y persiste un nuevo mago en la BD.
     * 
     * Realiza validación mediante crearMago() y luego
     * persiste el mago en la BD si es válido.
     * 
     * @param nombre El nombre del mago
     * @param vida Los puntos de vida iniciales
     * @param nivelMagia El nivel de poder mágico inicial
     * @return El Mago persistido con ID asignado, o null si hay error
     */
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
     * Modifica el nombre de un mago existente.
     * 
     * @param nombre El nuevo nombre del mago
     * @param id El ID del mago a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
     * Modifica los puntos de vida de un mago existente.
     * 
     * @param vida El nuevo valor de vida del mago
     * @param id El ID del mago a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
     * Modifica el nivel de poder mágico de un mago existente.
     * 
     * @param nivelMagia El nuevo nivel de magia del mago
     * @param id El ID del mago a modificar
     * @return true si se modificó correctamente, false en caso contrario
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
     * Añade un hechizo al mago si no lo tiene ya.
     * 
     * Verifica que el mago y hechizo existan y que el mago
     * no tenga el hechizo previamente para evitar duplicados.
     * 
     * @param magoId El ID del mago
     * @param hechizoId El ID del hechizo a añadir
     * @return true si se añadió correctamente, false en caso contrario
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
     * Verifica si un hechizo con ID específico ya existe en la lista de conjuros del mago.
     * 
     * Método auxiliar privado utilizado para evitar duplicados al añadir hechizos.
     * 
     * @param mago El mago del cual verificar hechizos
     * @param hechizoId El ID del hechizo a verificar
     * @return true si el mago ya tiene ese hechizo, false en caso contrario
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
     * Elimina un hechizo del mago.
     * 
     * @param magoId El ID del mago
     * @param hechizoId El ID del hechizo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
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
     * Obtiene la lista de todos los hechizos conocidos por un mago.
     * 
     * @param magoId El ID del mago
     * @return Lista de hechizos del mago, o lista vacía si no hay hechizos
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
     * Cuenta la cantidad total de hechizos conocidos por un mago.
     * 
     * @param magoId El ID del mago
     * @return El número de hechizos del mago, 0 si no hay hechizos
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

    /**
     * Obtiene un mago de la BD por su ID.
     * 
     * @param id El ID único del mago
     * @return El mago encontrado, o null si no existe
     */
    public Mago obtenerMago(int id) {

    Mago mago = null;

        try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            mago = em.find (Mago.class, id);

        } catch (Exception e) {
            System.out.println("Error al obtener el dragón " + e.getMessage());
            return null;
        }

        return mago;
    }

    /**
     * Elimina un mago de la BD por su ID.
     * 
     * @param id El ID del mago a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
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


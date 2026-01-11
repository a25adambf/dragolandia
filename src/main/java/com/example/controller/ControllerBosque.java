package com.example.controller;


import com.example.model.HibernateUtil;
import com.example.model.Monstruo;

import jakarta.persistence.EntityManager;

import com.example.model.Bosque;
import com.example.model.Dragon;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase ControllerBosque - Controlador para operaciones CRUD del bosque.
 * 
 * Este controlador gestiona todas las operaciones de persistencia
 * relacionadas con el bosque (Bosque) incluyendo:
 * - Crear, leer, actualizar y eliminar bosque (CRUD)
 * - Modificar atributos (nombre, nivel de peligro, monstruo jefe)
 * - Gestionar la lista de monstruos del bosque
 * - Búsqueda de monstruos en el bosque
 * 
 * Implementa el patrón DAO (Data Access Object) usando Jakarta Persistence.
 * 
 * Métodos principales:
 * - crearBosque(): Crea nueva instancia en memoria
 * - guardarBosque(): Persiste bosque en BD
 * - obtenerBosque(): Recupera bosque de BD por ID
 * - modificarNombre/NivelPeligro/MonstruoJefe(): Actualiza atributos
 * - anadirMonstruo/eliminarMonstruo(): Gestiona monstruos
 * - obtenerMonstruos/buscarMonstruoEnBosque(): Consulta monstruos
 * - eliminarBosque(): Borra bosque de BD
 * 
 * @see Bosque
 * @see HibernateUtil
 * @see Monstruo
 * @see Dragon
 */
public class ControllerBosque {
    
    /**
     * Crea una nueva instancia de Bosque en memoria sin persistencia.
     * 
     * Valida que los parámetros sean válidos:
     * - nombre con longitud > 0
     * - nivelPeligro > 0
     * - dragon no null
     * 
     * @param nombre El nombre del bosque
     * @param nivelPeligro El nivel de dificultad del bosque
     * @param dragon El dragón aliado del bosque
     * @return Nuevo Bosque si los parámetros son válidos, null en caso contrario
     */
    public Bosque crearBosque(String nombre, int nivelPeligro, Dragon dragon) {

        Bosque bosque = null;

        if (nombre.length() > 0 && nivelPeligro > 0 && dragon != null) {
            bosque = new Bosque(nombre, nivelPeligro, dragon);
        }

        return bosque;
    }

    /**
     * Crea y persiste un nuevo bosque en la BD.
     * 
     * Realiza validación mediante crearBosque() y luego
     * persiste el bosque en la BD si es válido.
     * 
     * @param nombre El nombre del bosque
     * @param nivelPeligro El nivel de dificultad inicial
     * @param dragon El dragón aliado del bosque
     * @return El Bosque persistido con ID asignado, o null si hay error
     */
    public Bosque guardarBosque(String nombre, int nivelPeligro, Dragon dragon) {
        
        Bosque bosque = crearBosque(nombre, nivelPeligro, dragon);

        if (bosque != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
                em.getTransaction().begin();

                em.persist(bosque);
                em.getTransaction().commit();
                System.out.println("Bosque guardado con id: " + bosque.getId());

            } catch (Exception e) {
                System.out.println("Error al guarda el Bosque " + e.getMessage());
                
                return null;
            }
        }
        
        return bosque;
    }


    /**
    * Modifica el nombre del bosque.
    * 
    * @param nombre El nuevo nombre del bosque
    * @param id El ID del bosque a modificar
    * @return true si se modificó correctamente, false en caso contrario
    */
    public boolean modificarNombre(String nombre, int id) {
        
        boolean modificado = false;
        
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNombre(nombre);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Nombre del bosque modificado correctamente");

                modificado = true;
            } else {
                em.getTransaction().commit();
                modificado = false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nombre: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return modificado;
        } finally {
            em.close();
        }

        return modificado;
    }

    /**
     * Modifica el nivel de peligro del bosque.
     * 
     * @param nivelPeligro El nuevo nivel de peligro/dificultad
     * @param id El ID del bosque a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarNivelPeligro(int nivelPeligro, int id) {

        boolean modificado = false;


        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNivelPeligro(nivelPeligro);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Nivel de peligro del bosque modificado correctamente");
                
                modificado = true;

            } else {
                em.getTransaction().commit();
                modificado =  false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nivel de peligro: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return modificado;
        } finally {
            em.close();
        }

        return modificado;
    }

    /**
     * Modifica el monstruo jefe del bosque.
     * 
     * Verifica que el monstruo exista en la lista del bosque
     * antes de establecerlo como jefe.
     * 
     * @param monstruoJefeId El ID del nuevo monstruo jefe
     * @param bosqueId El ID del bosque a modificar
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarMonstruoJefe(int monstruoJefeId, int bosqueId) {

        boolean modificado = false;

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, bosqueId);
            Monstruo monstruo = em.find(Monstruo.class, monstruoJefeId);

            if (bosque != null && monstruo != null) {
                // Comprobar que el monstruo existe en la lista de monstruos del bosque
                boolean existeEnLista = false;
                List<Monstruo> lista = bosque.getMonstruos();
                if (lista != null) {
                    for (Monstruo m : lista) {
                        if (m != null && m.getId() == monstruoJefeId) {
                            existeEnLista = true;
                            break;
                        }
                    }
                }

                if (!existeEnLista) {
                    em.getTransaction().commit();
                    System.out.println("El monstruo no existe en la lista del bosque");
                    modificado =  false;
                }

                bosque.setMonstruoJefe(monstruo);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Monstruo jefe del bosque modificado correctamente");
                modificado = true;
            } else {
                em.getTransaction().commit();
                modificado = false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el monstruo jefe: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return modificado;
        } finally {
            em.close();
        }

        return modificado;
    }

    /**
     * Obtiene un bosque de la BD por su ID.
     * 
     * @param id El ID único del bosque
     * @return El bosque encontrado, o null si no existe
     */
    public Bosque obtenerBosque(int id) {

        Bosque bosque = null;

        try (EntityManager em = HibernateUtil.getEntityManager()) {
            
            em.getTransaction().begin();

            bosque = em.find(Bosque.class, id);

        } catch (Exception e) {
            System.out.println("Error al obtener el bosque " + e.getMessage());
            return bosque;
        }

        return bosque;
    }

    /**
     * Obtiene la lista de monstruos de un bosque.
     * 
     * @param bosqueId El ID del bosque
     * @return Lista de monstruos del bosque, o null si no existe el bosque
     */
    public List<Monstruo> obtenerMonstruos(int bosqueId) {
        List<Monstruo> lista = null;
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, bosqueId);
            if (bosque != null) {
                lista = bosque.getMonstruos();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los monstruos: " + e.getMessage());
            return lista;
        }

        return lista;
    }

    /**
     * Añade un monstruo a la lista del bosque.
     * 
     * Verifica que no exista un duplicado antes de añadir.
     * 
     * @param bosqueId El ID del bosque
     * @param monstruoId El ID del monstruo a añadir
     * @return true si se añadió correctamente, false en caso contrario
     */
    public boolean anadirMonstruo(int bosqueId, int monstruoId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, bosqueId);
            Monstruo monstruo = em.find(Monstruo.class, monstruoId);

            if (bosque != null && monstruo != null) {
                if (bosque.getMonstruos() == null) {
                    bosque.setMonstruos(new ArrayList<>());
                }
                if (!bosque.getMonstruos().contains(monstruo)) {
                    bosque.getMonstruos().add(monstruo);
                    em.merge(bosque);
                    em.getTransaction().commit();
                    System.out.println("Monstruo añadido al bosque correctamente");
                    return true;
                } else {
                    em.getTransaction().commit();
                    System.out.println("El monstruo ya está en la lista del bosque");
                    return false;
                }
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al añadir el monstruo: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un monstruo de la lista del bosque.
     * 
     * @param bosqueId El ID del bosque
     * @param monstruoId El ID del monstruo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarMonstruo(int bosqueId, int monstruoId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, bosqueId);
            Monstruo monstruo = em.find(Monstruo.class, monstruoId);

            if (bosque != null && monstruo != null && bosque.getMonstruos() != null) {
                boolean removed = bosque.getMonstruos().removeIf(m -> m != null && m.getId() == monstruoId);
                if (removed) {
                    em.merge(bosque);
                    em.getTransaction().commit();
                    System.out.println("Monstruo eliminado del bosque correctamente");
                    return true;
                } else {
                    em.getTransaction().commit();
                    System.out.println("El monstruo no estaba en la lista del bosque");
                    return false;
                }
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el monstruo: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Busca un monstruo dentro de la lista de un bosque por ID.
     * 
     * @param bosqueId El ID del bosque
     * @param monstruoId El ID del monstruo a buscar
     * @return El monstruo si se encuentra, null en caso contrario
     */
    public Monstruo buscarMonstruoEnBosque(int bosqueId, int monstruoId) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, bosqueId);
            if (bosque != null && bosque.getMonstruos() != null) {
                for (Monstruo m : bosque.getMonstruos()) {
                    if (m != null && m.getId() == monstruoId) {
                        return m;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el monstruo: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Elimina un bosque de la BD por su ID.
     * 
     * @param id El ID del bosque a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarBosque(int id) {

        boolean eliminado = false;

        try (EntityManager em = HibernateUtil.getEntityManager()){

            em.getTransaction().begin();

            Bosque bosque = em.find(Bosque.class, id);

            if (bosque != null) {
                em.remove(bosque);
                em.getTransaction().commit();
                eliminado = true;
                
                System.out.println("Eliminado con éxito");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el Bosque" + e.getMessage());
            return eliminado;
        }
        
        return eliminado;
    }
}



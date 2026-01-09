package com.example.controller;


import com.example.model.HibernateUtil;
import com.example.model.Monstruo;

import jakarta.persistence.EntityManager;

import com.example.model.Bosque;
import com.example.model.Dragon;
import java.util.List;
import java.util.ArrayList;


public class ControllerBosque {
    
    /**
     * Crea un objeto bosque
     * @param nombre
     * @param nivelPeligro
     * @param monstruoJefe
     * @return
     */
    public Bosque crearBosque(String nombre, int nivelPeligro, Dragon dragon) {

        Bosque bosque = null;

        if (nombre.length() > 0 && nivelPeligro > 0 && dragon != null) {
            bosque = new Bosque(nombre, nivelPeligro, dragon);
        }

        return bosque;
    }

    /**
     * Crea y añade un objeto bosque a la base de datos
     * @param nombre
     * @param nivelPeligro
     * @param monstruoJefe
     * @return
     */

    public boolean guardarBosque(String nombre, int nivelPeligro, Dragon dragon) {
        
        Bosque bosque = crearBosque(nombre, nivelPeligro, dragon);

        boolean guardado = false;

        if (bosque != null) {
            
            try (EntityManager em = HibernateUtil.getEntityManager()) {
            
                em.getTransaction().begin();

                em.persist(bosque);
                em.getTransaction().commit();
                System.out.println("Bosque guardado con id: " + bosque.getId());
                guardado = true;

            } catch (Exception e) {
                System.out.println("Error al guarda el Bosque " + e.getMessage());
                
                return guardado;
            }
        }
        
        return guardado;
    }


   /**
    * Modifica el nombre del bosque
    * @param nombre
    * @param id
    * @return
    */
    public boolean modificarNombre(String nombre, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNombre(nombre);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Nombre del bosque modificado correctamente");
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
     * Modifica el nivel de peligro del bosque
     * @param nivelPeligro
     * @param id
     * @return
     */
    public boolean modificarNivelPeligro(int nivelPeligro, int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Bosque bosque = em.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNivelPeligro(nivelPeligro);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Nivel de peligro del bosque modificado correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el nivel de peligro: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica el monstruo jefe del bosque
     * @param monstruoJefeId
     * @param bosqueId
     * @return
     */
    public boolean modificarMonstruoJefe(int monstruoJefeId, int bosqueId) {
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
                    return false;
                }

                bosque.setMonstruoJefe(monstruo);
                em.merge(bosque);
                em.getTransaction().commit();
                System.out.println("Monstruo jefe del bosque modificado correctamente");
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el monstruo jefe: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }


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
     * Obtiene la lista de monstruos de un bosque
     * @param bosqueId
     * @return lista de monstruos o null si no existe el bosque
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
     * Añade un monstruo a la lista del bosque (por id de monstruo)
     * @param bosqueId
     * @param monstruoId
     * @return true si se añadió, false si no
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
     * Elimina un monstruo de la lista del bosque (por id de monstruo)
     * @param bosqueId
     * @param monstruoId
     * @return true si se eliminó, false si no
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
     * Busca un monstruo dentro de la lista de un bosque por id
     * @param bosqueId
     * @param monstruoId
     * @return Monstruo si se encuentra, null si no
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
     * Elimina un bosque de la base de datos dado su id
     * @param id
     * @return
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



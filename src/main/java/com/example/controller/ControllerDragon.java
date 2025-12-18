package com.example.controller;

import org.hibernate.*;

import com.example.model.HibernateUtil;
import com.example.model.Dragon;


public class ControllerDragon {
    
    Session session = null;


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
            
            try (SessionFactory factory = HibernateUtil.getSessionFactory()) {
            
            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.persist(dragon);
            tx.commit();
            System.out.println("Dragon guardado con id: " + dragon.getId());
            guardado = true;

        } catch (Exception e) {
            System.out.println("Error al guarda el Dragon " + e.getMessage());
            return guardado;
        }
        }
        
        return guardado;
    }


    public boolean modificarNombre(String nombre, int id) {

        boolean modificado = true;

        try (SessionFactory factory = HibernateUtil.getSessionFactory()) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            Dragon dragon = session.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setNombre(nombre);
                session.merge(dragon);
                tx.commit();
                System.out.println("Nombre modificado correctamente");

            } else modificado = false;

        } catch (Exception e) {
            System.out.println("Error al modificar el nombre " + e.getMessage());
            modificado = false;
            return modificado;
        }

        return modificado;
    }
    

    public boolean modificarIntesidadFuego(int intensidadFuego, int id) {

        boolean modificado = true;

        try (SessionFactory factory = HibernateUtil.getSessionFactory()) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            Dragon dragon = session.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setIntensidadFuego(intensidadFuego);
                session.merge(dragon);
                tx.commit();
                System.out.println("Intensidad de fuego modificada correctamente");

            } else modificado = false;

        } catch (Exception e) {
            System.out.println("Error al modificar la intensidad de fuego " + e.getMessage());
            modificado = false;
            return modificado;
        }

        return modificado;
    }

    public boolean modificarResistencia(int resistencia, int id) {

        boolean modificado = true;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            Transaction tx = session.beginTransaction();

            Dragon dragon = session.find(Dragon.class, id);

            if (dragon != null) {
                dragon.setResistencia(resistencia);
                session.merge(dragon);
                tx.commit();
                System.out.println("Resistencia modificada correctamente");

            } else modificado = false;

        } catch (Exception e) {
            System.out.println("Error al modificar la resistencia " + e.getMessage());
            modificado = false;
            return modificado;
        }

        return modificado;
    }


    public boolean eliminarDragon(int id) {

        boolean eliminado = false;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Dragon dragon = session.find(Dragon.class, id);

            if (dragon != null) {
                session.remove(dragon);
                tx.commit();
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



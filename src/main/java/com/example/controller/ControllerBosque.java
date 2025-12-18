package com.example.controller;

import org.hibernate.*;

import com.example.model.HibernateUtil;
import com.example.model.Monstruo;
import com.example.model.Bosque;


public class ControllerBosque {
    
    Session session = null;


    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe) {

        Bosque bosque = null;

        if (nombre.length() > 0 && nivelPeligro > 0 && monstruoJefe != null) {
            bosque = new Bosque(nombre, nivelPeligro, monstruoJefe);
        }

        return bosque;
    }



    public boolean guardarBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe) {
        
        Bosque bosque = crearBosque(nombre, nivelPeligro, monstruoJefe);

        boolean guardado = false;

        if (bosque != null) {
            
            try (SessionFactory factory = HibernateUtil.getSessionFactory()) {
            
            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.persist(bosque);
            tx.commit();
            System.out.println("Bosque guardado con id: " + bosque.getId());
            guardado = true;

        } catch (Exception e) {
            System.out.println("Error al guarda el Bosque " + e.getMessage());
            
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

            Bosque bosque = session.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNombre(nombre);
                session.merge(bosque);
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
    

    public boolean modificarNivelPeligro(int nivelPeligro, int id) {

        boolean modificado = true;

        try (SessionFactory factory = HibernateUtil.getSessionFactory()) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            Bosque bosque = session.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setNivelPeligro(nivelPeligro);
                session.merge(bosque);
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

    public boolean modificarMonstruoJefe(Monstruo monstruo, int id) {

        boolean modificado = true;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            Transaction tx = session.beginTransaction();

            Bosque bosque = session.find(Bosque.class, id);

            if (bosque != null) {
                bosque.setMonstruoJefe(monstruo);
                session.merge(bosque);
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



    public boolean eliminarBosque(int id) {

        boolean eliminado = false;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Bosque bosque = session.find(Bosque.class, id);

            if (bosque != null) {
                session.remove(bosque);
                tx.commit();
                eliminado = true;
                
                System.out.println("Eliminado con éxito");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el Bosque");
            return eliminado;
        }
        
        return eliminado;
    }
}



package com.example.controller;

import org.hibernate.*;

import com.example.model.HibernateUtil;
import com.example.model.Mago;


public class ControllerMago {
    
    Session session = null;


    public Mago crearMago(String nombre, int vida, int nivelMagia) {

        Mago mago = null;

        if (nombre.length() > 0 && vida > 0 && nivelMagia > 0) {
            mago = new Mago(nombre, vida, nivelMagia);
        }

        return mago;
    }



    public boolean guardarMago(String nombre, int vida, int nivelMagia) {
        
        Mago mago = crearMago(nombre, vida, nivelMagia);

        boolean guardado = false;

        if (mago != null) {
            
            try (SessionFactory factory = HibernateUtil.getSessionFactory()) {
            
            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.persist(mago);
            tx.commit();
            System.out.println("Mago guardado con id: " + mago.getId());
            guardado = true;

        } catch (Exception e) {
            System.out.println("Error al guarda el mago " + e.getMessage());
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

            Mago mago = session.find(Mago.class, id);

            if (mago != null) {
                mago.setNombre(nombre);
                session.merge(mago);
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
    

    public boolean modificarVida(int vida, int id) {

        boolean modificado = true;

        try (SessionFactory factory = HibernateUtil.getSessionFactory()) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            Mago mago = session.find(Mago.class, id);

            if (mago != null) {
                mago.setVida(vida);
                session.merge(mago);
                tx.commit();
                System.out.println("Vida modificada correctamente");

            } else modificado = false;

        } catch (Exception e) {
            System.out.println("Error al modificar la vida " + e.getMessage());
            modificado = false;
            return modificado;
        }

        return modificado;
    }

    public boolean modificarNivelMagia(int nivelMagia, int id) {

        boolean modificado = true;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            Transaction tx = session.beginTransaction();

            Mago mago = session.find(Mago.class, id);

            if (mago != null) {
                mago.setNivelMagia(nivelMagia);
                session.merge(mago);
                tx.commit();
                System.out.println("Nivel de magia modificado correctamente");

            } else modificado = false;

        } catch (Exception e) {
            System.out.println("Error al modificar el nivel de magia " + e.getMessage());
            modificado = false;
            return modificado;
        }

        return modificado;
    }


    public boolean eliminarMago(int id) {

        boolean eliminado = false;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Mago mago = session.find(Mago.class, id);

            if (mago != null) {
                session.remove(mago);
                tx.commit();
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


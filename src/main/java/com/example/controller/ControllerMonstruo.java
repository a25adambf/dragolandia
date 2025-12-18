package com.example.controller;

import org.hibernate.*;

import com.example.model.HibernateUtil;
import com.example.model.Monstruo;
import com.example.model.TipoMonstruo;


public class ControllerMonstruo {
    
    Session session = null;


    public Monstruo crearMonstruo(String nombre, int vida,TipoMonstruo tipo , int fuerza) {

        Monstruo monstruo = null;

        if (nombre.length() > 0 && vida > 0 && fuerza > 0) {
            monstruo = new Monstruo(nombre, vida, tipo, fuerza);
        }

        return monstruo;
    }



    public boolean guardarMonstruo(String nombre,TipoMonstruo tipo, int intensidadFuego, int resistencia) {
        
        Monstruo monstruo = crearMonstruo(nombre, intensidadFuego, tipo, resistencia);

        boolean guardado = false;

        if (monstruo != null) {
            
            try (SessionFactory factory = HibernateUtil.getSessionFactory()) {
            
            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.persist(monstruo);
            tx.commit();
            System.out.println("Monstruo guardado con id: " + monstruo.getId());
            guardado = true;

        } catch (Exception e) {
            System.out.println("Error al guarda el Monstruo " + e.getMessage());
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

            Monstruo monstruo = session.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setNombre(nombre);
                session.merge(monstruo);
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

            Monstruo monstruo = session.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setVida(vida);
                session.merge(monstruo);
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

    public boolean modificarFuerza(int fuerza, int id) {

        boolean modificado = true;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            Transaction tx = session.beginTransaction();

            Monstruo monstruo = session.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setFuerza(fuerza);
                session.merge(monstruo);
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

    public boolean modificarTipo(TipoMonstruo tipoMonstruo, int id) {

        boolean modificado = true;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            Transaction tx = session.beginTransaction();

            Monstruo monstruo = session.find(Monstruo.class, id);

            if (monstruo != null) {
                monstruo.setTipo(tipoMonstruo);
                session.merge(monstruo);
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


    public boolean eliminarMonstruo(int id) {

        boolean eliminado = false;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Monstruo monstruo = session.find(Monstruo.class, id);

            if (monstruo != null) {
                session.remove(monstruo);
                tx.commit();
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



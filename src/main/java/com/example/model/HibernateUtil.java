package com.example.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() { }

    public static SessionFactory getSessionFactory() {
        
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                
            } catch (Exception e) {
                throw new RuntimeException("Error al crear SessionFactory" + e.getMessage());
            }
        }

        return sessionFactory;
    }
}

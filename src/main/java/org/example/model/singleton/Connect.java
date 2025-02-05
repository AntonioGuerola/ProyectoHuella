package org.example.model.singleton;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connect {
    private static Connect instance;

    private SessionFactory sessionFactory;

    private Connect() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch (Exception e) {
            throw new RuntimeException("Error al contruir la SessionFactory");
        }
    }

    public static synchronized Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void close(){
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}

package org.example.model.dao;

import org.example.model.entities.Actividad;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.util.List;

public class ActividadDAO {

    private static final String FIND_BY_ID = "FROM Actividad a WHERE a.id = :id";
    private static final String FIND_ALL = "FROM Actividad";
    private static final String FIND_BY_NAME = "FROM Actividad a WHERE a.nombre = :nombre";

    public Actividad findById(Integer id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_ID);
        query.setParameter("id", id);
        Actividad actividad = (Actividad) query.getSingleResult();
        session.close();
        return actividad;
    }

    public Actividad findByName(String nombre) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_NAME);
        query.setParameter("nombre", nombre);
        Actividad actividad = (Actividad) query.getSingleResult();
        session.close();
        return actividad;
    }

    public List<Actividad> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Actividad> actividades = session.createQuery(FIND_ALL, Actividad.class).list();
        session.close();
        return actividades;
    }
}

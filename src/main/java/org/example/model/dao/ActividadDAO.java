package org.example.model.dao;

import org.example.model.entities.Actividad;
import org.example.model.entities.Categoria;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ActividadDAO implements DAO<Actividad, Integer> {

    private static final String FIND_BY_ID = "FROM Actividad a WHERE a.id = :id";
    private static final String FIND_ALL = "FROM Actividad";
    private static final String FIND_BY_NAME = "FROM Actividad a WHERE a.nombre = :nombre";

    @Override
    public Actividad save(Actividad entity) {
        return null;
    }

    @Override
    public Actividad delete(Actividad entity) {
        return null;
    }

    @Override
    public Actividad findById(Integer key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Actividad.class)
                    .setParameter("id", key)
                    .uniqueResult();
        }
    }

    @Override
    public List<Actividad> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Actividad.class).list();
        }
    }

    public Actividad findByName(String key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_NAME, Actividad.class)
                    .setParameter("nombre", key)
                    .uniqueResult();
        }
    }

    @Override
    public void close() {
        // No se necesita implementación en Hibernate
    }

    public static ActividadDAO buildActividadDAO() {
        return new ActividadDAO();
    }
}

package org.example.model.dao;

import org.example.model.entities.Huella;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class HuellaDAO implements DAO<Huella, Integer> {

    private static final String FIND_BY_ID = "FROM Huella h WHERE h.id = :id";
    private static final String FIND_ALL = "FROM Huella";

    @Override
    public Huella save(Huella entity) {
        if (entity == null) return null;

        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        }

        return entity;
    }

    @Override
    public Huella delete(Huella entity) {
        if (entity == null) return null;

        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        }

        return entity;
    }

    @Override
    public Huella findById(Integer key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Huella.class)
                    .setParameter("id", key)
                    .uniqueResult();
        }
    }

    @Override
    public List<Huella> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Huella.class).list();
        }
    }

    public static HuellaDAO buildHuellaDAO() {
        return new HuellaDAO();
    }

    @Override
    public void close() throws IOException {
        // No es necesario cerrar manualmente las sesiones de Hibernate aquí
    }
}

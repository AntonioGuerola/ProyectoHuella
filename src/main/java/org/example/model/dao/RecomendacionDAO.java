package org.example.model.dao;

import org.example.model.entities.Recomendacion;
import org.example.model.singleton.Connect;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class RecomendacionDAO implements DAO<Recomendacion, Integer> {
    private static final String FIND_BY_ID = "FROM Recomendacion c WHERE c.id = :id";
    private static final String FIND_ALL = "FROM Recomendacion";
    private static final String FIND_BY_CATEGORIA = "FROM Recomendacion r WHERE r.idCategoria.id = :categoriaId";

    @Override
    public Recomendacion save(Recomendacion entity) {
        return null;
    }

    @Override
    public Recomendacion delete(Recomendacion entity) {
        return null;
    }

    @Override
    public Recomendacion findById(Integer key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Recomendacion.class)
                    .setParameter("id", key)
                    .uniqueResult();
        }
    }

    @Override
    public List<Recomendacion> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Recomendacion.class).list();
        }
    }

    public List<Recomendacion> findByCategoria(Integer categoriaId) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_CATEGORIA, Recomendacion.class)
                    .setParameter("categoriaId", categoriaId)
                    .list();
        }
    }

    @Override
    public void close() throws IOException {
        // No es necesario cerrar la sesión aquí
    }

    public static RecomendacionDAO buildRecomendacionDAO() {
        return new RecomendacionDAO();
    }
}

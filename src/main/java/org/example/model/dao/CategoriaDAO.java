package org.example.model.dao;

import org.example.model.entities.Categoria;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class CategoriaDAO implements DAO<Categoria, Integer> {
    private static final String FIND_BY_ID = "FROM Categoria c WHERE c.id = :id";
    private static final String FIND_ALL = "FROM Categoria";

    @Override
    public Categoria save(Categoria entity) {
        return null;
    }

    @Override
    public Categoria delete(Categoria entity) {
        return null;
    }

    @Override
    public Categoria findById(Integer key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Categoria.class)
                    .setParameter("id", key)
                    .uniqueResult();
        }
    }

    @Override
    public List<Categoria> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Categoria.class).list();
        }
    }

    @Override
    public void close() throws IOException {
        // No es necesario cerrar sesiones aquí, se manejan automáticamente con try-with-resources
    }

    public static CategoriaDAO buildCategoriaDAO() {
        return new CategoriaDAO();
    }
}

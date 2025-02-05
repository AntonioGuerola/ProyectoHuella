package org.example.model.dao;

import org.example.model.entities.Habito;
import org.example.model.entities.HabitoId;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class HabitoDAO implements DAO<Habito, HabitoId> {

    private static final String FIND_BY_ID = "FROM Habito h WHERE h.id.idUsuario = :idUsuario AND h.id.idActividad = :idActividad";
    private static final String FIND_ALL = "FROM Habito";

    /**
     * Save or update a habit in the database.
     * @param entity The habit to save or update.
     * @return The saved or updated habit.
     */
    @Override
    public Habito save(Habito entity) {
        if (entity == null) return null;

        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        }

        return entity;
    }

    /**
     * Delete a habit.
     * @param entity The habit to delete.
     * @return The deleted habit.
     */
    @Override
    public Habito delete(Habito entity) {
        if (entity == null) return null;

        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        }

        return entity;
    }

    /**
     * Find a habit by its ID.
     * @param key The key consisting of user ID and activity ID.
     * @return The found habit, or null if not found.
     */
    @Override
    public Habito findById(HabitoId key) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Habito.class)
                    .setParameter("idUsuario", key.getIdUsuario())
                    .setParameter("idActividad", key.getIdActividad())
                    .uniqueResult();
        }
    }

    /**
     * Retrieve all habits.
     * @return List of all habits.
     */
    @Override
    public List<Habito> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Habito.class).list();
        }
    }

    public static HabitoDAO buildHabitoDAO() {
        return new HabitoDAO();
    }

    @Override
    public void close() throws IOException {

    }
}

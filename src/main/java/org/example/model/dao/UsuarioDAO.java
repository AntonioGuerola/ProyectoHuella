package org.example.model.dao;

import org.example.model.entities.Usuario;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UsuarioDAO {

    private static final String FIND_BY_EMAIL = "FROM Usuario u WHERE u.email = :email";
    private static final String FIND_BY_ID = "FROM Usuario u WHERE u.id = :id";
    private static final String FIND_ALL = "FROM Usuario";
    private static final String FINDHUELLASDEUSUARIO = "SELECT u FROM Usuario u LEFT JOIN FETCH u.huellas WHERE u.id = :userId";

    /**
     * Insert a new user in the database.
     * @param usuario The user to insert.
     */
    public void insertUsuario(Usuario usuario) {
        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            usuario.setFechaRegistro(java.time.Instant.now()); // Set registration date
            session.save(usuario);
            tx.commit();
        }
    }

    /**
     * Update an existing user.
     * @param usuario The user to update.
     */
    public void updateUsuario(Usuario usuario) {
        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(usuario);
            tx.commit();
        }
    }

    /**
     * Delete a user.
     * @param usuario The user to delete.
     */
    public void deleteUsuario(Usuario usuario) {
        try (Session session = Connect.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(usuario);
            tx.commit();
        }
    }

    /**
     * Find a user by ID.
     * @param id The ID to search.
     * @return The user found, or null if not found.
     */
    public Usuario findById(Integer id) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_ID, Usuario.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    /**
     * Find a user by email.
     * @param email The email to search.
     * @return The user found, or null if not found.
     */
    public Usuario findByEmail(String email) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_BY_EMAIL, Usuario.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    /**
     * Retrieve all users.
     * @return List of all users.
     */
    public List<Usuario> findAll() {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FIND_ALL, Usuario.class).list();
        }
    }

    public static UsuarioDAO buildUsuarioDAO() {
        return new UsuarioDAO();
    }

    public Usuario getUsuarioConHuellas(Integer userId) {
        try (Session session = Connect.getInstance().getSession()) {
            return session.createQuery(FINDHUELLASDEUSUARIO, Usuario.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }
}

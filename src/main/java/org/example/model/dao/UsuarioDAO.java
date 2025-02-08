package org.example.model.dao;

import org.example.model.entities.Usuario;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class UsuarioDAO {

    private final static String FINDUSERBYID = "FROM Usuario WHERE id = :id";
    private final static String FINDUSERBYEMAIL = "FROM Usuario WHERE email = :email";
    private final static String FINDHUELLASDEUSUARIO = "SELECT u FROM Usuario u LEFT JOIN FETCH u.huellas WHERE u.id = :userId";
    private final static String FINDHABITOSDEUSUARIO = "SELECT u FROM Usuario u LEFT JOIN FETCH u.habitos WHERE u.id = :userId";

    public void insertUsuario(Usuario user) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUsuario(Usuario user) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUsuario(Usuario user) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    public Usuario findUserByID(Integer id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FINDUSERBYID);
        query.setParameter("id", id);
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }

    public Usuario findUserByEmail(String email) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FINDUSERBYEMAIL);
        query.setParameter("email", email);

        Usuario user = (Usuario) query.getSingleResult(); // Permitimos que lance la excepción
        session.close();
        return user;
    }

    public List<Usuario> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Usuario> users = session.createQuery("FROM Usuario", Usuario.class).list();
        session.close();
        return users;
    }

    public Usuario getUsuarioConHuellas(Integer userId) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FINDHUELLASDEUSUARIO);
        query.setParameter("userId", userId);
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }

    public List<Usuario> getAllUsersWithFootprints() {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.huellas", Usuario.class);
        List<Usuario> users = (List<Usuario>) ((org.hibernate.query.Query<?>) query).list();
        session.close();
        return users;
    }

    public Usuario getUsuarioConHabitos(Integer userId) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FINDHABITOSDEUSUARIO);
        query.setParameter("userId", userId);
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }

}

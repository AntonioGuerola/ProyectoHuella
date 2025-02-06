package org.example.model.dao;

import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.util.List;

public class HuellaDAO {

    private static final String FIND_BY_ID = "FROM Huella h WHERE h.id = :id";
    private static final String FIND_ALL = "FROM Huella";
    private static final String FIND_BY_USER = "FROM Huella h WHERE h.idUsuario.id = :userId";
    private static final String DELETE_ALL_BY_USUARIO = "DELETE FROM Huella h WHERE h.idUsuario = :idUsuario";

    public void insertHuella(Huella huella) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.persist(huella);
        session.getTransaction().commit();
        session.close();
    }

    public void updateHuella(Huella huella) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.update(huella);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteHuella(Huella huella) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.remove(huella);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAllHuellasByUsuario(Usuario usuario) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        Query query = session.createQuery(DELETE_ALL_BY_USUARIO);
        query.setParameter("idUsuario", usuario);  // Cambié a pasar el objeto Usuario completo
        query.executeUpdate();  // Ejecutamos el DELETE
        session.getTransaction().commit();
        session.close();
    }

    public Huella findById(Integer id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_ID);
        query.setParameter("id", id);
        Huella huella = (Huella) query.getSingleResult();
        session.close();
        return huella;
    }

    public List<Huella> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Huella> huellas = session.createQuery(FIND_ALL, Huella.class).list();
        session.close();
        return huellas;
    }

    public List<Huella> findByUserId(Integer userId) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_USER);
        query.setParameter("userId", userId);
        List<Huella> huellas = query.getResultList();
        session.close();
        return huellas;
    }

    public static HuellaDAO buildHuellaDAO() {
        return new HuellaDAO();
    }
}

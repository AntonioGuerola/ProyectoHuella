package org.example.model.dao;

import org.example.model.entities.Habito;
import org.example.model.entities.HabitoId;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.util.List;

public class HabitoDAO {

    private static final String FIND_BY_ID = "FROM Habito h WHERE h.id.idUsuario = :idUsuario AND h.id.idActividad = :idActividad";
    private static final String FIND_ALL = "FROM Habito";

    public void insertHabito(Habito habito) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.merge(habito);
        session.getTransaction().commit();
        session.close();
    }

    public void updateHabito(Habito habito) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.update(habito);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteHabito(Habito habito) {
        Session session = Connect.getInstance().getSession();
        session.beginTransaction();
        session.remove(habito);
        session.getTransaction().commit();
        session.close();
    }

    public Habito findById(HabitoId id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_ID);
        query.setParameter("idUsuario", id.getIdUsuario());
        query.setParameter("idActividad", id.getIdActividad());
        Habito habito = (Habito) query.getSingleResult();
        session.close();
        return habito;
    }

    public List<Habito> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Habito> habitos = session.createQuery(FIND_ALL, Habito.class).list();
        session.close();
        return habitos;
    }

    public static HabitoDAO buildHabitoDAO() {
        return new HabitoDAO();
    }
}

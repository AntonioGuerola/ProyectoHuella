package org.example.model.dao;

import org.example.model.entities.Recomendacion;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.util.List;

public class RecomendacionDAO {

    private static final String FIND_BY_ID = "FROM Recomendacion c WHERE c.id = :id";
    private static final String FIND_ALL = "FROM Recomendacion";
    private static final String FIND_BY_CATEGORIA = "FROM Recomendacion r WHERE r.idCategoria.id = :categoriaId";

    public void insertRecomendacion(Recomendacion recomendacion) {
    }

    public void updateRecomendacion(Recomendacion recomendacion) {
    }

    public void deleteRecomendacion(Recomendacion recomendacion) {
    }

    public Recomendacion findById(Integer id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_ID);
        query.setParameter("id", id);
        Recomendacion recomendacion = (Recomendacion) query.getSingleResult();
        session.close();
        return recomendacion;
    }

    public List<Recomendacion> findByCategoria(Integer categoriaId) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_CATEGORIA, Recomendacion.class);
        query.setParameter("categoriaId", categoriaId);
        List<Recomendacion> recomendaciones = query.getResultList();
        session.close();
        return recomendaciones;
    }

    public List<Recomendacion> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Recomendacion> recomendaciones = session.createQuery(FIND_ALL, Recomendacion.class).list();
        session.close();
        return recomendaciones;
    }
}

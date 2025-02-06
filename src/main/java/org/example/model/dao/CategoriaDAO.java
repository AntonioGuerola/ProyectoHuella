package org.example.model.dao;

import org.example.model.entities.Categoria;
import org.example.model.singleton.Connect;
import org.hibernate.Session;
import jakarta.persistence.Query;
import java.util.List;

public class CategoriaDAO {
    private static final String FIND_BY_ID = "FROM Categoria c WHERE c.id = :id";
    private static final String FIND_ALL = "FROM Categoria";

    public Categoria findById(Integer id) {
        Session session = Connect.getInstance().getSession();
        Query query = session.createQuery(FIND_BY_ID);
        query.setParameter("id", id);
        Categoria categoria = (Categoria) query.getSingleResult();
        session.close();
        return categoria;
    }

    public List<Categoria> findAll() {
        Session session = Connect.getInstance().getSession();
        List<Categoria> categorias = session.createQuery(FIND_ALL, Categoria.class).list();
        session.close();
        return categorias;
    }

    public static CategoriaDAO buildCategoriaDAO() {
        return new CategoriaDAO();
    }
}

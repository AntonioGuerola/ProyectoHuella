package org.example.model.service;

import org.example.model.dao.CategoriaDAO;
import org.example.model.entities.Categoria;
import java.util.List;

public class CategoriaService {

    public Categoria getCategoriaById(Integer id) {
        try {
            return CategoriaDAO.buildCategoriaDAO().findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Categoria> getAllCategorias() {
        try {
            return CategoriaDAO.buildCategoriaDAO().findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CategoriaService buildCategoriaService() {
        return new CategoriaService();
    }
}

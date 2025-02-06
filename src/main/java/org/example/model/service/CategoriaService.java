package org.example.model.service;

import org.example.model.dao.CategoriaDAO;
import org.example.model.entities.Categoria;
import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public Categoria getCategoriaById(Integer id) {
        try {
            return categoriaDAO.findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Si no se encuentra la categoría, devuelve null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Categoria> getAllCategorias() {
        try {
            return categoriaDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CategoriaService buildCategoriaService() {
        return new CategoriaService();
    }
}

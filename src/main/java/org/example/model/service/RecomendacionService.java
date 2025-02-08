package org.example.model.service;

import org.example.model.dao.RecomendacionDAO;
import org.example.model.entities.Recomendacion;

import java.util.List;

public class RecomendacionService {
    private RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    public Recomendacion getRecomendacionById(Integer id) {
        try {
            return recomendacionDAO.findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recomendacion> getRecomendacionesByCategoria(Integer categoriaId) {
        try {
            return recomendacionDAO.findByCategoria(categoriaId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recomendacion> getAllRecomendaciones() {
        try {
            return recomendacionDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RecomendacionService buildRecomendacionService() {
        return new RecomendacionService();
    }
}

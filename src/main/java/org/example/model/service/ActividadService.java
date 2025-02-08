package org.example.model.service;

import org.example.model.dao.ActividadDAO;
import org.example.model.entities.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDAO actividadDAO = new ActividadDAO();

    public Actividad getActividadById(Integer id) {
        try {
            return actividadDAO.findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Actividad getActividadByName(String nombre) {
        try {
            return actividadDAO.findByName(nombre);
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Actividad> getAllActividades() {
        try {
            return actividadDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ActividadService buildActividadService() {
        return new ActividadService();
    }
}

package org.example.model.service;

import org.example.model.dao.HabitoDAO;
import org.example.model.entities.Habito;
import org.example.model.entities.HabitoId;
import org.example.model.utils.JavaFXUtils;
import java.util.List;

public class HabitoService {
    private HabitoDAO habitoDAO = new HabitoDAO();

    public boolean createHabito(Habito habito) {
        if (habito == null || habito.getId() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL CREAR HÁBITO", "El hábito es inválido.");
            return false;
        }

        try {
            habitoDAO.insertHabito(habito);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHabito(Habito habito) {
        if (habito == null || habito.getId() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL ACTUALIZAR HÁBITO", "El hábito es inválido.");
            return false;
        }

        try {
            habitoDAO.updateHabito(habito);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteHabito(Habito habito) {
        try {
            habitoDAO.deleteHabito(habito);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Habito getHabitoById(HabitoId id) {
        try {
            return habitoDAO.findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Si no se encuentra el hábito, devolvemos null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Habito> getAllHabitos() {
        try {
            return habitoDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HabitoService buildHabitoService() {
        return new HabitoService();
    }
}

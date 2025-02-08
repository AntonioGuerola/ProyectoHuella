package org.example.model.service;

import org.example.model.dao.HabitoDAO;
import org.example.model.dao.HuellaDAO;
import org.example.model.entities.Habito;
import org.example.model.entities.HabitoId;
import org.example.model.entities.Huella;
import org.example.model.utils.JavaFXUtils;
import java.util.List;

public class HabitoService {

    public boolean createHabito(Habito habito) {
        if (habito == null || habito.getId() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL CREAR HÁBITO", "El hábito es inválido.");
            return false;
        }

        try {
            HabitoDAO.buildHabitoDAO().insertHabito(habito);
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
            HabitoDAO.buildHabitoDAO().updateHabito(habito);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHabito(Habito habito) {
        if (habito == null || habito.getId() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HÁBITO", "El hábito es inválida.");
            return false;
        }

        try {
            HabitoDAO.buildHabitoDAO().deleteHabito(habito);
            return true;
        } catch (Exception e) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HÁBITO", "Ocurrió un error al eliminar el hábito.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllHabitosByUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HÁBITOS", "El ID del usuario es inválido.");
            return false;
        }

        try {
            HabitoDAO.buildHabitoDAO().deleteAllHabitosByUsuario(idUsuario);
            return true;
        } catch (Exception e) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HÁBITOS", "Ocurrió un error al intentar eliminar los hábitos.");
            e.printStackTrace();
            return false;
        }
    }

    public Habito getHabitoById(HabitoId id) {
        try {
            return HabitoDAO.buildHabitoDAO().findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Si no se encuentra el hábito, devolvemos null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Habito> getAllHabitos() {
        try {
            return HabitoDAO.buildHabitoDAO().findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HabitoService buildHabitoService() {
        return new HabitoService();
    }
}

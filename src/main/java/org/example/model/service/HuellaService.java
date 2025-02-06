package org.example.model.service;

import org.example.model.dao.HuellaDAO;
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.utils.JavaFXUtils;
import java.util.List;

public class HuellaService {

    public boolean createHuella(Huella huella) {
        if (huella == null || huella.getIdUsuario() == null || huella.getIdActividad() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL CREAR HUELLA", "La huella de carbono es inválida.");
            return false;
        }

        try {
            HuellaDAO.buildHuellaDAO().insertHuella(huella);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHuella(Huella huella) {
        if (huella == null || huella.getIdUsuario() == null || huella.getIdActividad() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL ACTUALIZAR HUELLA", "La huella de carbono es inválida.");
            return false;
        }

        try {
            HuellaDAO.buildHuellaDAO().updateHuella(huella);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteHuella(Huella huella) {
        try {
            HuellaDAO.buildHuellaDAO().deleteHuella(huella);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteAllHuellasByUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HUELLAS", "El usuario es inválido.");
            return false;
        }

        try {
            HuellaDAO.buildHuellaDAO().deleteAllHuellasByUsuario(usuario);  // Pasamos el objeto usuario aquí
            return true;
        } catch (Exception e) {
            JavaFXUtils.showErrorAlert("ERROR AL ELIMINAR HUELLAS", "Ocurrió un error al intentar eliminar las huellas.");
            e.printStackTrace();
            return false;
        }
    }

    public Huella getHuellaById(Integer id) {
        try {
            return HuellaDAO.buildHuellaDAO().findById(id);
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Si no se encuentra la huella, devolvemos null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Huella> getAllHuellas() {
        try {
            return HuellaDAO.buildHuellaDAO().findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Huella> getHuellasByUserId(Integer userId) {
        try {
            return HuellaDAO.buildHuellaDAO().findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HuellaService buildHuellaService() {
        return new HuellaService();
    }
}

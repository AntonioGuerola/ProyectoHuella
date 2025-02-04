package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import org.example.App;
import org.example.model.connection.MySQLConnection;
import org.example.model.dao.ActividadDAO;
import org.example.model.entities.Actividad;
import org.example.model.singleton.userSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class registrarHabitosController extends Controller implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxActividades = new ChoiceBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        try {
            ActividadDAO actividadDAO = new ActividadDAO(MySQLConnection.getConnection());
            List<Actividad> actividades = actividadDAO.findAll();

            // Limpiar y añadir las actividades al ChoiceBox
            choiceBoxActividades.getItems().clear();
            for (Actividad actividad : actividades) {
                choiceBoxActividades.getItems().add(actividad.getNombre());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(Object output) {

    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }
}

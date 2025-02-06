package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.example.App;
import org.example.model.entities.Actividad;
import org.example.model.entities.Habito;
import org.example.model.entities.Tipo;
import org.example.model.entities.Usuario;
import org.example.model.service.ActividadService;
import org.example.model.service.HabitoService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class registrarHabitosController extends Controller implements Initializable {
    Usuario userLoggedIn = userSingleton.getInstance().getCurrentUser();

    @FXML
    private ChoiceBox<String> choiceBoxActividades = new ChoiceBox<>();

    @FXML
    private ChoiceBox<Tipo> choiceBoxTipo = new ChoiceBox<>();

    @FXML
    private TextField frecuenciaText;

    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxTipo.getItems().setAll(Tipo.values());
        datePicker.setDayCellFactory(getDayCellFactory());
    }

    @Override
    public void onOpen(Object input) throws IOException {
        List<Actividad> actividades = ActividadService.buildActividadService().getAllActividades();

        // Limpiar y añadir las actividades al ChoiceBox
        choiceBoxActividades.getItems().clear();
        for (Actividad actividad : actividades) {
            choiceBoxActividades.getItems().add(actividad.getNombre());
        }
    }

    @Override
    public void onClose(Object output) {

    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Deshabilitar los días posteriores a la fecha actual
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Color para los días deshabilitados
                        }
                    }
                };
            }
        };
    }

    public boolean Save() {
        if (choiceBoxActividades.getValue() == null || choiceBoxTipo.getValue() == null || frecuenciaText.getText().isEmpty() || datePicker.getValue() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HÁBITO", "Todos los campos son obligatorios.");
            return false;
        }

        try {
            String actividadSeleccionada = choiceBoxActividades.getValue();
            Actividad actividad = ActividadService.buildActividadService().getActividadByName(actividadSeleccionada);

            if (actividad == null) {
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HÁBITO", "No se encontró la actividad seleccionada.");
                return false;
            }

            int frecuencia;
            try {
                frecuencia = Integer.parseInt(frecuenciaText.getText());
                if (frecuencia <= 0) {
                    JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HÁBITO", "La frecuencia debe ser un número positivo.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HÁBITO", "La frecuencia debe ser un número válido.");
                return false;
            }

            Tipo tipo = choiceBoxTipo.getValue();

            LocalDate fechaSeleccionada = datePicker.getValue();
            Instant ultimaFecha = fechaSeleccionada.atStartOfDay().toInstant(java.time.ZoneOffset.UTC);

            // Crear un nuevo Habito
            Habito habito = new Habito(userLoggedIn, actividad, frecuencia, tipo, ultimaFecha);

            // Guardar el Habito usando Hibernate
            HabitoService.buildHabitoService().createHabito(habito);

            JavaFXUtils.showInfoAlert("ÉXITO", "Hábito registrado correctamente.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HÁBITO", "Ocurrió un error al guardar en la base de datos.");
            return false;
        }
    }


    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }
}

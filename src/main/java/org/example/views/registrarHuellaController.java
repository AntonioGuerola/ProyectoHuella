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
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.service.ActividadService;
import org.example.model.service.HuellaService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class registrarHuellaController extends Controller implements Initializable {
    Usuario userLoggedIn = userSingleton.getInstance().getCurrentUser();

    @FXML
    private ChoiceBox<String> choiceBoxActividades = new ChoiceBox<>();

    @FXML
    private TextField valorText;

    @FXML
    private TextField unidadText;

    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setDayCellFactory(getDayCellFactory());
    }

    @Override
    public void onOpen(Object input) throws IOException {
        List<Actividad> actividades = ActividadService.buildActividadService().getAllActividades();

        choiceBoxActividades.getItems().clear();
        for (Actividad actividad : actividades) {
            choiceBoxActividades.getItems().add(actividad.getNombre());
        }

        choiceBoxActividades.setOnAction(e -> actualizarUnidadPorCategoria());
    }

    private void actualizarUnidadPorCategoria() {
        String actividadSeleccionada = choiceBoxActividades.getValue();

        Actividad actividad = ActividadService.buildActividadService().getActividadByName(actividadSeleccionada);
        if (actividad != null) {
            int categoria = actividad.getIdCategoria().getId();

            switch (categoria) {
                case 1:
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("Km");
                    break;
                case 2:
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("KWh");
                    break;
                case 3:
                case 4:
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("Kg");
                    break;
                case 5:
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("m³");
                    break;
                default:
                    unidadText.setVisible(true);
                    unidadText.setVisible(false);
                    unidadText.clear();
                    break;
            }
        }
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
    }

    @Override
    public void onClose(Object output) {

    }

    public boolean Save() {
        if (choiceBoxActividades.getValue() == null || valorText.getText().isEmpty() || valorText.getText().isEmpty() || datePicker.getValue() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "Todos los campos son obligatorios.");
            return false;
        }

        try {
            String actividadSeleccionada = choiceBoxActividades.getValue();
            String unidadSeleccionada = unidadText.getText();

            BigDecimal valor;
            try {
                valor = new BigDecimal(valorText.getText());
                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "El valor debe ser un número positivo.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "El valor debe ser un número válido.");
                return false;
            }

            LocalDate fechaSeleccionada = datePicker.getValue();

            Actividad actividad = ActividadService.buildActividadService().getActividadByName(actividadSeleccionada);
            if (actividad == null) {
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "No se encontró la actividad seleccionada.");
                return false;
            }

            Huella huella = new Huella(userLoggedIn, actividad, valor, unidadSeleccionada, fechaSeleccionada);

            HuellaService.buildHuellaService().createHuella(huella);

            JavaFXUtils.showInfoAlert("ÉXITO", "Huella registrada correctamente.");
            App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "Ocurrió un error al guardar en la base de datos.");
            return false;
        }
    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }
}

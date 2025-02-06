package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.example.App;
import org.example.model.dao.ActividadDAO;
import org.example.model.dao.HuellaDAO;
import org.example.model.entities.Actividad;
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
        List<Actividad> actividades = ActividadDAO.buildActividadDAO().findAll();

        choiceBoxActividades.getItems().clear();
        for (Actividad actividad : actividades) {
            choiceBoxActividades.getItems().add(actividad.getNombre());
        }

        // Añadir listener para cuando se seleccione una actividad
        choiceBoxActividades.setOnAction(e -> actualizarUnidadPorCategoria());
    }

    private void actualizarUnidadPorCategoria() {
        // Obtener la actividad seleccionada
        String actividadSeleccionada = choiceBoxActividades.getValue();

        // Buscar la actividad en la base de datos
        Actividad actividad = ActividadDAO.buildActividadDAO().findByName(actividadSeleccionada);
        if (actividad != null) {
            // Obtener la categoría de la actividad
            int categoria = actividad.getIdCategoria().getId();  // Asegúrate de que la actividad tiene el atributo categoría

            // Dependiendo de la categoría, asignar la unidad adecuada
            switch (categoria) {
                case 1:
                    // Si la categoría es 1, la unidad es "Km"
                    unidadText.setVisible(false);  // Ocultar el ChoiceBox
                    unidadText.setVisible(true);          // Mostrar el TextField
                    unidadText.setText("Km");
                    break;
                case 2:
                    // Si la categoría es 2, la unidad es "KWh"
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("KWh");
                    break;
                case 3:
                case 4:
                    // Si la categoría es 3 o 4, la unidad es "Kg"
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("Kg");
                    break;
                case 5:
                    // Si la categoría es 5, la unidad es "m³"
                    unidadText.setVisible(false);
                    unidadText.setVisible(true);
                    unidadText.setText("m³");
                    break;
                default:
                    // Si no se cumple ninguna de las anteriores, mostrar el ChoiceBox
                    unidadText.setVisible(true);
                    unidadText.setVisible(false);
                    unidadText.clear();  // Limpiar las unidades anteriores
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

    @Override
    public void onClose(Object output) {

    }

    public boolean Save() {
        // Validar si todos los campos son válidos
        if (choiceBoxActividades.getValue() == null || valorText.getText().isEmpty() || valorText.getText().isEmpty() || datePicker.getValue() == null) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "Todos los campos son obligatorios.");
            return false;
        }

        try {
            // Obtener los valores seleccionados
            String actividadSeleccionada = choiceBoxActividades.getValue();
            String unidadSeleccionada = unidadText.getText();  // El valor de unidad será del TextField

            // Validar el valor ingresado (por ejemplo, asegurarse de que sea un número)
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

            // Obtener la fecha seleccionada
            LocalDate fechaSeleccionada = datePicker.getValue();
            Instant fechaInstant = fechaSeleccionada.atStartOfDay().toInstant(java.time.ZoneOffset.UTC);

            // Crear la Huella (asegúrate de que la clase Huella esté definida correctamente)
            Actividad actividad = ActividadDAO.buildActividadDAO().findByName(actividadSeleccionada); // Buscar la actividad en la base de datos
            if (actividad == null) {
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR HUELLA", "No se encontró la actividad seleccionada.");
                return false;
            }

            Huella huella = new Huella(userLoggedIn, actividad, valor, unidadSeleccionada, fechaSeleccionada);

            // Guardar la huella en la base de datos (con HuellaDAO)
            HuellaDAO.buildHuellaDAO().save(huella);

            JavaFXUtils.showInfoAlert("ÉXITO", "Huella registrada correctamente.");
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

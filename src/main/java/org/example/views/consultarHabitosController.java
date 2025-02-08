package org.example.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.entities.Habito;
import org.example.model.entities.Usuario;
import org.example.model.service.HabitoService;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class consultarHabitosController extends Controller implements Initializable {
    private Usuario userLoggedIn;
    private final HabitoService habitoService = HabitoService.buildHabitoService();

    @FXML
    private TableView<Habito> tablaHabitos;

    @FXML
    private TableColumn<Habito, String> columnaActividad;

    @FXML
    private TableColumn<Habito, String> columnaFrecuencia;

    @FXML
    private TableColumn<Habito, String> columnaTipo;

    @FXML
    private TableColumn<Habito, String> columnaUltimaFecha;

    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedIn = userSingleton.getInstance().getCurrentUser();

        if (userLoggedIn != null) {
            configurarColumnasTabla();
            cargarHabitosUsuario();
            configurarSeleccionTabla();
        }

        btnEliminar.setDisable(true);
    }

    private void configurarColumnasTabla() {
        columnaActividad.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre())
        );
        columnaFrecuencia.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnaUltimaFecha.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));
    }

    private void cargarHabitosUsuario() {
        if (userLoggedIn != null) {
            Usuario usuarioConHabitos = UsuarioService.buildUsuarioService().getUserWithHabits(userLoggedIn.getId());

            if (usuarioConHabitos != null && usuarioConHabitos.getHabitos() != null) {
                ObservableList<Habito> habitos = FXCollections.observableArrayList(usuarioConHabitos.getHabitos());
                tablaHabitos.setItems(habitos);
            }
        }
    }

    private void configurarSeleccionTabla() {
        tablaHabitos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnEliminar.setDisable(newSelection == null);
        });
    }

    @FXML
    public void eliminarHabito() {
        Habito habitoSeleccionado = tablaHabitos.getSelectionModel().getSelectedItem();

        if (habitoSeleccionado == null) {
            JavaFXUtils.showErrorAlert("Error", "Por favor, selecciona un hábito para eliminar.");
            return;
        }

        boolean confirmacion = JavaFXUtils.showConfirmationDialog(
                "Confirmar eliminación",
                "¿Estás seguro de que quieres eliminar este hábito? Esta acción no se puede deshacer."
        );

        if (confirmacion) {
            boolean eliminado = habitoService.deleteHabito(habitoSeleccionado);

            if (eliminado) {
                JavaFXUtils.showInfoAlert("Éxito", "El hábito ha sido eliminado correctamente.");
                cargarHabitosUsuario(); // Recargar la tabla después de eliminar
                tablaHabitos.getSelectionModel().clearSelection(); // Limpiar selección para evitar errores
            } else {
                JavaFXUtils.showErrorAlert("Error", "No se pudo eliminar el hábito.");
            }
        }
    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {}

    @Override
    public void onClose(Object output) {}
}

package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.service.HuellaService;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HuellaseImpactoController extends Controller implements Initializable {
    private Usuario userLoggedIn;
    private final HuellaService huellaService = HuellaService.buildHuellaService();

    @FXML
    private TableView<Huella> tablaHuellas;

    @FXML
    private TableColumn<Huella, String> columnaActividad;

    @FXML
    private TableColumn<Huella, Double> columnaValor;

    @FXML
    private TableColumn<Huella, String> columnaUnidad;

    @FXML
    private TableColumn<Huella, String> columnaFecha;

    @FXML
    private TableColumn<Huella, BigDecimal> columnaImpacto;

    @FXML
    private Label impactoTotalLabel;

    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedIn = userSingleton.getInstance().getCurrentUser();

        if (userLoggedIn != null) {
            configurarColumnasTabla();
            cargarHuellasUsuario();
            configurarSeleccionTabla();
        }

        btnEliminar.setDisable(true);
    }

    private void configurarColumnasTabla() {
        columnaActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        columnaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        columnaUnidad.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaImpacto.setCellValueFactory(new PropertyValueFactory<>("impactoAmbiental"));
    }

    private void cargarHuellasUsuario() {
        if (userLoggedIn != null) {
            Usuario usuarioConHuellas = UsuarioService.buildUsuarioService().getUserWithFootprints(userLoggedIn.getId());

            if (usuarioConHuellas != null && usuarioConHuellas.getHuellas() != null) {
                ObservableList<Huella> huellas = FXCollections.observableArrayList(usuarioConHuellas.getHuellas());
                tablaHuellas.setItems(huellas);

                BigDecimal impactoTotal = calcularImpactoTotal(usuarioConHuellas.getHuellas());
                impactoTotalLabel.setText("Impacto total: " + impactoTotal + " kg CO₂");
            }
        }
    }

    private BigDecimal calcularImpactoTotal(List<Huella> huellas) {
        return huellas.stream()
                .map(Huella::getImpactoAmbiental)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void configurarSeleccionTabla() {
        tablaHuellas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnEliminar.setDisable(newSelection == null);
        });
    }

    @FXML
    public void eliminarHuella() {
        Huella huellaSeleccionada = tablaHuellas.getSelectionModel().getSelectedItem();

        if (huellaSeleccionada == null) {
            JavaFXUtils.showErrorAlert("Error", "Por favor, selecciona una huella para eliminar.");
            return;
        }

        boolean confirmacion = JavaFXUtils.showConfirmationDialog(
                "Confirmar eliminación",
                "¿Estás seguro de que quieres eliminar esta huella? Esta acción no se puede deshacer."
        );

        if (confirmacion) {
            boolean eliminado = huellaService.deleteHuella(huellaSeleccionada);

            if (eliminado) {
                JavaFXUtils.showInfoAlert("Éxito", "La huella ha sido eliminada correctamente.");
                cargarHuellasUsuario();
                tablaHuellas.getSelectionModel().clearSelection();
            } else {
                JavaFXUtils.showErrorAlert("Error", "No se pudo eliminar la huella.");
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

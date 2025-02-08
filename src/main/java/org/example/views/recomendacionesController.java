package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.entities.Huella;
import org.example.model.entities.Recomendacion;
import org.example.model.entities.Usuario;
import org.example.model.service.RecomendacionService;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class recomendacionesController extends Controller implements Initializable {

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
    private TextField recomendacionTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnasTabla();
        cargarHuellasUsuario();
        configurarSeleccionTabla();
    }

    private void configurarColumnasTabla() {
        columnaActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        columnaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        columnaUnidad.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    private void cargarHuellasUsuario() {
        var userLoggedIn = userSingleton.getInstance().getCurrentUser();

        if (userLoggedIn != null) {
            Usuario usuarioConHuellas = UsuarioService.buildUsuarioService().getUserWithFootprints(userLoggedIn.getId());

            if (usuarioConHuellas != null && usuarioConHuellas.getHuellas() != null) {
                ObservableList<Huella> huellas = FXCollections.observableArrayList(usuarioConHuellas.getHuellas());
                tablaHuellas.setItems(huellas);
            }
        }
    }

    private void configurarSeleccionTabla() {
        tablaHuellas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarRecomendacion(newSelection);
            }
        });
    }

    private void mostrarRecomendacion(Huella huella) {
        try {
            if (huella.getIdActividad() != null && huella.getIdActividad().getIdCategoria() != null) {
                Integer categoriaId = huella.getIdActividad().getIdCategoria().getId();

                List<Recomendacion> recomendaciones = RecomendacionService.buildRecomendacionService().getRecomendacionesByCategoria(categoriaId);

                if (!recomendaciones.isEmpty()) {
                    recomendacionTextField.setText(recomendaciones.get(0).getDescripcion());
                } else {
                    recomendacionTextField.setText("No hay recomendaciones disponibles.");
                }
            } else {
                recomendacionTextField.setText("No se encontró una categoría para esta huella.");
            }
        } catch (Exception e) {
            recomendacionTextField.setText("Error al cargar recomendaciones.");
            e.printStackTrace();
        }
    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}

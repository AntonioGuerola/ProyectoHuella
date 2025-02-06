package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class mainController extends Controller implements Initializable {

    private Usuario userLoggedIn;

    @FXML
    private TableView<Huella> tablaHuellas;

    @FXML
    private TableColumn<Huella, String> columnaActividad;

    @FXML
    private TableColumn<Huella, BigDecimal> columnaValor;

    @FXML
    private TableColumn<Huella, String> columnaUnidad;

    @FXML
    private TableColumn<Huella, String> columnaFecha;

    @FXML
    private TableColumn<Huella, BigDecimal> columnaImpacto;

    @FXML
    private Label impactoTotalLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedIn = userSingleton.getInstance().getCurrentUser();

        if (userLoggedIn != null) {
            configurarColumnasTabla();
            cargarHuellasUsuario();
        }
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
            Usuario usuarioConHuellas = UsuarioDAO.buildUsuarioDAO().getUsuarioConHuellas(userLoggedIn.getId());

            if (usuarioConHuellas != null && usuarioConHuellas.getHuellas() != null) {
                ObservableList<Huella> huellas = FXCollections.observableArrayList(usuarioConHuellas.getHuellas());
                tablaHuellas.setItems(huellas);

                // Calcular impacto total
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

    public void goRegistrarHuella() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLA, null);
    }

    public void goRegistrarHabito() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHABITOS, null);
    }

    public void goRecomendaciones() throws IOException {
        App.currentController.changeScene(Scenes.RECOMENDACIONES, null);
    }

    public void goBack() throws IOException {
        userSingleton.closeSession();
        App.currentController.changeScene(Scenes.INICIO, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}

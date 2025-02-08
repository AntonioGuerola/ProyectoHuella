package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.service.UsuarioService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class ComparacionImpactoController extends Controller implements Initializable {

    @FXML
    private TableView<Usuario> tablaImpacto;

    @FXML
    private TableColumn<Usuario, String> columnaUsuario;

    @FXML
    private TableColumn<Usuario, BigDecimal> columnaImpacto;

    @FXML
    private BarChart<String, Number> graficoImpacto;

    private final ObservableList<Usuario> usuariosSeleccionados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnasTabla();
        cargarImpactoUsuarios();
        configurarSeleccionTabla();
    }

    private void configurarColumnasTabla() {
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaImpacto.setCellValueFactory(param -> {
            Usuario usuario = param.getValue();
            BigDecimal impactoTotal = calcularImpactoTotal(usuario);  // Calculamos el impacto total aquí
            return new javafx.beans.property.SimpleObjectProperty<>(impactoTotal);
        });    }

    private void cargarImpactoUsuarios() {
        List<Usuario> usuarios = UsuarioService.buildUsuarioService().getAllUsersWithFootprints();
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        for (Usuario usuario : usuarios) {
            if (usuario.getHuellas() != null) {
                BigDecimal impactoTotal = calcularImpactoTotal(usuario);
                // Mostrar el impacto directamente en la tabla, sin necesidad de almacenarlo en la entidad
                listaUsuarios.add(usuario);
            }
        }

        tablaImpacto.setItems(listaUsuarios);
    }

    private BigDecimal calcularImpactoTotal(Usuario usuario) {
        return usuario.getHuellas().stream()
                .map(h -> h.getImpactoAmbiental())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void configurarSeleccionTabla() {
        tablaImpacto.setOnMouseClicked(event -> {
            Usuario usuarioSeleccionado = tablaImpacto.getSelectionModel().getSelectedItem();
            if (usuarioSeleccionado != null) {
                if (usuariosSeleccionados.contains(usuarioSeleccionado)) {
                    usuariosSeleccionados.remove(usuarioSeleccionado);
                } else {
                    if (usuariosSeleccionados.size() < 4) {
                        usuariosSeleccionados.add(usuarioSeleccionado);
                    }
                }
                actualizarGrafico();
            }
        });
    }

    private void actualizarGrafico() {
        graficoImpacto.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Impacto Ambiental");

        for (Usuario usuario : usuariosSeleccionados) {
            BigDecimal impactoTotal = calcularImpactoTotal(usuario);
            series.getData().add(new XYChart.Data<>(usuario.getNombre(), impactoTotal));
        }

        graficoImpacto.getData().add(series);
    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {}

    @Override
    public void onClose(Object output) {}
}

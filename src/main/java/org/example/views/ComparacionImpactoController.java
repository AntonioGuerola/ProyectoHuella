package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.service.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

public class ComparacionImpactoController extends Controller implements Initializable {

    @FXML
    private TableView<Usuario> tablaImpacto;

    @FXML
    private TableColumn<Usuario, String> columnaUsuario;

    @FXML
    private TableColumn<Usuario, BigDecimal> columnaImpacto;

    @FXML
    private BarChart<String, Number> graficoImpacto;

    @FXML
    private CategoryAxis ejeUsuarios;

    @FXML
    private NumberAxis ejeImpacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnasTabla();
        cargarImpactoUsuarios();
    }

    private void configurarColumnasTabla() {
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaImpacto.setCellValueFactory(new PropertyValueFactory<>("impactoTotal"));
    }

    private void cargarImpactoUsuarios() {
        // Obtener la lista de usuarios con sus huellas
        List<Usuario> usuarios = UsuarioService.buildUsuarioService().getAllUsersWithFootprints();

        // Crear una lista observable para la tabla
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        // Crear datos para el gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Impacto Ambiental");

        for (Usuario usuario : usuarios) {
            if (usuario.getHuellas() != null) {
                // Calcular impacto total
                BigDecimal impactoTotal = calcularImpactoTotal(usuario);

                // Agregar usuario a la lista con su impacto
                usuario.setImpactoTotal(impactoTotal);
                listaUsuarios.add(usuario);

                // Agregar datos al gráfico
                series.getData().add(new XYChart.Data<>(usuario.getNombre(), impactoTotal));
            }
        }

        // Cargar datos en la tabla
        tablaImpacto.setItems(listaUsuarios);

        // Cargar datos en el gráfico
        graficoImpacto.getData().clear();
        graficoImpacto.getData().add(series);
    }

    private BigDecimal calcularImpactoTotal(Usuario usuario) {
        return usuario.getHuellas().stream()
                .map(h -> h.getImpactoAmbiental())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void actualizarDatos() {
        cargarImpactoUsuarios();
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

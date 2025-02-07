package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.service.HabitoService;
import org.example.model.service.HuellaService;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class AjustesUsuarioController extends Controller implements Initializable {
    private final Usuario usuario = userSingleton.getInstance().getCurrentUser();
    private final UsuarioService usuarioService = UsuarioService.buildUsuarioService();

    @FXML
    private TextField nameText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameText.setText(usuario.getNombre());
        emailText.setText(usuario.getEmail());
    }

    @Override
    public void onOpen(Object input) throws IOException {}

    @Override
    public void onClose(Object output) {}

    @FXML
    public void guardarCambios() throws NoSuchAlgorithmException {
        String nuevoNombre = nameText.getText().trim();
        String nuevoEmail = emailText.getText().trim();
        String nuevaContraseña = passwordText.getText().trim();

        boolean cambiosRealizados = false;

        // Verificar si el nombre o email han cambiado
        if (!nuevoNombre.equals(usuario.getNombre()) || !nuevoEmail.equals(usuario.getEmail())) {
            usuario.setNombre(nuevoNombre);
            usuario.setEmail(nuevoEmail);
            cambiosRealizados = true;
        }

        if (emailText.getText().isEmpty() || nameText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL GUARDAR CAMBIOS", "El nombre y el email no pueden estar en blanco");
            return;
        }

        // Validación de correo electrónico
        if (!JavaFXUtils.validateEmail(nuevoEmail)) {
            JavaFXUtils.showErrorAlert("ERROR AL GUARDAR CAMBIOS", "El email no es válido");
            return;
        }

        // Verificar si la contraseña no excede los 16 caracteres y si no es igual a la actual
        if (!nuevaContraseña.isEmpty()) {
            if (nuevaContraseña.length() > 16) {
                JavaFXUtils.showErrorAlert("ERROR AL GUARDAR CAMBIOS", "La contraseña no puede tener más de 16 caracteres.");
                return;
            }

            if (JavaFXUtils.hashPassword(nuevaContraseña).equals(usuario.getContraseña())) {
                JavaFXUtils.showErrorAlert("ERROR AL GUARDAR CAMBIOS", "La nueva contraseña no puede ser igual a la actual.");
                return;
            }

            // Si la contraseña es válida, actualizamos la contraseña del usuario
            usuario.setContraseña(JavaFXUtils.hashPassword(nuevaContraseña));
            cambiosRealizados = true;
        }

        // Si hay cambios, actualizamos el usuario
        if (cambiosRealizados) {
            if (usuarioService.updateUser(usuario, nuevaContraseña)) {
                JavaFXUtils.showInfoAlert("ÉXITO", "Los cambios se han guardado correctamente.");
            }
        }
    }


    @FXML
    public void eliminarUsuario() throws IOException {
        // Mostrar diálogo de confirmación
        boolean confirmacion = JavaFXUtils.showConfirmationDialog(
                "Confirmar eliminación",
                "¿Estás seguro de que quieres eliminar tu cuenta? Todos los hábitos y huellas serán también eliminados."
        );

        if (confirmacion) {
            try {
                // Primero, eliminar todos los hábitos del usuario
                String idUsuario = usuario.getId().toString(); // Usamos el id del usuario para eliminar sus hábitos
                boolean habitosEliminados = HabitoService.buildHabitoService().deleteAllHabitosByUsuario(idUsuario);

                if (!habitosEliminados) {
                    JavaFXUtils.showErrorAlert("Error al eliminar hábitos", "No se pudieron eliminar todos los hábitos.");
                    return;  // Si no se pudieron eliminar los hábitos, no eliminamos el usuario
                }

                // Ahora eliminamos las huellas del usuario pasándole el objeto Usuario completo
                boolean huellasEliminados = HuellaService.buildHuellaService().deleteAllHuellasByUsuario(usuario);

                if (!huellasEliminados) {
                    JavaFXUtils.showErrorAlert("Error al eliminar huellas", "No se pudieron eliminar todas las huellas.");
                    return;  // Si no se pudieron eliminar las huellas, no eliminamos el usuario
                }

                // Si los hábitos y huellas fueron eliminados correctamente, eliminamos el usuario
                usuarioService.deleteUser(usuario);

                // Cerrar la sesión del usuario
                userSingleton.closeSession();

                // Mostrar mensaje de éxito
                JavaFXUtils.showInfoAlert("Cuenta eliminada", "Tu cuenta ha sido eliminada correctamente.");

                // Redirigir a la escena de inicio
                App.currentController.changeScene(Scenes.INICIO, null);

            } catch (Exception e) {
                e.printStackTrace();
                JavaFXUtils.showErrorAlert("Error", "No se pudo eliminar la cuenta.");
            }
        }
    }



    @FXML
    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
    }
}

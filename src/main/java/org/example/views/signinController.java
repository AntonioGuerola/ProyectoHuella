package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entities.Usuario;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class signinController extends Controller implements Initializable {

    @FXML
    private TextField nameText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField confirmPasswordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    public boolean saveUser() throws NoSuchAlgorithmException, IOException {
        boolean result = false;

        if (nameText.getText().isEmpty() || emailText.getText().isEmpty() ||
                passwordText.getText().isEmpty() || confirmPasswordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "No puede haber campos vacíos");
            return false;
        }

        if (!passwordText.getText().equals(confirmPasswordText.getText())) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "Las contraseñas no coinciden");
            return false;
        }

        if (passwordText.getText().length() > 16) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "La contraseña es demasiado larga");
            return false;
        }

        if (!JavaFXUtils.validateEmail(emailText.getText())) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El email no es válido");
            return false;
        }

        Usuario userToRegister = new Usuario(nameText.getText(), emailText.getText(), JavaFXUtils.hashPassword(passwordText.getText()));

        // Verificar si el usuario ya existe
        if (UsuarioService.buildUsuarioService().getUserByEmail(userToRegister.getEmail()) == null) {
            UsuarioService.buildUsuarioService().registerUser(userToRegister);
            userSingleton.initialize(userToRegister);
            JavaFXUtils.showInfoAlert("ÉXITO", "Usuario registrado correctamente.");
            result = true;

            App.currentController.changeScene(Scenes.INICIO, null);
        } else {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El email ya está registrado");
        }

        return result;
    }

    @FXML
    private void goBack() throws IOException {
        App.currentController.changeScene(Scenes.INICIO, null);
    }
}

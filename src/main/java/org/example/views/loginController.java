package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.service.UsuarioService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class loginController extends Controller implements Initializable {

    @FXML
    public TextField emailText;

    @FXML
    public TextField passwordText;

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
    public boolean login() throws NoSuchAlgorithmException, IOException {
        boolean result = false;

        if (emailText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "Los campos de email y contraseña no pueden estar vacíos");
            return false;
        }

        String email = emailText.getText();
        String passwordHash = JavaFXUtils.hashPassword(passwordText.getText());

        Usuario userFromDatabase = UsuarioService.buildUsuarioService().getUserByEmail(email);

        if (userFromDatabase != null && userFromDatabase.getContraseña().equals(passwordHash)) {
            userSingleton.initialize(userFromDatabase);
            System.out.println("Logueado correctamente");

            App.currentController.changeScene(Scenes.MENUPRINCIPAL, null);
            result = true;
        } else {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "Credenciales incorrectas.");
        }
        return result;
    }

    @FXML
    private void goBack() throws IOException {
        App.currentController.changeScene(Scenes.INICIO, null);
    }
}

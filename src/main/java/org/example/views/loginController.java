package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
    public boolean login() throws NoSuchAlgorithmException, SQLException {
        boolean result = false;

        if (emailText.getText().isEmpty() && passwordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "Los campos de email y contraseña no pueden estar vacíos");
            return false;
        }
        if (emailText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "El campo de email no puede estar vacío");
            return false;
        }
        if (passwordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "El campo de contraseña no puede estar vacío");
            return false;
        }

        Usuario userToLogin = new Usuario("", emailText.getText(), JavaFXUtils.hashPassword(passwordText.getText()));
        Usuario userFromDataBase = UsuarioDAO.buildUsuario().findByEmail(userToLogin.getEmail());
        if (userFromDataBase != null) {
            if (userFromDataBase.getEmail().equals(userToLogin.getEmail())) {
                if (userFromDataBase.getContraseña() != null && userFromDataBase.getContraseña().equals(userToLogin.getContraseña())) {
                    userSingleton.getInstance(userFromDataBase);
                    result = true;
                } else {
                    JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "Contraseña incorrecta");
                }
            }
        } else {
            JavaFXUtils.showErrorAlert("ERROR AL INICIAR SESIÓN", "No hay ningún usuario con este email");
        }
        return result;
    }

    @FXML
    private void goBack() throws IOException {
        App.currentController.changeScene(Scenes.INICIO, null);
    }
}

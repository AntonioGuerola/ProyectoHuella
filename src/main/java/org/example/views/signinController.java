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
    public boolean saveUser() throws NoSuchAlgorithmException, SQLException, IOException {
        boolean result = false;
        if (emailText.getText().isEmpty() && passwordText.getText().isEmpty() && confirmPasswordText.getText().isEmpty() && nameText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "No puede haber campos vacíos");
            return false;
        }
        if (emailText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El campo de email no puede estar vacío");
            return false;
        }
        if (passwordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El campo de contraseña no puede estar vacío");
            return false;
        }
        if (confirmPasswordText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El campo para confirmar la contraseña no puede estar vacío");
            return false;
        }
        if (nameText.getText().isEmpty()) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "El campo del nombre no puede estar vacío");
            return false;
        }

        if (passwordText.getText().length() <= 16){
            if (passwordText.getText().equals(confirmPasswordText.getText())){
                System.out.println(nameText.getText() + emailText.getText() + JavaFXUtils.hashPassword(passwordText.getText()));
                Usuario userToRegister = new Usuario(nameText.getText(), emailText.getText(), JavaFXUtils.hashPassword(passwordText.getText()));
                System.out.println(userToRegister);
                if (JavaFXUtils.validateEmail(emailText.getText())){
                    if (UsuarioDAO.buildUsuario().findByEmail(userToRegister.getEmail()) == null){
                        System.out.println(userToRegister);
                        UsuarioDAO.buildUsuario().save(userToRegister);
                        userSingleton.getInstance(userToRegister);
                        result = true;
                        App.currentController.changeScene(Scenes.INICIO, null);
                    }else{
                        JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "Error al registrarse porque este email ya existe");
                    }
                }else{
                    JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "Error al registrarse porque el email no es válido");
                }
            }else{
                JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "Error al registrarse porque la contraseña es diferente");
            }
        }else{
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRARSE", "Error al registrarse porque la contraseña es muy larga");
        }
        return result;
    }

    @FXML
    private void goBack() throws IOException {
        App.currentController.changeScene(Scenes.INICIO, null);
    }
}

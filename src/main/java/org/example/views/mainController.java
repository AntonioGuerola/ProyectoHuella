package org.example.views;

import javafx.fxml.Initializable;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController extends Controller implements Initializable {
    Usuario userLoggedIn = userSingleton.getInstance().getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(userLoggedIn);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    public void goRegistrarHuella() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLA, null);
    }

    public void goRegistrarHabito() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHABITOS, null);
    }

    public void goBack() throws IOException {
        userSingleton.closeSession();
        App.currentController.changeScene(Scenes.INICIO, null);
    }
}

package org.example.views;

import javafx.fxml.Initializable;
import org.example.App;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController extends Controller implements Initializable {

    private Usuario userLoggedIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedIn = userSingleton.getInstance().getCurrentUser();
    }

    public void goRegistrarHuella() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLA, null);
    }

    public void goRegistrarHabito() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHABITOS, null);
    }

    public void goConsultarHuellas() throws IOException {
        App.currentController.changeScene(Scenes.CONSULTARHUELLAS, null);
    }

    public void goRecomendaciones() throws IOException {
        App.currentController.changeScene(Scenes.RECOMENDACIONES, null);
    }

    public void goComparacionImpacto() throws IOException {
        App.currentController.changeScene(Scenes.COMPARACIONIMPACTO, null);
    }

    public void goAjustes() throws IOException {
        App.currentController.changeScene(Scenes.AJUSTES, null);
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

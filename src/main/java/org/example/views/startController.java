package org.example.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startController extends Controller implements Initializable {

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
    private void toRegister() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRAR, null);
    }

    @FXML
    private void toLogIn() throws IOException {
        App.currentController.changeScene(Scenes.INICIARSESION, null);
    }

    @FXML
    private void close() throws IOException {
        System.exit(0);
    }
}

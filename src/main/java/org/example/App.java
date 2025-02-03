package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.views.AppController;
import org.example.views.Scenes;
import org.example.views.View;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Scene scene;
    public static AppController currentController;

    @Override
    public void start(Stage stage) throws IOException {
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 900, 600);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setTitle("HuellaCarbono");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(900);
        stage.setMaxHeight(600);
        stage.setMaxWidth(900);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
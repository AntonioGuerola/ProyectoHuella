package org.example.views;

public enum Scenes {
    ROOT("view/layout.fxml"),
    REGISTRAR("view/signin.fxml"),
    INICIO("view/start.fxml"),
    INICIARSESION("view/login.fxml"),
    MENUPRINCIPAL("view/main.fxml"),
    REGISTRARHUELLA("view/registrarHuella.fxml"),
    REGISTRARHABITOS("view/registrarHabitos.fxml"),
    RECOMENDACIONES("view/recomendaciones.fxml"),
    AJUSTES("view/ajustesUsuario.fxml"),
    COMPARACIONIMPACTO("view/comparacionImpacto.fxml"),
    CONSULTARHUELLAS("view/HuellaseImpacto.fxml"),
    CONSULTARHABITOS("view/consultarHabitos.fxml");

    private String url;

    /**
     * Constructor for Scenes enumeration.
     * @param url The URL of the corresponding FXML file.
     */
    Scenes(String url) {
        this.url = url;
    }

    /**
     * Gets the URL of the scene.
     * @return The URL of the FXML file representing the scene.
     */
    public String getURL() {
        return url;
    }
}
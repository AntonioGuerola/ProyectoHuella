package org.example.views;

public enum Scenes {
    ROOT("view/layout.fxml"),
    REGISTRAR("view/signin.fxml"),
    INICIO("view/main.fxml"),
    INICIARSESION("view/login.fxml"),;

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
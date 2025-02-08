module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires java.xml.bind;
    requires layout;
    requires kernel;

    opens org.example.views to javafx.fxml;
    exports org.example;

    opens org.example.model.entities to org.hibernate.orm.core, javafx.base;
}

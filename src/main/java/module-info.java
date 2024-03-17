module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.net.http;
    requires org.json;
    requires json.simple;
    requires com.dlsc.gmapsfx;
    requires jdk.jsobject;
    requires org.junit.jupiter.api;
    requires junit;

    opens ApplicationScene to javafx.fxml;
    exports ApplicationScene;
}
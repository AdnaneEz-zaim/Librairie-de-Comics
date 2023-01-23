module com.example.comicsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;
    requires com.fasterxml.jackson.databind;
    requires fontawesomefx;
    requires okhttp3;

    opens com.example.develop to javafx.fxml;
    exports com.example.develop;
    opens com.example.develop.controllers to javafx.fxml;
    exports com.example.develop.controllers;
    opens com.example.develop.model to javafx.fxml;
    exports com.example.develop.model;
    opens com.example.develop.service to javafx.fxml;
    exports com.example.develop.service;
}
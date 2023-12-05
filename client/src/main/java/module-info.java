module de.ostfalia.group4.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens de.ostfalia.group4.client to javafx.fxml;
    exports de.ostfalia.group4.client;
}
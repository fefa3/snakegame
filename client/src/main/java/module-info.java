module de.ostfalia.group4.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.lang3;
    requires java.desktop;


    opens de.ostfalia.group4.client to javafx.fxml;
    exports de.ostfalia.group4.client;
}
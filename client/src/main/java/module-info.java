module de.ostfalia.group4.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.ostfalia.group4.client to javafx.fxml;
    exports de.ostfalia.group4.client;
}
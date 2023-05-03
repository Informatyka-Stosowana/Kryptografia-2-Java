module com.example.dsa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.dsa to javafx.fxml;
    exports com.example.dsa;
}
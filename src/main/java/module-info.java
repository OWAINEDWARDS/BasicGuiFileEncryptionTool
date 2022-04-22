module com.example.fileencryptiontool {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fileencryptiontool to javafx.fxml;
    exports com.example.fileencryptiontool;
}
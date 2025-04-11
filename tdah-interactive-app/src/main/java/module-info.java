module com.tdahapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;
    
    opens com.tdahapp to javafx.fxml;
    exports com.tdahapp;
}

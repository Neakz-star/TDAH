package com.tdahapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Updated resource path
            Parent root = FXMLLoader.load(App.class.getResource("/fxml/Registro.fxml"));
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("B.E.E.S.");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
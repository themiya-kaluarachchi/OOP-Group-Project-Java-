package com.example.javaminiproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SeaExplorer extends Application {
    protected static Scene scene = null;
    protected int id;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeaExplorer.class.getResource("Dashboard.fxml"));
        scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Sea Explorer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.students.rgz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static Scene current_scene;
    public static Stage current_stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        current_scene = scene;
        current_stage = stage;
        setUp(stage, scene);
        stage.show();
    }

    public void setUp(Stage stage, Scene scene) {
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ico.png"))));
        stage.setTitle("РГЗ №2");
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        System.out.println("Authors: Hostiev Ivan & Gorlov Maksim");
        launch();
    }
}
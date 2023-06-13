package com.students.rgz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import static com.students.rgz.MainController.usedButtonList;

public class Helper {
    public void warn(Stage stage, Scene scene) {
        Popup popup = new Popup();
        Label label = new Label("Увага!\nВиберіть 2 або більше кольорів");
        label.setStyle("-fx-background-color: #FF9999; -fx-padding: 16px; -fx-font-weight: bold;");
        VBox content = new VBox(label);
        content.setAlignment(Pos.CENTER);

        popup.getContent().add(content);

        popup.show(stage);

        popup.setAutoHide(true);
        popup.setX(stage.getX()+62);
        popup.setY(stage.getY()+100);
    }

    public void restartApplication(ActionEvent event, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main main = new Main();
            main.setUp(stage, scene);

            stage.setScene(scene);
            stage.show();
            MainController.colorNum = 0;
            usedButtonList.getItems().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

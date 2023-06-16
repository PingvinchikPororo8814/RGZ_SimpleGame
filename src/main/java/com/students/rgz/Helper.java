package com.students.rgz;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import static com.students.rgz.MainController.usedButtonList;

public class Helper {

    public void faq(Stage stage) {
        Popup popup = new Popup();
        Label label = new Label("Виберіть 2 або більше кольорів, та виберіть який розмір клітинок ви хочете вибрати,\n після цього буде згенероване поле, якщо натиснути кнопку <Генерація поля> то ви побачите\n згенероване поле з вашими кольорами та цифрами на клітинках, якщо натиснути <Наступний крок> то цифри\n зміняться, якщо коло клітини з кольором, цифра більше чим вона на 1, у такому випадку вона теж буде збільшена на 1.\n Ви можете натискати <Наступний крок> до тих пір поки не залишиться цифра яка не менше ніж інші\n на 1 або усі клітинки будуть одніеї цифри. Коли буде кінець ви побачите на екрані <Game Over>.\n У налаштуваннях ви можете покинути програму або перезавантажити та почати все спочатку.");
        label.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold;");
        VBox content = new VBox(label);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");

        popup.getContent().add(content);

        // рамка
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        borderPane.setCenter(content);

        popup.getContent().add(borderPane);

        popup.show(stage);

        popup.setAutoHide(true);
        popup.setX(stage.getX());
        popup.setY(stage.getY()+100);
    }

    public void warn(Stage stage) {
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

    public void restartApplication(Stage stage) {
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

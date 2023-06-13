package com.students.rgz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PostController {
    private final Helper help = new Helper();
    public static ListView<String> buttonList = new ListView<>();
    public static int size;
    @FXML
    private MenuItem restart;

    @FXML
    private GridPane colorGrid;

    @FXML
    private Button button;

    @FXML
    private ImageView img;

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    @FXML
    protected void restartApplication(ActionEvent event) {
        help.restartApplication(event, (Stage) restart.getParentPopup().getOwnerWindow());
    }

    @FXML
    protected void faq() {
        colorGridGen();
    }

    @FXML
    protected void ButtonOnClick() {
        if (button.getText().equals("Генерація клітинок")) {
            colorGridGen();
            button.setText("Наступний крок");
            buttonList.getItems().addAll("red", "orange", "yellow", "pink", "green", "cyan", "blue", "purple");
            return;
        }
        if (!nextStep()) {
            button.setVisible(false);
            String imagePath = getClass().getResource("/images/game_over1.png").toExternalForm();;
            switch (size) {
                case 2:
                    imagePath = getClass().getResource("/images/game_over2.png").toExternalForm();
                    break;
                case 3:
                    imagePath = getClass().getResource("/images/game_over3.png").toExternalForm();
                    break;
            }
            Image image = new Image(imagePath);
            img.setImage(image);
            img.setVisible(true);
        }
    }

    public void colorGridGen() {
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setFillWidth(true);
        colorGrid.getColumnConstraints().add(colConstraints);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Node node = new Pane();
                int num = random.nextInt(MainController.colorNum);
                node.setStyle("-fx-background-color: " + MainController.usedButtonList.getItems().get(num) + ";");
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-font-weight: bold; " + "-fx-font-family: 'Comic Sans MS'; " + "-fx-alignment: center;");
                Map<String, Integer> buttonChoiceMap = new HashMap<>();
                buttonChoiceMap.put("red", 1);
                buttonChoiceMap.put("orange", 2);
                buttonChoiceMap.put("yellow", 3);
                buttonChoiceMap.put("pink", 4);
                buttonChoiceMap.put("green", 5);
                buttonChoiceMap.put("cyan", 6);
                buttonChoiceMap.put("blue", 7);
                buttonChoiceMap.put("purple", 8);
                int choice = buttonChoiceMap.getOrDefault(MainController.usedButtonList.getItems().get(num), 0);

                label.setText(Integer.toString(choice));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(label);
                StackPane.setAlignment(label, Pos.CENTER);

                colorGrid.add(node, j, i);
                colorGrid.add(stackPane, j, i);
            }
        }
    }

    public boolean nextStep() {
        int[][] arr = new int[8][8];
        int i = 0, j = 0;
        // Отримання масива чисел з поля
        for (Node node : colorGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;

                // Отримання Label із StackPane
                Label label = null;
                for (Node childNode : stackPane.getChildren()) {
                    if (childNode instanceof Label) {
                        label = (Label) childNode;
                        break;
                    }
                }

                if (label != null) {
                    // Отримання позиції клітинки у GridPane
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (columnIndex != null && rowIndex != null) {
                        String text = label.getText();
                        arr[i][j] = Integer.parseInt(text);
                    }
                }
                j++;
                if (j == 8) {
                    j = 0;
                    i++;
                }
            }
            if (i == 8) {
                break;
            }
        }

        boolean nextStep = false;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                int currentValue = arr[i][j];

                // Перевірка верхньої клітинки
                if (i > 0 && arr[i - 1][j] == currentValue + 1) {
                    nextStep = true;
                    arr[i][j] = currentValue + 1;
                }

                // Перевірка нижньої клітинки
                if (i < 7 && arr[i + 1][j] == currentValue + 1) {
                    nextStep = true;
                    arr[i][j] = currentValue + 1;
                }

                // Перевірка клітинки зліва
                if (j > 0 && arr[i][j - 1] == currentValue + 1) {
                    nextStep = true;
                    arr[i][j] = currentValue + 1;
                }

                // Перевірка клітинки справа
                if (j < 7 && arr[i][j + 1] == currentValue + 1) {
                    nextStep = true;
                    arr[i][j] = currentValue + 1;
                }
            }
        }

        if (nextStep) {
            upgradeGrid(arr);
        }

        return  nextStep;
    }

    private void upgradeGrid(int[][] arr) {
        colorGrid.getChildren().clear(); // Очищення старих клітинок

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setFillWidth(true);
        colorGrid.getColumnConstraints().add(colConstraints);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Node node = new Pane();
                node.setStyle("-fx-background-color: " + buttonList.getItems().get(arr[i][j]) + ";");
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-font-weight: bold; " + "-fx-font-family: 'Comic Sans MS'; " + "-fx-alignment: center;");
                label.setText(Integer.toString(arr[i][j]));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(label);
                StackPane.setAlignment(label, Pos.CENTER);

                colorGrid.add(node, j, i);
                colorGrid.add(stackPane, j, i);
            }
        }
    }
}

package com.students.rgz;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {
    public static ListView<String> usedButtonList = new ListView<>();
    public static short colorNum = 0;
    private boolean canUseColor = true;
    private final Helper help = new Helper();
    @FXML
    private Button smallButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button bigButton;

    @FXML
    private Button redButton;

    @FXML
    private Button orangeButton;

    @FXML
    private Button yellowButton;

    @FXML
    private Button pinkButton;

    @FXML
    private Button greenButton;

    @FXML
    private Button cyanButton;

    @FXML
    private Button blueButton;

    @FXML
    private Button purpleButton;

    @FXML
    private MenuItem restart;

    @FXML
    public Label text;

    @FXML
    private MenuItem faq;

    @FXML
    protected void faq() {
        help.faq((Stage) faq.getParentPopup().getOwnerWindow());
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    @FXML
    protected void restartApplication() {help.restartApplication((Stage) restart.getParentPopup().getOwnerWindow());}

    // Обробка логіки вибору маленьких клітинок
    @FXML
    protected void onSmallButtonClick() {
        if (checkColors()) {
            selectSize("small.fxml", "маленькі");
            PostController.size = 1;
        }
    }

    // Обробка логіки вибору середніх клітинок
    @FXML
    protected void onMediumButtonClick() {
        if (checkColors()) {
            selectSize("medium.fxml", "середні");
            PostController.size = 2;
        }
    }

    // Обробка логіки вибору великих клітинок
    @FXML
    protected void onBigButtonClick() {
        if (checkColors()) {
            selectSize("big.fxml", "великі");
            PostController.size = 3;
        }
    }

    @FXML
    protected void onColorButtonClick(ActionEvent event) {
        if (canUseColor) {
            Button button = (Button) event.getSource();
            ListView<Button> colorButtonList = getColorButtonList();

            for (Button colorButton : colorButtonList.getItems()) {
                if (colorButton == button) {
                    if (colorButton.getEffect() == null) {
                        colorButton.setEffect(new DropShadow());
                    } else {
                        colorButton.setEffect(null);
                    }
                    break;
                }
            }
        }
    }

    private boolean checkColors() {
        for (Button colorButton : getColorButtonList().getItems()) {
            if (colorButton.getEffect() != null) {
                colorNum++;
                if (!usedButtonList.getItems().contains(colorButton)) {
                    usedButtonList.getItems().add(colorButton.getId().replace("Button", ""));
                }
            }
        }

        if (colorNum < 2) {
            colorNum = 0;
            usedButtonList.getItems().clear();
            help.warn((Stage) mediumButton.getScene().getWindow());
            return false;
        }
        return true;
    }

    private ListView<Button> getColorButtonList() {
        ListView<Button> buttonList = new ListView<>();
        buttonList.getItems().addAll(redButton, orangeButton, yellowButton, pinkButton, greenButton, cyanButton, blueButton, purpleButton);
        return buttonList;
    }

    private void selectSize(String file, String txt) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(file));
            Main.current_scene = new Scene(fxmlLoader.load());

            canUseColor = false;
            smallButton.setVisible(false);
            mediumButton.setVisible(false);
            bigButton.setVisible(false);
            generateGrid(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateGrid(String size) {
        text.setVisible(true);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                createTextChangeTransition("Розмір клітинок:\n" + size, 0),
                createTextChangeTransition("Генерація поля.", 2),
                createTextChangeTransition("Генерація поля..", 1),
                createTextChangeTransition("Генерація поля...", 1),
                createTextChangeTransition("Поле сгенеровано!", 1),
                createTextChangeTransition("", 2)
        );
        sequentialTransition.play();
    }

    private PauseTransition createTextChangeTransition(String txt, int delaySeconds) {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(delaySeconds));
        pauseTransition.setOnFinished(event -> {
            text.setText(txt);
            if (txt.equals("Генерація поля.")) {
                Stage stage = new Stage();
                Main.current_stage = stage;
                Main main = new Main();
                main.setUp(stage, Main.current_scene);
            }
            if (txt.equals("")) {
                Stage currentStage = (Stage) bigButton.getScene().getWindow();
                currentStage.close();
                Main.current_stage.show();
            }
        });
        return pauseTransition;
    }
}

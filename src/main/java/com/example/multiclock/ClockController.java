package com.example.multiclock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClockController implements Initializable {
    public ChoiceBox<String> area_choiceBox;
    public Label clock_lbl;
    public Button create_btn;

    // Create a thread
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        area_choiceBox.getItems().addAll(ZoneId.getAvailableZoneIds());
        area_choiceBox.setValue(ZoneId.systemDefault().toString());

        startClock();
        area_choiceBox.setOnAction(event -> updateClock());
        create_btn.setOnAction(event -> {
            App newApp = new App();
            Stage stage = new Stage();
            try {
                newApp.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

private void updateClock() {
    Task<Void> task = new Task<>() {
        @Override
        protected Void call() throws Exception {
            while (true) {
                LocalDateTime now = LocalDateTime.now(ZoneId.of(area_choiceBox.getValue()));
                String timeString = String.format("%02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond());
                Platform.runLater(() -> clock_lbl.setText(timeString));
                Thread.sleep(1000);
            }
        }
    };
    Thread clockThread = new Thread(task);
    clockThread.setDaemon(true);
    clockThread.start();
}

    private void startClock() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateClock();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

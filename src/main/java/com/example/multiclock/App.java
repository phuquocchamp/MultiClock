package com.example.multiclock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application implements Runnable {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Clock.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("PQ Clock");
        stage.show();
    }

    @Override
    public void run() {
    }

    public static void main(String[] args){
        launch(args);
    }
}

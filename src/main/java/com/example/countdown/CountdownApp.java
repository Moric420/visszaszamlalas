package com.example.countdown;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CountdownApp {



    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("countdown.fxml"));
        primaryStage.setTitle("Visszaszámlálás");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}


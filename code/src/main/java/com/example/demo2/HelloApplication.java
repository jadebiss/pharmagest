package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primarystage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("connexion.fxml"));


        Scene scene = new Scene(root, 800, 500);
        primarystage.setTitle("PharmaGest");
        primarystage.setScene(scene);
        primarystage.getIcons().add(new Image(getClass().getResourceAsStream("images/pharmagest-icon.png")));
        primarystage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

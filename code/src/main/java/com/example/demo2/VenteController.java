package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class VenteController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label cancelButton;


    private Stage stage;
    private Scene scene;
    private Parent root;
    public void setCancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setDashboardButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            if (loader != null) {
                root = loader.load();
                DashboardController dashboardController = loader.getController();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 800, 500);
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Failed to load dashboard.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

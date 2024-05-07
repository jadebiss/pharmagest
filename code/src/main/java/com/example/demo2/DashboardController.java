package com.example.demo2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    public Label nameLabel;

    @FXML
    private Button cancelButton;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayName(String username) {
        nameLabel.setText(username);
    }

    public String getUsername() {
        return nameLabel.getText();
    }


    @FXML
    private void setCancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setApprovisionnementButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("medicaments.fxml"));
        try {
            root = loader.load();

            ApprovisionnementController approvisionnementController = loader.getController();
            approvisionnementController.setLabelApprovisionnement();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load medicaments.fxml");
        }
    }

    @FXML
    private void setMedicamentButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("caisse 1.fxml"));
        try {
            root = loader.load();
            CaisseController caisseController = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load EmployeeView.fxml");
        }
    }

    @FXML
    private void setCaisseButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("caisse.fxml"));
        try {
            root = loader.load();

            RecuController recuController = loader.getController();
            recuController.setLabelRecu();
            recuController.setLabelRecuDetails();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1000, 610);
            String username = getUsername();
            recuController.displayName(username);
            scene.getStylesheets().add(getClass().getResource("tableview.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load caisse.fxml");
        }
    }

    @FXML
    private void setCrudButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeView.fxml"));
        try {
            root = loader.load();
            UserAccountController userAccountController = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 500);
            scene.getStylesheets().add(getClass().getResource("tableview.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load EmployeeView.fxml");
        }
    }


    @FXML
    private void setConnexionButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        try {
            root = loader.load();
            HelloController helloController = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load EmployeeView.fxml");
        }
    }
}


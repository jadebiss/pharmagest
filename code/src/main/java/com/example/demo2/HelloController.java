package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class HelloController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    public TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setLoginButtonOnAction(ActionEvent event) throws IOException {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            if (validateLogin()) loadDashboard(event);
            else {
                loginMessageLabel.setText("Essayez à nouveau");
            }
        } else {
            loginMessageLabel.setText("Entrez un nom d'utilisateur et mot de passe");
        }
    }

    public void setCancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE username = ? AND mdp_pharm = ?";
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection("PharmaGest", "postgres", "1234");

            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, passwordPasswordField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next() && queryResult.getInt(1) == 1) {
                loginMessageLabel.setText("Bienvenue !");
                updateLastLogin(usernameTextField.getText()); // Appel pour enregistrer les informations de connexion
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void updateLastLogin(String username) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection("PharmaGest", "postgres", "1234");

            String insertUserLogQuery = "INSERT INTO user_logs (username, last_login) VALUES (?, ?)";

            PreparedStatement preparedStatement = connectDB.prepareStatement(insertUserLogQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Set the current timestamp

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    private void loadDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            if (loader != null) {
                root = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.displayName(usernameTextField.getText());
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 900, 600);
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
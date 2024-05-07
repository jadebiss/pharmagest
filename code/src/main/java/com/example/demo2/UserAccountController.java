package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserAccountController {
    @FXML
    private TextField UserIdText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newFirstName;
    @FXML
    private TextField newLastName;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField statusText;
    @FXML
    private TableView<UserAccount> userAccountTable;
    @FXML
    private TableColumn<UserAccount, Integer> id_useraccountsColumn;
    @FXML
    private TableColumn<UserAccount, String> usernameColumn;
    @FXML
    private TableColumn<UserAccount, String> firstNameColumn;
    @FXML
    private TableColumn<UserAccount, String> lastNameColumn;
    @FXML
    private TableColumn<UserAccount, String> passwordColumn;
    @FXML
    private TableColumn<UserAccount, String> statusColumn;
    private Executor exec;

    public UserAccountController() {
    }

    @FXML
    private void initialize() {
        this.exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        this.id_useraccountsColumn.setCellValueFactory(cellData -> cellData.getValue().id_useraccountsProperty().asObject());
        this.usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        this.firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        this.lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        this.passwordColumn.setCellValueFactory(cellData -> cellData.getValue().mdpPharmProperty());
        this.statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

    }

    @FXML
    private void searchUserAccount(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            UserAccount userAccount = UserAccountDAO.searchUserAccount(this.UserIdText.getText());
            populateAndShowUserAccount(userAccount);
        } catch (SQLException var3) {
            var3.printStackTrace();
            resultArea.setText("Error occurred while getting employee information from DB.\n" + var3);
            throw var3;
        }
    }
    @FXML
    private void searchUserAccounts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<UserAccount> userData = UserAccountDAO.searchUserAccounts();
            this.populateUserAccounts(userData);
        } catch (SQLException var3) {
            System.out.println("Error occurred while getting employees information from DB.\n" + var3);
            throw var3;
        }
    }


    @FXML
    private void populateUserAccount(UserAccount userAccount) throws ClassNotFoundException {
        ObservableList<UserAccount> userData = FXCollections.observableArrayList();
        userData.add(userAccount);
        this.userAccountTable.setItems(userData);
    }

    @FXML
    private void populateUserAccounts(ObservableList<UserAccount> userData) throws ClassNotFoundException {
        this.userAccountTable.setItems(userData);
    }


    @FXML
    private void setUserInfoToTextArea(UserAccount userAccount) {
        this.resultArea.setText("Username: " + userAccount.getUsername() + "\nFirst Name: "
                + userAccount.getFirstname() + "\nLast Name: " + userAccount.getLastname());
    }

    @FXML
    private void populateAndShowUserAccount(UserAccount userAccount) throws ClassNotFoundException {
        if (userAccount != null) {
            this.populateUserAccount(userAccount);
            this.setUserInfoToTextArea(userAccount);
        } else {
            this.resultArea.setText("This user account does not exist!\n");
        }
    }

    @FXML
    private void updateUsername(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            UserAccountDAO.updateUsername(this.UserIdText.getText(), this.newUsername.getText());
            this.resultArea.setText("Username has been updated for, username id: " + this.UserIdText.getText() + "\n");
        } catch (SQLException var3) {
            this.resultArea.setText("Problem occurred while updating username: " + var3);
        }

    }

    @FXML
    private void insertUserAccount(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            UserAccountDAO.insertUserAccount(
                    this.usernameText.getText(),
                    this.firstNameText.getText(),
                    this.lastNameText.getText(),
                    this.passwordText.getText()
            );
            this.resultArea.setText("User account inserted! \n");
        } catch (SQLException var3) {
            this.resultArea.setText("Problem occurred while inserting user account " + var3);
            throw var3;
        }
    }

    @FXML
    private void deleteUserAccount(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            UserAccountDAO.deleteUserWithId(this.UserIdText.getText());
            this.resultArea.setText("User deleted! User id: " + this.UserIdText.getText() + "\n");
        } catch (SQLException var3) {
            this.resultArea.setText("Problem occurred while deleting user " + var3);
            throw var3;
        }
    }
}

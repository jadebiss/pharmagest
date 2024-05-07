package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecuController {

    private Scene scene;
    private Parent root;
    @FXML
    private TextField recuIdText;
    @FXML
    private TextField totalText;
    @FXML
    private TextArea resultArea;

    @FXML
    private Text total;
    @FXML
    private TextField montantText;
    @FXML
    public Label nameLabel;
    @FXML
    private Label tableRecuLabel;
    @FXML
    private Label tableRecuDetailsLabel;

    @FXML
        private TableView<RecuDetails> recuDetailsTable;
    @FXML
    private TableColumn<RecuDetails, Integer> idRecuDetailsColumn;
    @FXML
    private TableColumn<RecuDetails, String> nomMedicamentColumn;
    @FXML
    private TableColumn<RecuDetails, String> prixMedicamentColumn;
    @FXML
    private TableColumn<RecuDetails, Integer> quantiteMedicamentColumn;
    @FXML
    private TableColumn<RecuDetails, Double> totalColumn;
    @FXML
    private TableView<Recu> recuTable;
    @FXML
    private TableColumn<Recu, Integer> idRecuColumn;
    @FXML
    private TableColumn<Recu, Double> totalRecuColumn;
    @FXML
    private TableColumn<Recu, String> statutColumn;
    @FXML
    private TableColumn<Recu, String> vendeurColumn;
    private Executor exec;

    public RecuController() {
    }

    @FXML
    private void initialize() {
        this.exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        this.idRecuDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().idRecuDetailsProperty().asObject());
        this.nomMedicamentColumn.setCellValueFactory(cellData -> cellData.getValue().nomMedicamentProperty());
        this.prixMedicamentColumn.setCellValueFactory(cellData -> cellData.getValue().prixMedicamentProperty());
        this.quantiteMedicamentColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteMedicamentProperty().asObject());
        this.totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        this.idRecuColumn.setCellValueFactory(cellData -> cellData.getValue().idRecuProperty().asObject());
        this.totalRecuColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        this.statutColumn.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
        this.vendeurColumn.setCellValueFactory(cellData -> cellData.getValue().vendeurProperty());

        recuTable.setOnMouseClicked(event -> {
            if (!recuTable.getSelectionModel().isEmpty()) {

                Recu selectedRecu = recuTable.getSelectionModel().getSelectedItem();
                total.setText(String.valueOf(selectedRecu.getTotal()));
                try {
                    ObservableList<RecuDetails> recuDetails = RecuDetailsDAO.searchRecuDetails(String.valueOf(selectedRecu.getIdRecu()));
                    if (recuDetails != null) {
                        populateRecuDetails(recuDetails);
                    } else {
                        // No matching RecuDetails found
                        resultArea.setText("No details found for selected Recu.");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    // Handle exception
                }
            }
        });

    }

    @FXML
    public void displayName(String username) {
        nameLabel.setText(username);
    }

    public String getUsername() {
        return nameLabel.getText();
    }


    @FXML
    public void setLabelRecu() {
        ObservableList<Recu> recuData = recuTable.getItems();
        if (recuData.isEmpty()) {
            tableRecuLabel.setText("Cherchez un recu pour lancer la caisse");
        }

    }

    @FXML
    public void setLabelRecuDetails() {
        ObservableList<RecuDetails> recuDetailsData = recuDetailsTable.getItems();
        if (recuDetailsData.isEmpty()) {
            tableRecuDetailsLabel.setText("Les détails du reçu apparaîtront ici");
        }
    }

    @FXML
    private void paiementRecu() {
        try {
            // Get total and amount paid
            double totalAmount = Double.parseDouble(total.getText());
            double amountPaid = Double.parseDouble(montantText.getText());

            // Calculate remaining amount
            double reste = amountPaid - totalAmount;

            // Format remaining amount to two decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            reste = Double.valueOf(df.format(reste));

            Alert alert;

            if (reste >= 0) {
                // Display remaining amount in the alert
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Remaining Amount");
                alert.setHeaderText(null);
                alert.setContentText("Montant a rendre : " + reste);
                alert.getDialogPane().getStyleClass().add("alert");

                Recu selectedRecu = recuTable.getSelectionModel().getSelectedItem();
                selectedRecu.setStatut("Payé");
                RecuDAO.updateRecuStatus(selectedRecu);
                RecuDAO.updateVendeur(this, selectedRecu);




            } else {
                // Alert if amount paid is less than total amount
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Amount paid is less than the total amount.");
                alert.getDialogPane().getStyleClass().add("alert");
            }

            // Apply custom style class to the alert's dialog pane
            alert.getDialogPane().getStyleClass().add("alert");
            alert.showAndWait();


        } catch (NumberFormatException e) {
            // Handle parsing errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numbers for Total and Amount Paid.");
            alert.getDialogPane().getStyleClass().add("alert");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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
                String username = getUsername();
                dashboardController.displayName(username);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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


    @FXML
    private void searchRecu(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        String recuId = this.recuIdText.getText().trim();

        if (recuId.isEmpty()) {
            try {
                ObservableList<Recu> recuData = RecuDAO.searchRecus();
                populateRecus(recuData);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            try {
                Recu recu = RecuDAO.searchRecu(recuId);
                if (recu != null) {
                    populateAndShowRecu(recu);
                } else {

                    System.out.println("No Recu found with ID: " + recuId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }



    @FXML
    private void searchRecus(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Recu> recuData = RecuDAO.searchRecus();
            this.populateRecus(recuData);
        } catch (SQLException var3) {
            System.out.println("Error occurred while getting recus information from DB.\n" + var3);
            throw var3;
        }
    }

    @FXML
    private void populateRecu(Recu recu) throws ClassNotFoundException {
        ObservableList<Recu> recuData = FXCollections.observableArrayList();
        recuData.add(recu);
        this.recuTable.setItems(recuData);
    }

    @FXML
    private void populateRecus(ObservableList<Recu> recuData) throws ClassNotFoundException {
        this.recuTable.setItems(recuData);
    }




    @FXML
    private void populateAndShowRecu(Recu recu) throws ClassNotFoundException {
        if (recu != null) {
            this.populateRecu(recu);
        } else {
            this.resultArea.setText("This recu does not exist!\n");
        }
    }




    @FXML
    private void populateRecuDetails(ObservableList<RecuDetails> recuDetailsList) throws ClassNotFoundException {
        this.recuDetailsTable.setItems(recuDetailsList);
    }

    @FXML
    private void setRecuInfoToTextAreaDetails(Recu recu) {
        this.resultArea.setText("ID Recu: " + recu.getIdRecu() + "\nTotal: " + recu.getTotal() + "\nStatut: " + recu.getStatut() + "\nVendeur: " + recu.getVendeur());
    }

    @FXML
    private void populateAndShowRecuDetails(RecuDetails recuDetails) throws ClassNotFoundException {
        if (recuDetails != null) {
            ObservableList<RecuDetails> recuDataDetails = FXCollections.observableArrayList();
            recuDataDetails.add(recuDetails);
            this.populateRecuDetails(recuDataDetails);
            //this.setRecuInfoToTextAreaDetails(recuDetails);
        } else {
            this.resultArea.setText("This recu does not exist!\n");
        }
    }

}

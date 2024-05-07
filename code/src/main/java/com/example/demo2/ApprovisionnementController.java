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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApprovisionnementController {

    private Scene scene;
    private Parent root;
    @FXML
    private TextField medicNameText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField quantiteACommander;
    @FXML
    private Label tableLabel;
    @FXML
    private TableView<Approvisionnement> medicamentTable;
    @FXML
    private TableColumn<Approvisionnement, Integer> idMedicColumn;
    @FXML
    private TableColumn<Approvisionnement, String> nameColumn;
    @FXML
    private TableColumn<Approvisionnement, String> quantiteColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> quantiteMaxColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> quantiteCommanderColumn;
    @FXML
    private TableColumn<Approvisionnement, Boolean> prescriptionColumn;
    @FXML
    private TableColumn<Approvisionnement, Double> prixUnitaireAchatColumn;
    @FXML
    private TableColumn<Approvisionnement, Double> prixHabituelColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> seuilCommandeColumn;
    private Executor exec;
    private ObservableList<Approvisionnement> selectedMedicaments = FXCollections.observableArrayList();

    public ApprovisionnementController() {
    }

    @FXML
    private void initialize() {
        this.exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        this.idMedicColumn.setCellValueFactory(cellData -> cellData.getValue().idMedicProperty().asObject());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.prixUnitaireAchatColumn.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireAchatProperty().asObject());
        this.quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        this.quantiteMaxColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteMaxProperty().asObject());
        this.quantiteCommanderColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteCommanderProperty().asObject());
        this.prescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().prescriptionProperty());
        this.prixHabituelColumn.setCellValueFactory(cellData -> cellData.getValue().prixHabituelProperty().asObject());
        this.seuilCommandeColumn.setCellValueFactory(cellData -> cellData.getValue().seuilCommandeProperty().asObject());

        medicamentTable.setOnMouseClicked(event -> {
            if (!medicamentTable.getSelectionModel().isEmpty()) {

                Approvisionnement selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
                quantiteACommander.setText(String.valueOf(selectedMedicament.getQuantiteCommander()));
            }
        });

    }


    @FXML
    public void setLabelApprovisionnement() {
        ObservableList<Approvisionnement> medicamentData = medicamentTable.getItems();
        if (medicamentData.isEmpty()) {
            tableLabel.setText("Cherchez un medicament pour lancer l'approvisionnement");
        }
    }



    @FXML
    private void setQuantiteACommander() {
        try {
            // Get total and amount paid
            Approvisionnement selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
            int nouvelleCommande = Integer.valueOf(quantiteACommander.getText());
            int max = selectedMedicament.getQuantiteMax();
            int quantite = Integer.valueOf(selectedMedicament.getQuantity());

            int diff = nouvelleCommande + quantite;


            Alert alert;

            if (diff <= max) {
                // Display remaining amount in the alert
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commande prete a etre validee");
                alert.setHeaderText(null);
                alert.setContentText("Votre Commande est de  : " + nouvelleCommande);
                alert.getDialogPane().getStyleClass().add("alert");

                selectedMedicament.setQuantiteCommander(Integer.valueOf(quantiteACommander.getText()));
                ApprovisionnementDAO.updateMedicamentQuantiteCommander(selectedMedicament);

            } else {
                // Alert if amount paid is less than total amount
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La quantite est superieur a la quantite maximale acceptee.");
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


    @FXML
    private void setDashboardButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            if (loader != null) {
                root = loader.load();
                DashboardController dashboardController = loader.getController();
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
    private void searchMedicament(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        String medicId = this.medicNameText.getText().trim();

        if (medicId.isEmpty()) {
            try {
                ObservableList<Approvisionnement> medicamentData = ApprovisionnementDAO.searchMedicaments();
                populateMedicaments(medicamentData);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            try {
                Approvisionnement medicament = ApprovisionnementDAO.searchMedicament(medicId);
                if (medicament != null) {
                    populateAndShowMedicament(medicament);
                } else {

                    System.out.println("No Medicament found with Name: " + medicId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @FXML
    private void searchMedicaments(ActionEvent actionEvent) {
        try {
            ObservableList<Approvisionnement> medicamentData = ApprovisionnementDAO.searchMedicaments();

            if (medicamentData == null || medicamentData.isEmpty()) {
                tableLabel.setText("Aucun medicament ne necessite d'approvisionnement");
            } else {
                this.populateMedicaments(medicamentData);
            }
        } catch (SQLException | ClassNotFoundException var3) {
            System.out.println("Error occurred while getting medicaments information from DB.\n" + var3);
        }
    }


    @FXML
    private void populateMedicament(Approvisionnement medicament) {
        ObservableList<Approvisionnement> medicamentData = FXCollections.observableArrayList();
        medicamentData.add(medicament);
        this.medicamentTable.setItems(medicamentData);
    }

    @FXML
    private void populateMedicaments(ObservableList<Approvisionnement> medicamentData) {
        this.medicamentTable.setItems(medicamentData);
    }

    @FXML
    private void populateAndShowMedicament(Approvisionnement medicament) {
        if (medicament != null) {
            this.populateMedicament(medicament);
        } else {
            this.resultArea.setText("This medicament does not exist!\n");
        }
    }
}

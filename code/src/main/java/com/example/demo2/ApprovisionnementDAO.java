package com.example.demo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ApprovisionnementDAO {
    public ApprovisionnementDAO() {
    }

    public static Approvisionnement searchMedicament(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.medicaments WHERE name LIKE '%" + name + "%'";

        try {
            ResultSet rsMedic = DBUtil.dbExecuteQuery(selectStmt);
            Approvisionnement medicament = getMedicamentFromResultSet(rsMedic);
            return medicament;
        } catch (SQLException var4) {
            System.out.println("While searching a medicament with name " + name + ", an error occurred: " + var4);
            throw var4;
        }
    }


    private static Approvisionnement getMedicamentFromResultSet(ResultSet rs) throws SQLException {
        Approvisionnement medicament = null;
        if (rs.next()) {
            medicament = new Approvisionnement();
            medicament.setIdMedic(rs.getInt("id_medic"));
            medicament.setName(rs.getString("name"));
            medicament.setPrixUnitaireAchat(rs.getDouble("prix_unitaire_achat"));
            medicament.setQuantity(rs.getString("quantite"));// Add new properties
            medicament.setPrescription(rs.getBoolean("prescription"));
            medicament.setQuantiteMax(rs.getInt("quantite_max"));
            medicament.setSeuilCommande(rs.getInt("seuil_commande"));
            medicament.setPrixHabituel(rs.getDouble("prix_habituel"));
            medicament.setQuantiteCommander(rs.getInt("quantite_commander"));
        }

        return medicament;
    }

    public static ObservableList<Approvisionnement> searchMedicaments() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.medicaments WHERE quantite <= seuil_commande";

        try {
            ResultSet rsMedicaments = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Approvisionnement> medicamentList = getMedicamentList(rsMedicaments);
            return medicamentList;
        } catch (SQLException var3) {
            System.out.println("SQL select operation has been failed: " + var3);
            throw var3;
        }
    }

    private static ObservableList<Approvisionnement> getMedicamentList(ResultSet rs) throws SQLException {
        ObservableList<Approvisionnement> medicamentList = FXCollections.observableArrayList();

        while(rs.next()) {
            Approvisionnement medicament = new Approvisionnement();
            medicament.setIdMedic(rs.getInt("id_medic"));
            medicament.setName(rs.getString("name"));
            medicament.setPrixUnitaireAchat(rs.getDouble("prix_unitaire_achat"));
            medicament.setQuantity(rs.getString("quantite"));
            medicament.setPrescription(rs.getBoolean("prescription"));
            medicament.setQuantiteMax(rs.getInt("quantite_max"));
            medicament.setSeuilCommande(rs.getInt("seuil_commande"));
            medicament.setPrixHabituel(rs.getDouble("prix_habituel"));
            medicament.setQuantiteCommander(rs.getInt("quantite_commander"));
            medicamentList.add(medicament);
        }

        return medicamentList;
    }

    public static void updateMedicamentQuantiteCommander(Approvisionnement selectedMedicament) throws SQLException {
        String sql = "UPDATE medicaments SET quantite_commander = ? WHERE id_medic = ?";

        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection("PharmaGest", "postgres", "1234");

            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            // Set parameters
            preparedStatement.setInt(1, selectedMedicament.getQuantiteCommander());
            preparedStatement.setInt(2, selectedMedicament.getIdMedic());

            // Execute update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            ex.printStackTrace();
            throw ex; // Rethrow the exception to be handled at higher levels
        }
    }

}

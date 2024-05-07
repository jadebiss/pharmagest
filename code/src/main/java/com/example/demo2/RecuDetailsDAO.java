package com.example.demo2;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecuDetailsDAO {
    public RecuDetailsDAO() {
    }

    private static ObservableList<RecuDetails> getRecuList(ResultSet rs) throws SQLException {
        ObservableList<RecuDetails> recuList = FXCollections.observableArrayList();

        while (rs.next()) {
            RecuDetails recuDetails = new RecuDetails();
            recuDetails.setIdRecuDetails(rs.getInt("id_details"));
            recuDetails.setNomMedicament(rs.getString("nom_medicament"));
            recuDetails.setPrixMedicament(rs.getString("prix_medicament"));
            recuDetails.setQuantiteMedicament(rs.getInt("quantite_medicament"));
            recuDetails.setTotal(rs.getDouble("total"));
            recuList.add(recuDetails);
        }

        return recuList;
    }

    public static ObservableList<RecuDetails> searchRecuDetails(String recuId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.recu_details WHERE id_details=" + recuId;

        try {
            ResultSet rsRecuDetails = DBUtil.dbExecuteQuery(selectStmt);
            return getRecuList(rsRecuDetails);
        } catch (SQLException var4) {
            System.out.println("While searching a recu with " + recuId + " id, an error occurred: " + var4);
            throw var4;
        }
    }
}

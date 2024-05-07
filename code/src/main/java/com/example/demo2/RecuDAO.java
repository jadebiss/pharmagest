package com.example.demo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecuDAO {
    public RecuDAO() {
    }

    public static Recu searchRecu(String recuId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.recu WHERE id_recu = " + recuId + " AND statut = 'En cours'";

        try {
            ResultSet rsRecuDetails = DBUtil.dbExecuteQuery(selectStmt);
            Recu recu = getRecuFromResultSet(rsRecuDetails);
            return recu;
        } catch (SQLException var4) {
            //System.out.println("While searching a recu with " + recuId + " id, an error occurred: " + var4);
            throw var4;
        }
    }

    private static Recu getRecuFromResultSet(ResultSet rs) throws SQLException {
        Recu recu = null;
        if (rs.next()) {
            recu = new Recu();
            recu.setIdRecu(rs.getInt("id_recu"));
            recu.setTotal(rs.getDouble("total"));
            recu.setStatut(rs.getString("statut"));
            recu.setVendeur(rs.getString("vendeur"));
        }

        return recu;
    }

    public static ObservableList<Recu> searchRecus() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.recu WHERE statut='En cours' ";

        try {
            ResultSet rsRecus = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Recu> recuList = getRecuList(rsRecus);
            return recuList;
        } catch (SQLException var3) {
            System.out.println("SQL select operation has been failed: " + var3);
            throw var3;
        }
    }

    private static ObservableList<Recu> getRecuList(ResultSet rs) throws SQLException {
        ObservableList<Recu> recuList = FXCollections.observableArrayList();

        while (rs.next()) {
            Recu recu = new Recu();
            recu.setIdRecu(rs.getInt("id_recu"));
            recu.setTotal(rs.getDouble("total"));
            recu.setStatut(rs.getString("statut"));
            recu.setVendeur(rs.getString("vendeur"));
            recuList.add(recu);
        }

        return recuList;
    }

    public static void updateRecuStatus(Recu recu) throws SQLException {
        String sql = "UPDATE recu SET statut = ? WHERE id_recu = ?";

        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection("PharmaGest", "postgres", "1234");

            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            // Set parameters
            preparedStatement.setString(1, recu.getStatut());
            preparedStatement.setInt(2, recu.getIdRecu());

            // Execute update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            ex.printStackTrace();
            throw ex; // Rethrow the exception to be handled at higher levels
        }
    }
    public static void updateVendeur(RecuController controller, Recu recu) throws SQLException {
        String sql = "UPDATE recu SET vendeur = ? WHERE id_recu = ?";
        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection("PharmaGest", "postgres", "1234");

            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            preparedStatement.setString(1, controller.getUsername());
            preparedStatement.setInt(2, recu.getIdRecu());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }


}


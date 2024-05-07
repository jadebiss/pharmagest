package com.example.demo2;


import javafx.beans.property.*;

public class RecuDetails {
    // Declare Recu Table Columns
    private IntegerProperty idRecuDetails;
    private final StringProperty nomMedicament;
    private final StringProperty prixMedicament;
    private IntegerProperty quantiteMedicament;
    private final DoubleProperty total;

    // Constructor
    public RecuDetails() {
        this.idRecuDetails = new SimpleIntegerProperty();
        this.nomMedicament = new SimpleStringProperty();
        this.prixMedicament = new SimpleStringProperty();
        this.quantiteMedicament = new SimpleIntegerProperty();
        this.total = new SimpleDoubleProperty();
    }

    // id_recu
    public int getIdRecuDetails() {
        return idRecuDetails.get();
    }

    public void setIdRecuDetails(int idRecu) {
        this.idRecuDetails.set(idRecu);
    }

    public IntegerProperty idRecuDetailsProperty() {
        return idRecuDetails;
    }

    // nom_medicament
    public String getNomMedicament() {
        return nomMedicament.get();
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament.set(nomMedicament);
    }

    public StringProperty nomMedicamentProperty() {
        return nomMedicament;
    }

    // prix_medicament
    public String getPrixMedicament() {
        return prixMedicament.get();
    }

    public void setPrixMedicament(String prixMedicament) {
        this.prixMedicament.set(prixMedicament);
    }

    public StringProperty prixMedicamentProperty() {
        return prixMedicament;
    }

    // quantite_medicament
    public int getQuantiteMedicament() {
        return quantiteMedicament.get();
    }

    public void setQuantiteMedicament(int quantiteMedicament) {
        this.quantiteMedicament.set(quantiteMedicament);
    }

    public IntegerProperty quantiteMedicamentProperty() {
        return quantiteMedicament;
    }

    // total
    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }
}


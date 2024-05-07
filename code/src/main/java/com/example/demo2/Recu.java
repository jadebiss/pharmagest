package com.example.demo2;

import javafx.beans.property.*;

public class Recu {
    // Declare Recu Table Columns
    private IntegerProperty idRecu;
    private final DoubleProperty total;
    private final StringProperty statut;
    private final StringProperty vendeur;

    // Constructor
    public Recu() {
        this.idRecu = new SimpleIntegerProperty();
        this.total = new SimpleDoubleProperty();
        this.statut = new SimpleStringProperty("En cours"); // Default value
        this.vendeur = new SimpleStringProperty();
    }

    // id_recu
    public int getIdRecu() {
        return idRecu.get();
    }

    public void setIdRecu(int idRecu) {
        this.idRecu.set(idRecu);
    }

    public IntegerProperty idRecuProperty() {
        return idRecu;
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

    // statut
    public String getStatut() {
        return statut.get();
    }

    public void setStatut(String statut) {
        this.statut.set(statut);
    }

    public StringProperty statutProperty() {
        return statut;
    }

    // vendeur
    public String getVendeur() {
        return vendeur.get();
    }

    public void setVendeur(String vendeur) {
        this.vendeur.set(vendeur);
    }

    public StringProperty vendeurProperty() {
        return vendeur;
    }
}

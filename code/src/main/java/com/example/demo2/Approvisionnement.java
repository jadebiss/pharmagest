package com.example.demo2;

import javafx.beans.property.*;

public class Approvisionnement {
    // Declare Medicaments Table Columns
    private IntegerProperty idMedic;
    private final StringProperty name;
    private final DoubleProperty price;
    private StringProperty conditionnement;
    private StringProperty quantity;
    private StringProperty description;
    private BooleanProperty prescription;
    private IntegerProperty quantiteMax;
    private IntegerProperty seuilCommande;
    private final DoubleProperty prixUnitaireAchat;
    private final DoubleProperty prixHabituel;
    private IntegerProperty quantiteCommander;

    // Constructor
    public Approvisionnement() {
        this.idMedic = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.conditionnement = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.prescription = new SimpleBooleanProperty();
        this.quantiteMax = new SimpleIntegerProperty();
        this.seuilCommande = new SimpleIntegerProperty();
        this.prixUnitaireAchat = new SimpleDoubleProperty();
        this.prixHabituel = new SimpleDoubleProperty();
        this.quantiteCommander = new SimpleIntegerProperty();
    }

    // id_medic
    public int getIdMedic() {
        return idMedic.get();
    }

    public void setIdMedic(int idMedic){
        this.idMedic.set(idMedic);
    }

    public IntegerProperty idMedicProperty(){
        return idMedic;
    }

    // name
    public String getName() {
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty nameProperty(){
        return name;
    }

    // price
    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price){
        this.price.set(price);
    }
    public DoubleProperty priceProperty(){
        return price;
    }

    // conditionnement
    public String getConditionnement() {
        return conditionnement.get();
    }

    public void setConditionnement(String conditionnement){
        this.conditionnement.set(conditionnement);
    }

    public StringProperty conditionnementProperty(){
        return conditionnement;
    }

    // quantity
    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity){
        this.quantity.set(quantity);
    }

    public StringProperty quantityProperty(){
        return quantity;
    }


    // description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description){
        this.description.set(description);
    }

    public StringProperty descriptionProperty(){
        return description;
    }

    // prescription
    public boolean isPrescription() {
        return prescription.get();
    }

    public void setPrescription(boolean prescription) {
        this.prescription.set(prescription);
    }

    public BooleanProperty prescriptionProperty() {
        return prescription;
    }

    // quantite_max
    public int getQuantiteMax() {
        return quantiteMax.get();
    }

    public void setQuantiteMax(int quantiteMax) {
        this.quantiteMax.set(quantiteMax);
    }

    public IntegerProperty quantiteMaxProperty() {
        return quantiteMax;
    }

    // seuil_commande
    public int getSeuilCommande() {
        return seuilCommande.get();
    }

    public void setSeuilCommande(int seuilCommande) {
        this.seuilCommande.set(seuilCommande);
    }

    public IntegerProperty seuilCommandeProperty() {
        return seuilCommande;
    }

    // prix_unitaire_achat
    public double getPrixUnitaireAchat() {
        return prixUnitaireAchat.get();
    }

    public void setPrixUnitaireAchat(double prixUnitaireAchat) {
        this.prixUnitaireAchat.set(prixUnitaireAchat);
    }

    public DoubleProperty prixUnitaireAchatProperty() {
        return prixUnitaireAchat;
    }

    // prix_habituel
    public double getPrixHabituel() {
        return prixHabituel.get();
    }

    public void setPrixHabituel(double prixHabituel) {
        this.prixHabituel.set(prixHabituel);
    }

    public DoubleProperty prixHabituelProperty() {
        return prixHabituel;
    }

    // quantite_commander
    public int getQuantiteCommander() {
        return quantiteCommander.get();
    }

    public void setQuantiteCommander(int quantiteCommander) {
        this.quantiteCommander.set(quantiteCommander);
    }

    public IntegerProperty quantiteCommanderProperty() {
        return quantiteCommander;
    }
}

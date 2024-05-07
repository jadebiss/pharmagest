package com.example.demo2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.*;

/**
 * Created by ONUR BASKIRT on 27.02.2016.
 */
public class UserAccount {
    // Declare UserAccounts Table Columns
    private IntegerProperty id_useraccounts;
    private StringProperty username;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty mdp_pharm;
    private StringProperty status;


    // Constructor
    public UserAccount() {
        this.id_useraccounts = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.mdp_pharm = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    // id_useraccounts
    public int getIdUserAccounts() {
        return id_useraccounts.get();
    }

    public void setIdUserAccounts(int idUserAccounts){
        this.id_useraccounts.set(idUserAccounts);
    }

    public IntegerProperty id_useraccountsProperty(){
        return id_useraccounts;
    }

    // username
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username){
        this.username.set(username);
    }

    public StringProperty usernameProperty(){
        return username;
    }

    // firstname
    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname){
        this.firstname.set(firstname);
    }

    public StringProperty firstnameProperty(){
        return firstname;
    }

    // lastname
    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname){
        this.lastname.set(lastname);
    }

    public StringProperty lastnameProperty(){
        return lastname;
    }

    // mdp_pharm
    public String getMdpPharm() {
        return mdp_pharm.get();
    }

    public void setMdpPharm(String mdpPharm){
        this.mdp_pharm.set(mdpPharm);
    }

    public StringProperty mdpPharmProperty(){
        return mdp_pharm;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status){
        this.status.set(status);
    }

    public StringProperty statusProperty(){
        return status;
    }
}

package com.example.demo2;

public class PharmaCaisse {

    private int id_medic, quantite;
    private double prixUnitaireAchat;
    private String name, conditionnement;

    public static class medicaments {

        int id, qty;
        double price;
        String name, cond;
        public medicaments(int id, int qty, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.cond = cond;
            this.qty = qty;}

        public int getId() {
            return id;
        }
        public String getName() { return name;}

        public int getQty() {
            return qty;
        }

        public String getCond() {
            return cond;
        }

        public double getPrice() { // Corrected method name to getPrix
            return price;
        }}}

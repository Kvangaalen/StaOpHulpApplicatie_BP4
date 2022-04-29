package com.example.sta_op_hulp.Model;

import java.util.Comparator;

public class Activiteit {
    private int gebruikersnummer, stoelnummer;
    private String tijdzit,tijdopstaan, datum;

    public Activiteit(int gebruikersnummer, int stoelnummer, String tijdzit, String tijdopstaan, String datum){
        this.gebruikersnummer = gebruikersnummer;
        this.stoelnummer = stoelnummer;
        this.tijdzit = tijdzit;
        this.tijdopstaan = tijdopstaan;
        this.datum = datum;
    }

    public static Comparator <Activiteit> Activiteitsort = new Comparator<Activiteit>() {
        @Override
        public int compare(Activiteit a1, Activiteit a2) {
            return a2.getDatum().compareTo(a1.getDatum()) ;
        }
    };

    public static Comparator <Activiteit> Activiteitsorttime = new Comparator<Activiteit>() {
        @Override
        public int compare(Activiteit a1, Activiteit a2) {
            return a2.getTijdzit().compareTo(a1.getTijdzit()) ;
        }
    };

    public int getGebruikersnummer() {
        return gebruikersnummer;
    }

    public void setGebruikersnummer(int gebruikersnummer) {
        this.gebruikersnummer = gebruikersnummer;
    }

    public int getStoelnummer() {
        return stoelnummer;
    }

    public void setStoelnummer(int stoelnummer) {
        this.stoelnummer = stoelnummer;
    }

    public String getTijdzit() {
        return tijdzit;
    }

    public void setTijdzit(String tijdzit) {
        this.tijdzit = tijdzit;
    }

    public String getTijdopstaan() {
        return tijdopstaan;
    }

    public void setTijdopstaan(String tijdopstaan) {
        this.tijdopstaan = tijdopstaan;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }



}
